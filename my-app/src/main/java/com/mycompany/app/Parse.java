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
 * Hello world!
 *
 */
public class Parse 
{
    // static List<String> calculateDisjunctions(String s){
    //     ArrayList<String> ar = new ArrayList<>();
    //     int bracketsStack = 0;
    //     boolean firstLine = false;
    //     String accum = "";
    //     for (int i = 0; i<s.length();i++){

    //         if(s.charAt(i) == '|'&&bracketsStack ==0){
    //             if(firstLine){
    //                 ar.add(accum);
    //                 //System.out.println("n: "+accum);
    //                 accum = "";
    //                 firstLine = false;
    //             }else{
    //                 firstLine = true;
    //             }
    //         }
            
    //         else{
    //             firstLine = false;
    //             accum += s.charAt(i);
    //             if(s.charAt(i) == '('){
    //                 bracketsStack++;
    //             }else if(s.charAt(i) == ')'){
    //                 bracketsStack--;
    //             }
                
    //         }
    //     }
    //     ar.add(accum);

    //     return ar;
    // }

    /**
     * Inserts coverage functions into the tested method
     */
    static int[] prepareMethod(MethodDeclaration m, CompilationUnit comp, ArrayList<Integer[]> branchPredicatesConditions){
        int[] result = new int[2];
        int branchCounter = 0;
        int condCounter = 0;
        
        //https://github.com/javaparser/javaparser/issues/946 by matozoid 
        for (IfStmt ifStmt : m.findAll(IfStmt.class)) {
            // "if" found

            String condition = ifStmt.getCondition().toString();

            String newCond = "";
            /*
            // distinct conjunsts
            List<String> conjuncts = calculateDisjunctions(condition);

            //DebugUtils.dbgLn("-Found a condition: "+condition);

            for (int i =0;i<conjuncts.size()-1;i++){
                condCounter++;
                newCond+="coveredCondition(("+conjuncts.get(i)+"), "+condCounter+", coveredConditions) || ";
            }
            condCounter++;
            newCond+="coveredCondition(("+conjuncts.get(conjuncts.size()-1)+"), "+condCounter+", coveredConditions) ";
            */

            //distinct all conditions
            String accum = "";
            int accumBracketsCounter =0;
            ArrayList<Integer> condCounterForBranch = new ArrayList<Integer>();
            for(int i = 0;i<condition.length();i++){
                char c = condition.charAt(i);
                if(c ==' '&& accum.equals("")){
                    //do nothing
                }
                else if(c =='&'){
                    if (!accum.equals("")){
                        condCounter++;
                        newCond+="CoverageUtils.coveredCondition(("+accum+"), "+condCounter+", coveredConditions)";
                        condCounterForBranch.add(condCounter);
                    }
                    
                    if(condition.charAt(i+1)=='&'){
                        newCond+="&&";
                        i++;
                    }else{
                        newCond+="&";
                    }
                    accum="";
                }else if(c =='|'){
                    if (!accum.equals("")){
                        condCounter++;
                        newCond+="CoverageUtils.coveredCondition(("+accum+"), "+condCounter+", coveredConditions)";
                        condCounterForBranch.add(condCounter);
                    }
                    
                    if(condition.charAt(i+1)=='|'){
                        newCond+="||";
                        i++;
                    }else{
                        newCond+="|";
                    }
                    
                    accum="";
                }else if(c=='('){
                    if(accum.equals("")){
                        newCond+=c;
                    }else{// ( bracket part of condition
                        accum+=c;
                        accumBracketsCounter++;
                    }
                }else if(c==')'){
                    if(accumBracketsCounter>0){
                        accumBracketsCounter--;
                        accum+=c;
                    }else{ //the condition finishes here
                        condCounter++;
                        newCond+="CoverageUtils.coveredCondition(("+accum+"), "+condCounter+", coveredConditions)";
                        condCounterForBranch.add(condCounter);
                        newCond+=c;
                        accum="";
                    }
                }else{
                    accum+=c;
                }
            }
            if (!accum.equals("")){
                condCounter++;
                newCond+="CoverageUtils.coveredCondition(("+accum+"), "+condCounter+", coveredConditions)";
                condCounterForBranch.add(condCounter);
            }
            
            //DebugUtils.dbgLn("newCond:"+newCond);
            ifStmt.setCondition(StaticJavaParser.parseExpression(newCond));
            //System.out.println(ifStmt.getCondition());

            branchCounter++;

            Integer[] conds = new Integer[condCounterForBranch.size()];
            conds  = condCounterForBranch.toArray(conds);
            branchPredicatesConditions.add(conds);

            if(ifStmt.getThenStmt().isExpressionStmt()){
                // no {} brackets after if, need to replace expressionStmt with blockStmt
                ExpressionStmt expStmt = ifStmt.getThenStmt().asExpressionStmt().clone();
                BlockStmt newBlockStmt = StaticJavaParser.parseBlock("{}").addStatement(expStmt);
                ifStmt.getThenStmt().replace(newBlockStmt);
            }

            ifStmt.getThenStmt().toBlockStmt().get().addStatement(0,
            StaticJavaParser.parseExpression("CoverageUtils.coveredBranch("+branchCounter+",coveredBranches)"));
            
            // ((BlockStmt) ifStmt.getChildNodes().get(0)).addStatement(0,
            //     StaticJavaParser.parseExpression("coveredBranch("+branchCounter+",coveredBranches)"));
            
            //m.getBody().get().getStatements().addAfter(someExp, ifStmt);

            if (ifStmt.getElseStmt().isPresent()) {
                // This "if" has an "else"
                if (ifStmt.getElseStmt().get() instanceof IfStmt) {
                    // it's an "else-if". We already count that by counting the "if" above.
                } else {
                    // it's an "else-something". Add it.
                    branchCounter++;
                    conds = new Integer[0];
                    branchPredicatesConditions.add(conds);
                    if(ifStmt.getThenStmt().isExpressionStmt()){
                        // no {} brackets after else, need to replace expressionStmt with blockStmt
                        ExpressionStmt expStmt = ifStmt.getElseStmt().get().asExpressionStmt().clone();
                        BlockStmt newBlockStmt = StaticJavaParser.parseBlock("{}").addStatement(expStmt);
                        ifStmt.getElseStmt().get().replace(newBlockStmt);
                    }
        
                    ifStmt.getElseStmt().get().toBlockStmt().get().addStatement(0,
                    StaticJavaParser.parseExpression("CoverageUtils.coveredBranch("+branchCounter+",coveredBranches)"));

                    // ((BlockStmt) ifStmt.getElseStmt().get()).addStatement(0,
                    // StaticJavaParser.parseExpression("coveredBranch("+branchCounter+",coveredBranches)"));
                }
            }
        }

        m.addParameter("Set<Integer>", "coveredBranches");
        m.addParameter("Set<Integer>", "coveredConditions");
        

        // System.out.println(" ****AT END***");
        // System.out.println(m);
        // System.out.println(" ****      ***");
        DebugUtils.dbgLn("Method parsed successfully");
        result[0] = branchCounter;
        result[1] = condCounter;
        return result;
    }
    public static void main( String[] args )
    {
        DebugUtils.dbgLn("GenerateTests start for arguments:");

        String dataString = "";
        String methodName = "";
        String className = "";
        if(args.length == 3){
            String path = args[0];
            className = args[1];
            methodName = args[2];
            DebugUtils.dbgLn("file path: '"+path+"'");
            DebugUtils.dbgLn("class name: '"+className+"'");
            DebugUtils.dbgLn("method name: '"+methodName+"'");
            
            try { 
                DebugUtils.dbg("Reading the file...");
                File myObj = new File(path);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    DebugUtils.dbg(".");
                    String data = myReader.nextLine();
                    // System.out.println(data);
                    dataString+=data+"\n";
                }
                DebugUtils.dbgLn("successful");
                myReader.close();

              } catch (FileNotFoundException e) {
                System.out.println("File '"+path+"' not found!");
                DebugUtils.dbgLn("Exiting...");
                return;
              }
        } else{
            DebugUtils.dbgLn("Please provide file path, class name and method name as arguments!");
            DebugUtils.dbgLn("Exiting...");
            return;
        }

