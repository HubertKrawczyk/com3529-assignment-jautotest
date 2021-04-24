package jautotest.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.IfStmt;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;

/**
 * Class used to parse and prepare a file containing class with a method for which tests will be generated.
 *
 */
public class Parse {

    public static int ADDED_PARAMS = 3; //coveredBranches, coveredConditions, coveredPredicates

    /**
     * Inserts coverage methods into the tested method
     * @param methodDeclaration Method Declaration
     * @branchPredicatesConditions An array which will be filled with values telling to which predicate each condition belongs
     * @return an array with two values: 1st->number of branches in the method, 2nd -> number of conditions in the method
     */
    static int[] prepareMethod(MethodDeclaration methodDeclaration, ArrayList<Integer[]> branchPredicatesConditions) {
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
                        newCond += Parse.class.getPackageName()
                            + ".CoverageUtils.coveredCondition((" + accum + "), " + condCounter
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
                        newCond += Parse.class.getPackageName()
                            + ".CoverageUtils.coveredCondition((" + accum + "), " + condCounter
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
                        newCond += Parse.class.getPackageName()
                            + ".CoverageUtils.coveredCondition((" + accum + "), " + condCounter
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
                newCond += Parse.class.getPackageName()
                    + ".CoverageUtils.coveredCondition((" + accum + "), " + condCounter + ", coveredConditions)";
                conditionsForBranch.add(condCounter);
            }

            newCond = Parse.class.getPackageName() +".CoverageUtils.coveredPredicate(("+newCond +"), "+branchCounter+", coveredPredicates)";

            ifStmt.setCondition(StaticJavaParser.parseExpression(newCond));
            

            Integer[] conds = new Integer[conditionsForBranch.size()];
            conds = conditionsForBranch.toArray(conds);
            branchPredicatesConditions.add(conds);

            if (ifStmt.getThenStmt().isExpressionStmt()) {
                // no '{}'' brackets after if, need to replace the expressionStmt with blockStmt
                ExpressionStmt expStmt = ifStmt.getThenStmt().asExpressionStmt().clone();
                BlockStmt newBlockStmt = new BlockStmt().addStatement(expStmt);
                ifStmt.getThenStmt().replace(newBlockStmt);
            }

