package com.mycompany.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;

public class GenerateTests {

    public static void main(String[] args) {
        
        if(args.length != 2){
            DebugUtils.dbgLn("Please provide class name and method name as arguments!");
            DebugUtils.dbgLn("Exiting...");
            return;
        }
        DebugUtils.dbgLn("GenerateTests start for arguments:");
        String originalClassName = args[0];
        DebugUtils.dbgLn("className: "+originalClassName);

        String className = "input." + originalClassName;

        String methodName = args[1];
        DebugUtils.dbgLn("methodName: "+methodName);
        DebugUtils.dbgLn("");

        // access the class
        Class<?> cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            DebugUtils.dbgLn("Invalid className, please parse the class first!");
            DebugUtils.dbgLn("Exiting...");
            return;
        }
        DebugUtils.dbgLn("Successfully accessed class '"+originalClassName+"'");

        // access the method
        Method[] methods = cls.getDeclaredMethods();
        Method meth = null;
        for(Method m : methods){
            if(m.getName().equals(methodName) && Modifier.isPublic(m.getModifiers())){
                meth = m;
                break;
            }
        }

        if(meth==null){
            DebugUtils.dbgLn("Method '"+methodName+"'not found!");
            DebugUtils.dbgLn("Exiting...");
            return;
        }else{
            DebugUtils.dbgLn("Successfully accessed method '"+methodName+"', parameter check...");
            Class<?>[] argsTypes = meth.getParameterTypes();
            Parameter[] params = meth.getParameters();
            ArrayList<String> paramNames = new ArrayList<String>();
            ArrayList<String> paramTypes = new ArrayList<String>();

            int numberOfParams = argsTypes.length - 2; // ignore the last 2 added during parsing

            for (int i = 0;i < numberOfParams;i++){
                paramNames.add(params[i].toString());
            }

           
            
            for(int i = 0;i < numberOfParams;i++){
                String paramType = argsTypes[i].getName();
                paramTypes.add(paramType);
                if(!(paramType.equals("int") || paramType.equals("double") || paramType.equals("boolean") || 
                paramType.equals("java.lang.Boolean") || paramType.equals("java.lang.String"))){
                    DebugUtils.dbgLn("Unsupported parameter type '" +paramType+"'");
                    DebugUtils.dbgLn("Supported types: int, double, boolean, java.lang.Boolean, java.lang.String");
                    DebugUtils.dbgLn("Exiting...");
                    return;
                }
            }
            DebugUtils.dbgLn("Parameters OK");
            DebugUtils.dbgLn("Number of parameters: "+numberOfParams);
            DebugUtils.dbgLn("Parameters types in order: "+paramTypes);

            Scanner keyboard = new Scanner(System.in);
            DebugUtils.dbgLn("   * Options: *");
            DebugUtils.dbgLn("- (1) Enter '1' if random search with upper/lower bounds should be applied");
            DebugUtils.dbgLn("- * end of options *");
            String keyInput = keyboard.nextLine();
            
            boolean result = false;
            if(keyInput.equals("1")){
                DebugUtils.dbgLn("(1) chosen");
                result = randomSearch(meth, cls, paramTypes, paramNames);
            }else{
                DebugUtils.dbgLn("No such option");
                DebugUtils.dbgLn("Exiting...");
                keyboard.close();
                return;
            }
            
            keyboard.close();
            if(!result){
                return; 
            }

        }

        
        DebugUtils.dbgLn("Finished.");
    }

    public enum StringCharTypes {
        ALPHABET,
        ALPHABET_AND_DIGITS,
        DIGITS,
        ASCII
    }

    /**
     * ask for lower/upper bounds to generate random values
     * method is tested with random values, inputs that add to the coverage are saved
     * and later used by "generateTests" to create file with unit tests
     */
    static boolean randomSearch(Method meth,  Class<?> cls, ArrayList<String> paramTypes, ArrayList<String> paramNames){
        Scanner keyboard = new Scanner(System.in);
        int numberOfParams = paramTypes.size();
        int[] lowerBoundsInts = new int[numberOfParams];
        int[] upperBoundsInts = new int[numberOfParams];
        double[] lowerBoundsDouble = new double[numberOfParams];
        double[] upperBoundsDouble = new double[numberOfParams];
        StringCharTypes[] stringCharTypes = new StringCharTypes[numberOfParams];


        int numOfIterations = 0;
        try {
            for(int i = 0;i<numberOfParams;i++){
                String type = paramTypes.get(i);
                DebugUtils.dbgLn("Parameter "+(i+1) + "/"+(numberOfParams)+" '"+paramNames.get(i)+"' is of type: "+type);
                    
                    if(type.equals("int")){
                        DebugUtils.dbgLn("Please provide lower int bound");
                        lowerBoundsInts[i] = keyboard.nextInt();
                        DebugUtils.dbgLn("Please provide upper int bound");
                        upperBoundsInts[i] = keyboard.nextInt();
                        if(lowerBoundsInts[i]>upperBoundsInts[i]){
                            DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
                            i--;
                        }
                    }else if(type.equals("double")){
                        DebugUtils.dbgLn("Please provide lower double bound");
                        lowerBoundsDouble[i] = keyboard.nextDouble();
                        DebugUtils.dbgLn("Please provide upper double bound");
                        upperBoundsDouble[i] = keyboard.nextDouble();
                        if(lowerBoundsDouble[i]>upperBoundsDouble[i]){
                            DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
                            i--;
                        }
                    }else if(type.equals("boolean")||type.equals("java.lang.Boolean")){
                        DebugUtils.dbgLn("Next type is boolean, no need for lower/upper bounds");
                    }else if(type.equals("java.lang.String")){
                        DebugUtils.dbgLn("Next type is java.lang.String, Strings will be generated randomly, character range as well as min/max string lengths need to be specified.");
                        DebugUtils.dbgLn("Please character types:\n 1 -> alphabet(upper and lowercase),\n 2 -> alphabet with digits,\n 3 -> digits,\n 4 -> ASCII characters");
                        int choice = keyboard.nextInt();
                        switch(choice){
                            case 1:
                            stringCharTypes[i] = StringCharTypes.ALPHABET;
                            break;
                            case 2:
                            stringCharTypes[i] = StringCharTypes.ALPHABET_AND_DIGITS;
                            break;
                            case 3:
                            stringCharTypes[i] = StringCharTypes.DIGITS;
                            break;
                            case 4:
                            stringCharTypes[i] = StringCharTypes.ASCII;
                            break;
                            default:
                            DebugUtils.dbgLn("Error: invalid choice");
                            i--;
                        }
                        if(stringCharTypes[i]!=null){
                            DebugUtils.dbgLn("Please provide min string lenght value");
                            lowerBoundsInts[i] = keyboard.nextInt();
                            DebugUtils.dbgLn("Please provide max string lenght value");
                            upperBoundsInts[i] = keyboard.nextInt();
                            if(lowerBoundsInts[i]>upperBoundsInts[i]){
                                DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
                                i--;
                            }else if(lowerBoundsInts[i]<0){
                                DebugUtils.dbgLn("Error: lower bound cannot be less than 0!");
                                i--;
                            }
                        }
                    }
                
            }
            DebugUtils.dbgLn("Please specify number of iterations:");
            numOfIterations = keyboard.nextInt();
            if(numOfIterations<0){
                DebugUtils.dbgLn("Error: number of iterations must be positive!");
                DebugUtils.dbgLn("Exiting...");
                keyboard.close();
                return false;
            }

        } catch (InputMismatchException e) {
            DebugUtils.dbgLn("Typed wrong value type");
            DebugUtils.dbgLn("Exiting...");
            keyboard.close();
            return false;
        }

        TreeSet<Integer> coveredBranches = new TreeSet<Integer>();
        TreeSet<Integer> coveredConditions = new TreeSet<Integer>();
        int oldCoveredBranchesSize = 0;
        int oldCoveredConditionsSize = 0;
        ArrayList<Object[]> successfulInputs = new ArrayList<Object[]>();
        ArrayList<Object> successfulOutputs= new ArrayList<Object>();

        DebugUtils.dbgLn("All set, performing search...");

        int actualNumberOfParams = numberOfParams+2;

        int progressBarSteps = 30;
        boolean showProgressbar = numOfIterations>10000;
        for(int i=0;i<numOfIterations;i++){
            Object[] arguments = new Object[actualNumberOfParams];
            for(int j = 0;j<numberOfParams;j++){
                String type = paramTypes.get(j);

                if(type.equals("int")){
                    arguments[j] = (int) getRandomDouble(lowerBoundsInts[j],upperBoundsInts[j]);
                }else if(type.equals("double")){
                    arguments[j] = getRandomDouble(lowerBoundsDouble[j],upperBoundsDouble[j]);
                }else if(type.equals("boolean")||type.equals("java.lang.Boolean")){
                    arguments[j] = Math.random()>0.5;
                }else if(type.equals("java.lang.String")){
                    arguments[j] = genRandomString(stringCharTypes[j], lowerBoundsInts[j],upperBoundsInts[j]);
                }
            }
            arguments[actualNumberOfParams-2] = coveredBranches;
            arguments[actualNumberOfParams-1] = coveredConditions;
            Object invokeResult;
            try {
                invokeResult = meth.invoke(null, arguments);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                keyboard.close();
                return false;
            }
            if(coveredBranches.size()>oldCoveredBranchesSize || coveredConditions.size()>oldCoveredConditionsSize ){
                oldCoveredBranchesSize=coveredBranches.size();
                oldCoveredConditionsSize=coveredConditions.size();
                successfulInputs.add(arguments);
                successfulOutputs.add(invokeResult);
            }
            if(showProgressbar && i % 1000 == 0){
                double progress = (i+1)/(double)numOfIterations;
                int barsCompleted = (int)(progressBarSteps*progress);
                DebugUtils.dbg("["+"=".repeat(barsCompleted)+" ".repeat(progressBarSteps - barsCompleted)+"] "+
                    String.format("%.2f",progress*100.0,2) +"%\r");
            }
            
            
        }
        DebugUtils.dbgLn("\n");

        DebugUtils.dbgLn("Search complete");
        keyboard.close();

        return generateTests(meth, cls,paramTypes, successfulInputs,successfulOutputs, coveredBranches,coveredConditions);
    }

    private static String genRandomString(StringCharTypes stringCharType, int min, int max) {
        String result="";
        String range = "";
        switch (stringCharType){
            case ALPHABET:
            range = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            break;
            case ALPHABET_AND_DIGITS:
            range = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            break;
            case DIGITS:
            range = "0123456789";
            break;
        }
        int stringLen = (int)getRandomDouble(min,max);
        for(int i=0;i<stringLen;i++){
            if(stringCharType == StringCharTypes.ASCII){
                char c = (char)(int)getRandomDouble(32, 126);
                if(c == '"'||c == '\\'){
                    result+="\\";
                };
                result+=c;
            }else{
                result+=range.charAt((int)getRandomDouble(0, range.length()-1));
            }
        }
        return result;
    }

    /** 
     * displays information about the achieved coverages and creates unit tests file
     */
    static boolean generateTests(Method meth,  Class<?> cls,ArrayList<String> paramTypes,ArrayList<Object[]> inputs, ArrayList<Object> outputs,
    TreeSet<Integer> coveredBranches, TreeSet<Integer> coveredConditions){
        DebugUtils.dbgLn("--- Information on result achieved ---");
        String resultInfo = "\n";
        try {
            int numOfBranches = (int)cls.getDeclaredField("numberOfBranches").get(null);

            int numOfConditions = (int)cls.getDeclaredField("numberOfConditions").get(null)*2;

            int numOfTests = inputs.size();

            resultInfo+="Branch coverage: "+(coveredBranches.size()/(double)numOfBranches)*100 +"% ("+coveredBranches.size()+"/"+numOfBranches+")\n";
            resultInfo+="Condition coverage: "+(coveredConditions.size()/(double)numOfConditions)*100 +"% ("+coveredConditions.size()+"/"+numOfConditions+")\n";
            resultInfo+="Number of tests generated: "+numOfTests+"\n";


            DebugUtils.dbgLn(resultInfo);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return false;
        }

        DebugUtils.dbgLn("--- End ---\n");

        DebugUtils.dbgLn("Creating unit test file...");

        // work on a a "blueaprint", add test cases
        String dataString = 
        "package com.mycompany.app; // please remove/change this line if used outside of this application\n"+

        "import static org.junit.Assert.assertTrue;\n"+
        
        "import org.junit.Test;\n"+
        
        "/*lol*/\npublic class " +cls.getSimpleName()+"Test{}";

        CompilationUnit comp = StaticJavaParser.parse(dataString);
        ClassOrInterfaceDeclaration testCls = comp.getClassByName(cls.getSimpleName()+"Test").get();

        resultInfo+="Test file generated automatically, please check the assertations.\n";
        Comment clsComment = testCls.getComment().get();
        clsComment.setContent(resultInfo);
        testCls.setComment(clsComment);


        for(int i=0;i<inputs.size();i++){
            String methodString =
            "public void test"+(i+1)+"()\n"+
            "{\n";

            // first one will work on original code, second on the parsed code used for generating test cases
            String testedMethodCallOriginal = cls.getSimpleName()+"."+meth.getName()+"(";
            String testedMethodCall;
            for (int j =0;j<inputs.get(i).length-2;j++) { //iterate over num of parameters
                if(paramTypes.get(j).equals("java.lang.String"))
                    testedMethodCallOriginal+="\""+inputs.get(i)[j]+"\"";
                else
                    testedMethodCallOriginal+=inputs.get(i)[j];
                if(j!=inputs.get(i).length-3){
                    testedMethodCallOriginal+=", ";
                }
            }
            testedMethodCall = testedMethodCallOriginal+", null, null);\n";
            testedMethodCallOriginal+=");\n";
            methodString += "//Object result = "+testedMethodCallOriginal;
            methodString += "Object result = input."+testedMethodCall;
            methodString += "// remove the line above and uncomment upper one to test original code\n";

            if(meth.getReturnType().isPrimitive()|| meth.getReturnType().getName().equals("java.lang.Integer")||
            meth.getReturnType().getName().equals("java.lang.Boolean")){
                methodString += "assertTrue(result.equals("+outputs.get(i)+"));\n";
            } else if(meth.getReturnType().isEnum()){
                
                String enumName = meth.getReturnType().getCanonicalName();
                //DebugUtils.dbgLn(meth.getReturnType().getPackageName());
                if(meth.getReturnType().getPackageName().equals("input")){
                    methodString += "//assertTrue(result.equals("+enumName.substring(6)+"."+outputs.get(i)+"))\n";
                    methodString += "assertTrue(result.equals("+enumName+"."+outputs.get(i)+"));\n";
                    methodString += "// remove the line above and uncomment upper one to test original code\n";
                }else{
                    methodString += "assertTrue(result.equals("+enumName+"."+outputs.get(i)+"));\n";
                }
                
            }else if(meth.getReturnType().getName().equals("java.lang.String")){
                methodString += "assertTrue(result.equals(\""+outputs.get(i)+"\"));\n";
            }else{
                methodString += "//assertTrue(result.equals("+outputs.get(i)+"));\n";
            }
            
            
            methodString+="}";


            MethodDeclaration md = StaticJavaParser.parseMethodDeclaration(methodString);
            MethodDeclaration newMd = testCls.addMethod("test"+(i+1),Keyword.PUBLIC);
            newMd.setBody(md.getBody().get());
            newMd.addAnnotation("Test");

        }

        // create the file
        File file = new File(".\\src\\test\\java\\com\\mycompany\\app\\"+cls.getSimpleName()+"Test.java");
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(comp.toString());
            writer.close();
            DebugUtils.dbgLn("Saved the file successfully.");
            DebugUtils.dbgLn("File location: "+file.getAbsolutePath());
        } catch (IOException e) {
            DebugUtils.dbgLn("An error occured during saving the file");
            e.printStackTrace();
            DebugUtils.dbgLn("Exiting...");
            return false;
        }
        return true;
    }
    static double getRandomDouble(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }
}
