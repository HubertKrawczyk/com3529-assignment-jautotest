package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;

/**
 * Class used to parse and prepare a file containing class with a method for which tests will be generated.
 *
 */
public class Parse {

    /**
     * Inserts coverage methods into the tested method
     * @param methodDeclaration Method Declaration
     * @param comp Compilation Unit
     * @branchPredicatesConditions An array which will be filled with values telling to which predicate each condition belongs
     * @return an array with two values: 1st->number of branches in the method, 2nd -> number of conditions in the method
     */
    static int[] prepareMethod(MethodDeclaration methodDeclaration, CompilationUnit comp,
        ArrayList<Integer[]> branchPredicatesConditions) {
        int[] result = new int[2];
        int branchCounter = 0;
        int condCounter = 0;

        // https://github.com/javaparser/javaparser/issues/946 by matozoid
        for (IfStmt ifStmt : methodDeclaration.findAll(IfStmt.class)) {
            // "if" found
            branchCounter++;

            String condition = ifStmt.getCondition().toString();

            String newCond = "";

            // separate all conditions and replace them with a coverage method
            String accum = "";
            int accumBracketsCounter = 0;
            ArrayList<Integer> conditionsForBranch = new ArrayList<Integer>();
            for (int i = 0; i < condition.length(); i++) {
                char c = condition.charAt(i);
                if (c == ' ' && accum.equals("")) {
                    // do nothing
                } else if (c == '&') {
                    if (!accum.equals("")) {
                        condCounter++;
                        newCond += "CoverageUtils.coveredCondition((" + accum + "), " + condCounter
                                + ", coveredConditions)";
                        conditionsForBranch.add(condCounter);
                    }

                    if (condition.charAt(i + 1) == '&') {
                        newCond += "&&";
                        i++;
                    } else {
                        newCond += "&";
                    }
                    accum = "";
                } else if (c == '|') {
                    if (!accum.equals("")) {
                        condCounter++;
                        newCond += "CoverageUtils.coveredCondition((" + accum + "), " + condCounter
                                + ", coveredConditions)";
                        conditionsForBranch.add(condCounter);
                    }

                    if (condition.charAt(i + 1) == '|') {
                        newCond += "||";
                        i++;
                    } else {
                        newCond += "|";
                    }

                    accum = "";
                } else if (c == '(') {
                    if (accum.equals("")) {
                        newCond += c;
                    } else {// ( bracket part of condition
                        accum += c;
                        accumBracketsCounter++;
                    }
                } else if (c == ')') {
                    if (accumBracketsCounter > 0) {
                        accumBracketsCounter--;
                        accum += c;
                    } else { // the condition finishes here
                        condCounter++;
                        newCond += "CoverageUtils.coveredCondition((" + accum + "), " + condCounter
                                + ", coveredConditions)";
                        conditionsForBranch.add(condCounter);
                        newCond += c;
                        accum = "";
                    }
                } else {
                    accum += c;
                }
            }
            if (!accum.equals("")) {
                condCounter++;
                newCond += "CoverageUtils.coveredCondition((" + accum + "), " + condCounter + ", coveredConditions)";
                conditionsForBranch.add(condCounter);
            }

            ifStmt.setCondition(StaticJavaParser.parseExpression(newCond));
            

            Integer[] conds = new Integer[conditionsForBranch.size()];
            conds = conditionsForBranch.toArray(conds);
            branchPredicatesConditions.add(conds);

            if (ifStmt.getThenStmt().isExpressionStmt()) {
                // no '{}'' brackets after if, need to replace the expressionStmt with blockStmt
                ExpressionStmt expStmt = ifStmt.getThenStmt().asExpressionStmt().clone();
                BlockStmt newBlockStmt = StaticJavaParser.parseBlock("{}").addStatement(expStmt);
                ifStmt.getThenStmt().replace(newBlockStmt);
            }

            // add coveredBranch method to the blockStmt
            ifStmt.getThenStmt().toBlockStmt().get().addStatement(0, StaticJavaParser
                    .parseExpression("CoverageUtils.coveredBranch(" + branchCounter + ",coveredBranches)"));


            if (ifStmt.getElseStmt().isPresent()) {
                // This "if" has an "else"
                if (ifStmt.getElseStmt().get() instanceof IfStmt) {
                    // it's an "else-if". We already count that by counting the "if" above.
                } else {
                    // it's an "else-something". Add it.
                    branchCounter++;
                    conds = new Integer[0];
                    branchPredicatesConditions.add(conds);
                    if (ifStmt.getThenStmt().isExpressionStmt()) {
                        // no '{}' brackets after else, need to replace expressionStmt with blockStmt
                        ExpressionStmt expStmt = ifStmt.getElseStmt().get().asExpressionStmt().clone();
                        BlockStmt newBlockStmt = StaticJavaParser.parseBlock("{}").addStatement(expStmt);
                        ifStmt.getElseStmt().get().replace(newBlockStmt);
                    }

                    // add coveredBranch method to the blockStmt
                    ifStmt.getElseStmt().get().toBlockStmt().get().addStatement(0, StaticJavaParser
                            .parseExpression("CoverageUtils.coveredBranch(" + branchCounter + ",coveredBranches)"));

                }
            }
        }

        methodDeclaration.addParameter("Set<Integer>", "coveredBranches");
        methodDeclaration.addParameter("Set<Integer>", "coveredConditions");

        DebugUtils.printLn("Method parsed successfully");
        result[0] = branchCounter;
        result[1] = condCounter;
        return result;
    }