        CompilationUnit comp = StaticJavaParser.parse(dataString);
        comp.addImport("java.util.Set");
        comp.addImport("com.mycompany.app.CoverageUtils");
        int branchCounter = 0;
        int condCounter = 0;
        MethodDeclaration testedMethod = null;

        for(MethodDeclaration m : comp.findAll(MethodDeclaration.class)){
            if( m.getNameAsString().equals("coveredCondition")) {
                DebugUtils.dbgLn("The class cannot contain method 'coveredCondition', please change it to other name");
                DebugUtils.dbgLn("Exiting...");
                return;
            }
            else if( m.getNameAsString().equals("coveredBranch")) {
                DebugUtils.dbgLn("The class cannot contain method 'coveredBranch', please change it to other name");
                DebugUtils.dbgLn("Exiting...");
                return;
            }
            else if(m.isPublic() && m.getNameAsString().equals(methodName)){
                testedMethod = m;
            }
        }
        if(testedMethod == null){
            DebugUtils.dbgLn("Method '"+methodName+"' not found");
            DebugUtils.dbgLn("Exiting...");
            return;
        }else{
            DebugUtils.dbgLn("Loaded the method");
        }
        ArrayList<Integer[]> branchPredicateConditions = new ArrayList<Integer[]>();
        int[] result = prepareMethod(testedMethod,comp,branchPredicateConditions);