            // add coveredBranch method to the blockStmt
            ifStmt.getThenStmt().toBlockStmt().get().addStatement(0, StaticJavaParser
                    .parseExpression(Parse.class.getPackageName()+".CoverageUtils.coveredBranch(" + branchCounter + ",coveredBranches)"));


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
                        BlockStmt newBlockStmt = new BlockStmt().addStatement(expStmt);
                        ifStmt.getElseStmt().get().replace(newBlockStmt);
                    }

                    // add coveredBranch method to the blockStmt
                    ifStmt.getElseStmt().get().toBlockStmt().get().addStatement(0, StaticJavaParser
                            .parseExpression(Parse.class.getPackageName()
                            + ".CoverageUtils.coveredBranch(" + branchCounter + ",coveredBranches)"));

                }
            }
        }

        methodDeclaration.addParameter("java.util.Set<Integer>", "coveredBranches");
        methodDeclaration.addParameter("java.util.Set<Integer>", "coveredConditions");
        methodDeclaration.addParameter("java.util.Set<Integer>", "coveredPredicates");

        DebugUtils.printLn("Method parsed successfully");
        result[0] = branchCounter;
        result[1] = condCounter;
        return result;
    }

    public static void main(String[] args) {
        DebugUtils.printLn("Parse start for arguments:");

        String dataString = "";
        String methodName = "";
        String className = "";
        String[] inputMethodParameters = null;
        if (args.length == 3) {
            String path = args[0];
            className = args[1];
            methodName = args[2];

            if (methodName.indexOf("(")!=-1){ // user specified method parameters
              inputMethodParameters = methodName.substring(methodName.indexOf("(")+1, methodName.indexOf(")")).split(", |,");
              methodName=methodName.substring(0, methodName.indexOf("("));

            }
            DebugUtils.printLn(" * file path: '" + path + "'");
            DebugUtils.printLn(" * class name: '" + className + "'");
            DebugUtils.printLn(" * method name: '" + methodName + "'");
            DebugUtils.printLn("");
            

            try {
                DebugUtils.print("Reading the file...");
                File myObj = new File(path);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    DebugUtils.print(".");
                    String data = myReader.nextLine();
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

        CompilationUnit comp = null;
        try {
            comp = StaticJavaParser.parse(dataString);
        } catch (ParseProblemException e) {
            DebugUtils.printLn("Problem occured during processing the file:\n" + e.getMessage());
            DebugUtils.printLn("Exiting...");
            return;
        }
        

        int branchCounter = 0;
        int condCounter = 0;

        ClassOrInterfaceDeclaration cls = null;
        try {
            cls = comp.getClassByName(className).get();
        } catch (NoSuchElementException e) {
            DebugUtils.printLn("Class '" + className + "' not found in the file");
            DebugUtils.printLn("Exiting...");
            return;
        }


        MethodDeclaration testedMethod = null;


        for (MethodDeclaration m : cls.findAll(MethodDeclaration.class)) {
            if (m.isPublic() && m.getNameAsString().equals(methodName)) {
                if(inputMethodParameters==null) {  
                  testedMethod = m;
                  break;

                } else if( m.getParameters().size() == inputMethodParameters.length) {
                  //test if the parameters match
                  boolean paramsMatch = true;
                  NodeList<Parameter> methodParams = m.getParameters();

                  for (int i = 0; i < methodParams.size(); i++) {
                    if(!methodParams.get(i).toString().equals(inputMethodParameters[i])){
                      paramsMatch = false;
                    }
                  }
                  if(paramsMatch) {
                    testedMethod = m;
                    break;
                  }
                  
                  
                }
                
            }
        }
        if (testedMethod == null) {
            if (inputMethodParameters == null) {
                DebugUtils.printLn("No public method called '" + methodName + "' found");
            } else {
                DebugUtils.printLn("No public method called '" + methodName + "' with given parameters found");
            }

            DebugUtils.printLn("Exiting...");
            return;

        } else if (testedMethod.getParameterByName("coveredBranches").isPresent()) {
            DebugUtils.printLn("Method cannot have parameter called 'coveredBranches'");
            DebugUtils.printLn("Exiting...");
            return;

        } else if (testedMethod.getParameterByName("coveredConditions").isPresent()) {
            DebugUtils.printLn("Method cannot have parameter called 'coveredConditions'");
            DebugUtils.printLn("Exiting...");
            return;

        } else {
            DebugUtils.printLn("Loaded the method: '"+testedMethod.getDeclarationAsString()+"'");

        }

        DebugUtils.printLn("Adding required code to the class...");

        ArrayList<Integer[]> branchPredicateConditions = new ArrayList<Integer[]>();
        int[] result = prepareMethod(testedMethod, branchPredicateConditions);

        branchCounter = result[0];
        condCounter = result[1];

        


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

        // https://stackabuse.com/how-to-get-current-date-and-time-in-java/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // document time the file was created
        Comment clsComment = new BlockComment();
        clsComment.setContent("\nParsed file, generated at: " + formatter.format(date)+"\n");
        cls.setComment(clsComment);


        char separator = File.separatorChar;

        // check if there is any test for this class already, and remove it
        DebugUtils.printLn("\nDeleting previously created test files with this class...");
        File testsFolder = new File("."+separator+"src"+separator+"test"+separator+"java"+separator+"jautotest"+separator+"app"+separator + "tests");
        for (File file : testsFolder.listFiles()) {
                if (file.getName().indexOf(className)!=-1) {
                    DebugUtils.printLn("Deleting test file: " +file.getName());
                    file.delete();
                } 
        }
        DebugUtils.printLn("Done\n");

    
    
        // save the new class to a file
        File file = new File("."+separator+"src"+separator+"main"+separator+"java"+separator+"input"+separator + className + ".java");
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(comp.toString());
            writer.close();
            DebugUtils.printLn("Saved the file successfully.");
            DebugUtils.printLn("File location: " + file.getAbsolutePath() + "\n(Do not modify)");
        } catch (IOException e) {
            DebugUtils.printLn("An error occured during saving the file");
            e.printStackTrace();
            DebugUtils.printLn("Exiting...");
            return;
        }

        DebugUtils.printLn("Finished. Next step is using 'GenerateTests', please refer to the manual in README.md .");

    }
}
