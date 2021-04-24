package jautotest.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;


public class GenerateTests {

    public static void main(String[] args) {

        if (args.length != 2) {
            DebugUtils.printLn("ERROR: Please provide class name and method name as arguments!");
            DebugUtils.printLn("README contains neccessary instructions");
            DebugUtils.printLn("Exiting...");
            return;
        }
        DebugUtils.printLn("GenerateTests start for arguments:");
        String originalClassName = args[0];
        DebugUtils.printLn("className: " + originalClassName);

        String className = "input." + originalClassName;
        String methodName = args[1];
        String[] inputMethodParameters = null;

        if (methodName.indexOf("(") != -1) { // user specified method parameters
            inputMethodParameters = methodName.substring(methodName.indexOf("(") + 1, methodName.indexOf(")"))
                    .split(", |,");
            methodName = methodName.substring(0, methodName.indexOf("("));

        }

        DebugUtils.printLn("methodName: " + methodName);
        DebugUtils.printLn("");

        // access the class
        Class<?> cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            DebugUtils.printLn("ERROR: Invalid className, please parse the correct class file first!");
            DebugUtils.printLn("README contains neccessary instructions");
            DebugUtils.printLn("Exiting...");
            return;
        }
        DebugUtils.printLn("Successfully accessed class '" + originalClassName + "'");