        branchCounter = result[0];
        condCounter = result[1];


        DebugUtils.dbgLn("Adding required code to the class...");

        // String coveredBranchFuncString = "static void coveredBranch(int id, Set<Integer> coveredBranches) {\n" +
        // "    if (coveredBranches != null && !coveredBranches.contains(id)) {\n"+
        // "        //System.out.println(\"* covered new branch: \" + id);\n"+
        // "        coveredBranches.add(id);\n"+
        // "    }\n"+
        // "}";

        // String coveredConditionFuncString = "static boolean coveredCondition(boolean predicate, int id, Set<Integer> coveredConditions) {\n" +
        // "    if (coveredConditions != null) {\n"+
        // "        if(predicate){\n"+
        // "           if(!coveredConditions.contains(id)){coveredConditions.add(id);}\n"+
        // "        }else{\n"+
        // "           if(!coveredConditions.contains(-id)){coveredConditions.add(-id);}"+
        // "        }"+
        // "    }\n"+
        // "    return predicate;\n"+
        // "}";


        // MethodDeclaration md1 =StaticJavaParser.parseMethodDeclaration(coveredBranchFuncString);

        ClassOrInterfaceDeclaration c = comp.getClassByName(className).get();

        // // add the 2 methods
        // MethodDeclaration newMd = c.addMethod("coveredBranch", Keyword.STATIC);
        // newMd.setBody(md1.getBody().get());
        // newMd.setParameters(md1.getParameters());


        // MethodDeclaration md2 = StaticJavaParser.parseMethodDeclaration(coveredConditionFuncString);

        // newMd = c.addMethod("coveredCondition", Keyword.STATIC);
        // newMd.setType(md2.getType());
        // newMd.setBody(md2.getBody().get());
        // newMd.setModifiers(md2.getModifiers());
        // newMd.setParameters(md2.getParameters());

        // add 2 fields telling number of branches and conditions
        FieldDeclaration fd = c.addPublicField(Integer.class, "numberOfBranches");
        fd.setStatic(true);
        fd.setVariable(0, new VariableDeclarator(fd.getElementType(), "numberOfBranches = "+branchCounter));

        fd = c.addPublicField(Integer.class, "numberOfConditions");
        fd.setStatic(true);
        fd.setVariable(0, new VariableDeclarator(fd.getElementType(), "numberOfConditions = "+condCounter));

        // add field telling which conditions belong to which branch predicates
        String predicateConditionsString = "{";
        for(int i=0;i<branchPredicateConditions.size();i++){
            predicateConditionsString+="{";
            Integer[] predicate = branchPredicateConditions.get(i);
            if(predicate.length>0){
                for(int j=0;j<predicate.length;j++){
                    predicateConditionsString+=predicate[j];
                    if(j<predicate.length-1)
                        predicateConditionsString+=", ";
                }
            }
            predicateConditionsString+="}";
            if(i<branchPredicateConditions.size()-1)
                predicateConditionsString+=", ";
        }
        predicateConditionsString += "}";

        fd = c.addPublicField("Integer", "branchesPredicatesConditions");
        fd.setStatic(true);
        fd.setVariable(0, new VariableDeclarator(fd.getElementType(), "branchesPredicatesConditions[][] = "+predicateConditionsString));

        comp.removePackageDeclaration();
        comp.setPackageDeclaration("input");

        // save the class to a file
        File file = new File(".\\src\\main\\java\\input\\"+className+".java");
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(comp.toString());
            writer.close();
            DebugUtils.dbgLn("Saved the file successfully.");
        } catch (IOException e) {
            DebugUtils.dbgLn("An error occured during saving the file");
            e.printStackTrace();
            DebugUtils.dbgLn("Exiting...");
            return;
        }
        
        DebugUtils.dbgLn("Finished. Next step is using 'GenerateTests', please refer to the manual in Readme.md .");

    }
}
