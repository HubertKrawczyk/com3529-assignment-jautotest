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

        if (args.length != 2) {
            DebugUtils.dbgLn("Please provide class name and method name as arguments!");
            DebugUtils.dbgLn("Exiting...");
            return;
        }
        DebugUtils.dbgLn("GenerateTests start for arguments:");
        String originalClassName = args[0];
        DebugUtils.dbgLn("className: " + originalClassName);

        String className = "input." + originalClassName;

        String methodName = args[1];
        DebugUtils.dbgLn("methodName: " + methodName);
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
        DebugUtils.dbgLn("Successfully accessed class '" + originalClassName + "'");

        // access the method
        Method[] methods = cls.getDeclaredMethods();
        Method meth = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName) && Modifier.isPublic(m.getModifiers())) {
                meth = m;
                break;
            }
        }

        if (meth == null) {
            DebugUtils.dbgLn("public method '" + methodName + "'not found!");
            DebugUtils.dbgLn("Exiting...");
            return;
        } else {
            DebugUtils.dbgLn("Successfully accessed public method '" + methodName + "', parameter check...");
            if (!Modifier.isStatic(meth.getModifiers()));
                DebugUtils.dbgLn("Method is not static, providing constructor parameters neccessary");
            Class<?>[] argsTypes = meth.getParameterTypes();
            Parameter[] params = meth.getParameters();
            ArrayList<String> paramNames = new ArrayList<String>();
            ArrayList<String> paramTypes = new ArrayList<String>();

            int numberOfParams = argsTypes.length - 2; // ignore the last 2 added during parsing

            for (int i = 0; i < numberOfParams; i++) {
                paramNames.add(params[i].toString());
            }

            for (int i = 0; i < numberOfParams; i++) {
                String paramType = argsTypes[i].getName();
                paramTypes.add(paramType);
                if (!(paramType.equals("int") || paramType.equals("double") || paramType.equals("boolean")
                        || paramType.equals("java.lang.Boolean") || paramType.equals("java.lang.String"))) {
                    DebugUtils.dbgLn("Unsupported parameter type '" + paramType + "'");
                    DebugUtils.dbgLn("Supported types: int, double, boolean, java.lang.Boolean, java.lang.String");
                    DebugUtils.dbgLn("Exiting...");
                    return;
                }
            }
            DebugUtils.dbgLn("Parameters OK");
            DebugUtils.dbgLn("Number of parameters: " + numberOfParams);
            DebugUtils.dbgLn("Parameters types in order: " + paramTypes);

            Scanner keyboard = new Scanner(System.in);
            DebugUtils.dbgLn("   * Options: *");
            DebugUtils.dbgLn("- (1) Enter '1' if random search with upper/lower bounds should be applied");
            DebugUtils.dbgLn("- * end of options *");
            String keyInput = keyboard.nextLine();

            boolean result = false;
            Search search;
            if (keyInput.equals("1")) {
                DebugUtils.dbgLn("(1) chosen");
                search = new RandomSearch(meth, cls);
                result = search.search(meth, cls, paramTypes, paramNames);
                if (!result) {
                    return;
                }
                result = generateTests(meth, cls, paramTypes, search.getSuccessfulInputs(),
                        search.getSuccessfulOutputs(), search.getCoveredBranches(), search.getCoveredConditions());
            } else {
                DebugUtils.dbgLn("No such option");
                DebugUtils.dbgLn("Exiting...");
                keyboard.close();
                return;
            }

            keyboard.close();
            if (!result) {
                return;
            }

        }

        DebugUtils.dbgLn("Finished.");
    }

    public enum StringCharTypes {
        ALPHABET, ALPHABET_AND_DIGITS, DIGITS, ASCII
    }

    /**
     * displays information about the achieved coverages and creates unit tests file
     */
    static boolean generateTests(Method meth, Class<?> cls, ArrayList<String> paramTypes, ArrayList<Object[]> inputs,
            ArrayList<Object> outputs, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        DebugUtils.dbgLn("--- Information on result achieved ---");
        String resultInfo = "\n";
        try {
            int numOfBranches = (int) cls.getDeclaredField("numberOfBranches").get(null);

            int numOfConditions = (int) cls.getDeclaredField("numberOfConditions").get(null) * 2;

            int numOfTests = inputs.size();

            resultInfo += "Branch coverage: " + (coveredBranches.size() / (double) numOfBranches) * 100 + "% ("
                    + coveredBranches.size() + "/" + numOfBranches + ")\n";
            resultInfo += "Condition coverage: " + (coveredConditions.size() / (double) numOfConditions) * 100 + "% ("
                    + coveredConditions.size() + "/" + numOfConditions + ")\n";
            resultInfo += "Number of tests generated: " + numOfTests + "\n";

            DebugUtils.dbgLn(resultInfo);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return false;
        }

        DebugUtils.dbgLn("--- End ---\n");

        DebugUtils.dbgLn("Creating unit test file...");

        // work on a a "blueaprint", add test cases
        String dataString = "package com.mycompany.app; // please remove/change this line if used outside of this application\n"
                +

                "import static org.junit.Assert.assertTrue;\n" +

                "import org.junit.Test;\n" +

                "/*lol*/\npublic class " + cls.getSimpleName() + "Test{}";

        CompilationUnit comp = StaticJavaParser.parse(dataString);
        ClassOrInterfaceDeclaration testCls = comp.getClassByName(cls.getSimpleName() + "Test").get();

        resultInfo += "Test file generated automatically, please check the assertations.\n";
        Comment clsComment = testCls.getComment().get();
        clsComment.setContent(resultInfo);
        testCls.setComment(clsComment);

        for (int i = 0; i < inputs.size(); i++) {
            String methodString = "public void test" + (i + 1) + "()\n" + "{\n";

            // first one will work on original code, second on the parsed code used for
            // generating test cases
            String testedMethodCallOriginal = cls.getSimpleName() + "." + meth.getName() + "(";
            String testedMethodCall;
            for (int j = 0; j < inputs.get(i).length - 2; j++) { // iterate over num of parameters
                if (paramTypes.get(j).equals("java.lang.String"))
                    testedMethodCallOriginal += "\"" + inputs.get(i)[j] + "\"";
                else
                    testedMethodCallOriginal += inputs.get(i)[j];
                if (j != inputs.get(i).length - 3) {
                    testedMethodCallOriginal += ", ";
                }
            }
            testedMethodCall = testedMethodCallOriginal + ", null, null);\n";
            testedMethodCallOriginal += ");\n";
            methodString += "//Object result = " + testedMethodCallOriginal;
            methodString += "Object result = input." + testedMethodCall;
            methodString += "// remove the line above and uncomment upper one to test original code\n";

            if (meth.getReturnType().isPrimitive() || meth.getReturnType().getName().equals("java.lang.Integer")
                    || meth.getReturnType().getName().equals("java.lang.Boolean")) {
                methodString += "assertTrue(result.equals(" + outputs.get(i) + "));\n";
            } else if (meth.getReturnType().isEnum()) {

                String enumName = meth.getReturnType().getCanonicalName();
                // DebugUtils.dbgLn(meth.getReturnType().getPackageName());
                if (meth.getReturnType().getPackageName().equals("input")) {
                    methodString += "//assertTrue(result.equals(" + enumName.substring(6) + "." + outputs.get(i)
                            + "))\n";
                    methodString += "assertTrue(result.equals(" + enumName + "." + outputs.get(i) + "));\n";
                    methodString += "// remove the line above and uncomment upper one to test original code\n";
                } else {
                    methodString += "assertTrue(result.equals(" + enumName + "." + outputs.get(i) + "));\n";
                }

            } else if (meth.getReturnType().getName().equals("java.lang.String")) {
                methodString += "assertTrue(result.equals(\"" + outputs.get(i) + "\"));\n";
            } else {
                methodString += "//assertTrue(result.equals(" + outputs.get(i) + "));\n";
            }

            methodString += "}";

            MethodDeclaration md = StaticJavaParser.parseMethodDeclaration(methodString);
            MethodDeclaration newMd = testCls.addMethod("test" + (i + 1), Keyword.PUBLIC);
            newMd.setBody(md.getBody().get());
            newMd.addAnnotation("Test");

        }

        // create the file
        File file = new File(".\\src\\test\\java\\com\\mycompany\\app\\" + cls.getSimpleName() + "Test.java");
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(comp.toString());
            writer.close();
            DebugUtils.dbgLn("Saved the file successfully.");
            DebugUtils.dbgLn("File location: " + file.getAbsolutePath());
        } catch (IOException e) {
            DebugUtils.dbgLn("An error occured during saving the file");
            e.printStackTrace();
            DebugUtils.dbgLn("Exiting...");
            return false;
        }
        return true;
    }

}