        // access the method
        Method[] methods = cls.getDeclaredMethods();
        Method testedMethod = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName) && Modifier.isPublic(m.getModifiers())) {
                if(inputMethodParameters==null) {  
                    testedMethod = m;
                    break;
                } else if (m.getParameters().length - 2 == inputMethodParameters.length) {
                    // test if the parameters match
                    boolean paramsMatch = true;
                    Parameter[] methodParams = m.getParameters();

                    for (int i = 0; i < methodParams.length - 2 ; i++) {
                        if (!methodParams[i].toString().equals(inputMethodParameters[i])) {
                            paramsMatch = false;
                        }
                    }
                    if (paramsMatch) {
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
            DebugUtils.printLn("README contains neccessary instructions");
            DebugUtils.printLn("Exiting...");
            return;
        } else {
            DebugUtils.printLn("Successfully accessed public method '" + methodName + "', parameter check...");
            if (!Modifier.isStatic(testedMethod.getModifiers())){
                DebugUtils.printLn("Method is not static, providing constructor parameters may be neccessary");
            }
            Class<?>[] argsTypes = testedMethod.getParameterTypes();
            Parameter[] params = testedMethod.getParameters();
            ArrayList<String> paramNames = new ArrayList<String>();
            ArrayList<String> paramTypes = new ArrayList<String>();

            int numberOfParams = argsTypes.length - 2; // ignore the last 2 added during parsing

            // check method parameters
            for (int i = 0; i < numberOfParams; i++) {
                paramNames.add(params[i].toString());
                String paramType = argsTypes[i].getName();
                paramTypes.add(paramType);
                if (!(paramType.equals("int") || paramType.equals("double") || paramType.equals("boolean")
                        || paramType.equals("byte") || paramType.equals("short") || paramType.equals("char")
                        || paramType.equals("flaot") || paramType.equals("java.lang.Boolean")
                        || paramType.equals("java.lang.String"))) {
                    DebugUtils.printLn("Unsupported parameter type '" + paramType + "'");
                    DebugUtils.printLn(
                            "Supported types: int, byte, short, char, double, float, boolean, java.lang.Boolean, java.lang.String");
                    if (!argsTypes[i].isPrimitive()) {
                        DebugUtils.printLn("'null' will be used");
                    } else { // long
                        DebugUtils.printLn("'Exiting");
                        return;
                    }

                }
            }

            DebugUtils.printLn("Parameters Checked");
            DebugUtils.printLn("Number of parameters: " + numberOfParams);
            DebugUtils.printLn("Parameters types in order: " + paramTypes);

            Scanner keyboard = new Scanner(System.in);
            DebugUtils.printLn("- (1) Enter '1' if random search with upper/lower bounds should be applied");
            String keyInput = keyboard.nextLine();

            Search search;
            if (keyInput.equals("1")) {
                DebugUtils.printLn("(1) chosen");
                search = new RandomSearch(testedMethod, cls);
                if (!search.search(keyboard)) {
                    keyboard.close();
                    return;
                }
                if(!generateTests(testedMethod, cls, paramTypes, search)){
                    keyboard.close();
                    return;
                }
            } else {
                DebugUtils.printLn("No such option");
                DebugUtils.printLn("Exiting...");
                keyboard.close();
                return;
            }

            keyboard.close();

        }

        DebugUtils.printLn("Finished.");
    }



    /**
     * displays information about the achieved coverages and creates unit tests file
     */
    static boolean generateTests(Method meth, Class<?> cls, ArrayList<String> paramTypes, Search search) {
        ArrayList<Object[]> inputs = search.getSuccessfulInputs();
        ArrayList<Object[]> constructorInputs = search.getSuccessfulConstructorInputs();
        ArrayList<Object> outputs = search.getSuccessfulOutputs();
        Set<Integer> coveredBranches = search.getCoveredBranches();
        Set<Integer> coveredConditions = search.getCoveredConditions();
        Set<Integer> coveredMasterConditions = search.getCoveredMasterConditions();

        boolean isStatic = Modifier.isStatic(meth.getModifiers());

        ArrayList<String> constructorParamTypes = new ArrayList<String>();
        if (!isStatic) {
            Class<?>[] argsTypes = search.getChosenConstructor().getParameterTypes();
            for (int i = 0; i < search.getChosenConstructor().getParameterCount(); i++) {
                constructorParamTypes.add(argsTypes[i].getName());
            }
        }

        DebugUtils.printLn("--- Information on the result achieved ---");
        String resultInfo = "\n";
        try {
            int numOfBranches = (int) cls.getDeclaredField("numberOfBranches").get(null);

            int numOfConditions = (int) cls.getDeclaredField("numberOfConditions").get(null);

            int numOfTests = inputs.size();

            resultInfo += "Branch coverage: "
                    + String.format("%.2f", (coveredBranches.size() / (double) numOfBranches) * 100, 2) + "% ("
                    + coveredBranches.size() + "/" + numOfBranches + ")\n";
            resultInfo += "Branch coverage - covered branches: " + coveredBranches + "\n";

            resultInfo += "\nCondition coverage: "
                    + String.format("%.2f", (coveredConditions.size() / (double) (numOfConditions * 2)) * 100, 2)
                    + "% (" + coveredConditions.size() + "/" + numOfConditions * 2 + ")\n";
            resultInfo += "Condition coverage - covered conditions: " + coveredConditions + "\n";

            resultInfo += "\nCorrelated MCDC coverage: "
                    + String.format("%.2f", (coveredMasterConditions.size() / (double) numOfConditions) * 100, 2)
                    + "% (" + coveredMasterConditions.size() + "/" + numOfConditions + ")\n";
            resultInfo += "Correlated MCDC - covered conditions: " + coveredMasterConditions + "\n";

            resultInfo += "\nNumber of tests generated: " + numOfTests + "\n";

            DebugUtils.printLn(resultInfo);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return false;
        }

        DebugUtils.printLn("--- End ---\n");

        DebugUtils.printLn("Creating unit test file...");
        String testClassName = cls.getSimpleName() + StringUtils.capitalize(meth.getName()) + "Test";
        // work on a a basic class, add test cases
        String dataString = 
                "package com.mycompany.app; // please remove/change this line if used outside of this application\n"+
                "import static org.junit.Assert.assertTrue;\n" +
                "import org.junit.Test;\n" +
                "public class " + testClassName + "{}";

        CompilationUnit comp = StaticJavaParser.parse(dataString);
        ClassOrInterfaceDeclaration testCls = comp.getClassByName(testClassName).get();

        // https://stackabuse.com/how-to-get-current-date-and-time-in-java/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        resultInfo += "Test file generated automatically at "+ formatter.format(date) + ", please check the assertations.\n";
        Comment clsComment = new BlockComment();
        clsComment.setContent(resultInfo);
        testCls.setComment(clsComment);

        // iterate over test cases
        for (int i = 0; i < inputs.size(); i++) {
            String methodString = "public void test" + (i + 1) + "()\n" + "{\n";

            if (!isStatic) { // need to use constructor
                methodString += "//" + cls.getSimpleName() + " testedObject = new " + cls.getSimpleName() + "(";
                methodString += StringUtils.paramsIntoString(constructorInputs.get(i), constructorParamTypes);
                methodString += ");\n";
                methodString += cls.getName() + " testedObject = new " + cls.getName() + "(";
                methodString += StringUtils.paramsIntoString(constructorInputs.get(i), constructorParamTypes);
                methodString += ");\n";
            }

            // first call (testedMethodCallOriginal) will work with the original code
            // second call (testedMethodCall) will work with the parsed code (with "input."
            // prefix) and require 2 more parameters (which can be null)
            String testedMethodCallOriginal;
            String testedMethodCall;
            if (!isStatic) {
                testedMethodCallOriginal = "testedObject" + "." + meth.getName() + "(";
            } else {
                testedMethodCallOriginal = cls.getSimpleName() + "." + meth.getName() + "(";
            }
            Object[] arguments = inputs.get(i);
            testedMethodCallOriginal += StringUtils.paramsIntoString(Arrays.copyOfRange(arguments , 0, arguments.length - 2),
                    paramTypes);

            testedMethodCall = testedMethodCallOriginal + ", null, null);\n";
            testedMethodCallOriginal += ");\n";

            methodString += "//Object result = " + testedMethodCallOriginal;
            if (isStatic) {
                methodString += "Object result = input." + testedMethodCall;
            } else {
                methodString += "Object result = " + testedMethodCall;
            }
            methodString += "// remove the line above and uncomment upper one to test original code\n";

            // asertion
            if (meth.getReturnType().getName().equals("char")) {
                methodString += "assertTrue(result.equals((char)" + (int) (char) outputs.get(i) + "));\n";

            } else if (meth.getReturnType().isPrimitive() || meth.getReturnType().getName().equals("java.lang.Integer")
                    || meth.getReturnType().getName().equals("java.lang.Boolean")) {
                methodString += "assertTrue(result.equals(" + outputs.get(i) + "));\n";

            } else if (meth.getReturnType().isEnum()) {
                String enumName = meth.getReturnType().getCanonicalName();
                if (meth.getReturnType().getPackageName().equals("input")) {
                    methodString += "//assertTrue(result.equals(" + enumName.substring(6) + "." + outputs.get(i)
                            + "))\n";
                    methodString += "assertTrue(result.equals(" + enumName + "." + outputs.get(i) + "));\n";
                    methodString += "// remove the line above and uncomment upper one to test original code\n";

                } else {
                    methodString += "assertTrue(result.equals(" + enumName + "." + outputs.get(i) + "));\n";
                }

            } else if (meth.getReturnType().getName().equals("java.lang.String")) {
                methodString += "assertTrue(result.equals(\"" + StringUtils.escapeString((String) outputs.get(i)) + "\"));\n";

            } else { // not supported type, probably some object, comment it
                methodString += "//assertTrue(result.equals(" + outputs.get(i) + "));\n";
            }

            methodString += "}";

            MethodDeclaration md = StaticJavaParser.parseMethodDeclaration(methodString);
            MethodDeclaration newMd = testCls.addMethod("test" + (i + 1), Keyword.PUBLIC);
            newMd.setBody(md.getBody().get());
            newMd.addAnnotation("Test");

        }

        // create the file
        char separator = File.separatorChar;
        File file = new File("."+separator+"src"+separator+"test"+separator+"java"+separator+"com"+separator+"mycompany"+separator+"app"+separator + testClassName + ".java");
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(comp.toString());
            writer.close();
            DebugUtils.printLn("Saved the file successfully.");
            DebugUtils.printLn("File location: " + file.getAbsolutePath());
        } catch (IOException e) {
            DebugUtils.printLn("ERROR: An error occured during saving the file");
            e.printStackTrace();
            DebugUtils.printLn("Exiting...");
            return false;
        }
        return true;
    }

}