    public static void main(String[] args) {
        DebugUtils.printLn("GenerateTests start for arguments:");

        String dataString = "";
        String methodName = "";
        String className = "";
        if (args.length == 3) {
            String path = args[0];
            className = args[1];
            methodName = args[2];
            DebugUtils.printLn("file path: '" + path + "'");
            DebugUtils.printLn("class name: '" + className + "'");
            DebugUtils.printLn("method name: '" + methodName + "'");

            try {
                DebugUtils.print("Reading the file...");
                File myObj = new File(path);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    DebugUtils.print(".");
                    String data = myReader.nextLine();
                    // System.out.println(data);
                    dataString += data + "\n";
                }
                DebugUtils.printLn("done");
                myReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("File '" + path + "' not found!");
                DebugUtils.printLn("Exiting...");
                return;
            }
        } else {
            DebugUtils.printLn("Please provide file path, class name and method name as arguments!");
            DebugUtils.printLn("Exiting...");
            return;
        }

        CompilationUnit comp = StaticJavaParser.parse(dataString);
        comp.addImport("java.util.Set");
        comp.addImport("com.mycompany.app.CoverageUtils");
        int branchCounter = 0;
        int condCounter = 0;
        MethodDeclaration testedMethod = null;

        for (MethodDeclaration m : comp.findAll(MethodDeclaration.class)) {
            if (m.getNameAsString().equals("coveredCondition")) {
                DebugUtils.printLn("The class cannot contain method 'coveredCondition', please change it to other name");
                DebugUtils.printLn("Exiting...");
                return;
            } else if (m.getNameAsString().equals("coveredBranch")) {
                DebugUtils.printLn("The class cannot contain method 'coveredBranch', please change it to other name");
                DebugUtils.printLn("Exiting...");
                return;
            } else if (m.isPublic() && m.getNameAsString().equals(methodName)) {
                testedMethod = m;
            }
        }
        if (testedMethod == null) {
            DebugUtils.printLn("No public method called '" + methodName + "' found");
            DebugUtils.printLn("Exiting...");
            return;
        } else {
            DebugUtils.printLn("Loaded the method");
        }

        DebugUtils.printLn("Adding required code to the class...");

        ArrayList<Integer[]> branchPredicateConditions = new ArrayList<Integer[]>();
        int[] result = prepareMethod(testedMethod, comp, branchPredicateConditions);

        branchCounter = result[0];
        condCounter = result[1];

        

        ClassOrInterfaceDeclaration cls = comp.getClassByName(className).get();

        // add 2 static public fields telling number of branches and conditions
        FieldDeclaration fd = cls.addPublicField(Integer.class, "numberOfBranches");
        fd.setStatic(true);
        fd.setVariable(0, new VariableDeclarator(fd.getElementType(), "numberOfBranches = " + branchCounter));

        fd = cls.addPublicField(Integer.class, "numberOfConditions");
        fd.setStatic(true);
        fd.setVariable(0, new VariableDeclarator(fd.getElementType(), "numberOfConditions = " + condCounter));

        // add a field telling which conditions belong to which branch predicates
        String predicateConditionsString = "{";
        for (int i = 0; i < branchPredicateConditions.size(); i++) {
            predicateConditionsString += "{";
            Integer[] predicate = branchPredicateConditions.get(i);
            if (predicate.length > 0) {
                for (int j = 0; j < predicate.length; j++) {
                    predicateConditionsString += predicate[j];
                    if (j < predicate.length - 1)
                        predicateConditionsString += ", ";
                }
            }
            predicateConditionsString += "}";
            if (i < branchPredicateConditions.size() - 1)
                predicateConditionsString += ", ";
        }
        predicateConditionsString += "}";

        fd = cls.addPublicField("Integer", "branchesPredicatesConditions");
        fd.setStatic(true);
        fd.setVariable(0, new VariableDeclarator(fd.getElementType(),
                "branchesPredicatesConditions[][] = " + predicateConditionsString));

        comp.removePackageDeclaration();
        comp.setPackageDeclaration("input");

        // save the class to a file
        File file = new File(".\\src\\main\\java\\input\\" + className + ".java");
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(comp.toString());
            writer.close();
            DebugUtils.printLn("Saved the file successfully.");
        } catch (IOException e) {
            DebugUtils.printLn("An error occured during saving the file");
            e.printStackTrace();
            DebugUtils.printLn("Exiting...");
            return;
        }

        DebugUtils.printLn("Finished. Next step is using 'GenerateTests', please refer to the manual in README.md .");

    }
}
