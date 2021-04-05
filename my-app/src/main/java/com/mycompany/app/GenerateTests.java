package com.mycompany.app;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.print.attribute.standard.NumberOfInterveningJobs;

// import com.mycompany.app.DebugUtils;
// import input.*;


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
        //  System.out.println("testing "+m.getName()+" against "+methodName);
            if(m.getName().equals(methodName) && Modifier.isPublic(m.getModifiers())){
                meth = m;
                break;
            }
        }

        //Class inst = new cls.getClass();
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
                //DebugUtils.dbgLn(argsTypes[i].getName());
                String paramType = argsTypes[i].getName();
                paramTypes.add(paramType);
                if(!(paramType.equals("int") || paramType.equals("double") || paramType.equals("boolean") || 
                paramType.equals("java.lang.Boolean")) ){
                    DebugUtils.dbgLn("Unsupported parameter type '" +paramType+"'");
                    DebugUtils.dbgLn("Supported types: int, double, boolean, java.lang.Boolean");
                    DebugUtils.dbgLn("Exiting...");
                    return;
                }
                // DebugUtils.dbgLn(requiredArgs[i].getClass().);
            }
            DebugUtils.dbgLn("Parameters OK");
            DebugUtils.dbgLn("Number of parameters: "+numberOfParams);
            DebugUtils.dbgLn("Parameters types in order: "+paramTypes);

            Scanner keyboard = new Scanner(System.in);
            DebugUtils.dbgLn("   Options:");
            DebugUtils.dbgLn("- (1) Enter '1' if random search with upper/lower bounds should be applied");
            DebugUtils.dbgLn("- (2) Enter '2137' otherwise");
            String keyInput = keyboard.nextLine();
            
            if(keyInput.equals("1")){
                DebugUtils.dbgLn("(1) chosen");
                randomSearch(meth, paramTypes, paramNames);
            }else{
                DebugUtils.dbgLn("(2) chosen");
            }
            keyboard.close();
        }

        
        DebugUtils.dbgLn("Finished");
    }

    static void randomSearch(Method meth, ArrayList<String> paramTypes, ArrayList<String> paramNames){
        Scanner keyboard = new Scanner(System.in);
        int numberOfParams = paramTypes.size();
        int[] lowerBoundsInts = new int[numberOfParams];
        int[] upperBoundsInts = new int[numberOfParams];
        double[] lowerBoundsDouble = new double[numberOfParams];
        double[] upperBoundsDouble = new double[numberOfParams];


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
                    }else if(type.equals("double")){
                        DebugUtils.dbgLn("Please provide lower double bound");
                        lowerBoundsDouble[i] = keyboard.nextDouble();
                        DebugUtils.dbgLn("Please provide upper double bound");
                        upperBoundsDouble[i] = keyboard.nextDouble();
                    }else if(type.equals("boolean")||type.equals("java.lang.Boolean")){
                        DebugUtils.dbgLn("Next type is boolean, no need for lower/upper bounds");
                    }
                
            }
            DebugUtils.dbgLn("Please specify number of iterations:");
            numOfIterations = keyboard.nextInt();

        } catch (InputMismatchException e) {
            DebugUtils.dbgLn("Typed wrong value type");
            DebugUtils.dbgLn("Exiting...");
            keyboard.close();
            return;
        }

        TreeSet<Integer> coveredBranches = new TreeSet<Integer>();
        TreeSet<Integer> coveredConditions = new TreeSet<Integer>();




        DebugUtils.dbgLn("All set, performing search...");

        int[] chosenInts = new int[numberOfParams];
        double[] chosenDoubles = new double[numberOfParams];
        boolean[] chosenBools = new boolean[numberOfParams];

        for(int i=0;i<numOfIterations;i++){
            DebugUtils.dbgLn(".");
            for(int j = 0;j<numberOfParams;j++){
                String type = paramTypes.get(j);

                if(type.equals("int")){
                    chosenInts[j] = (int) getRandomDouble(lowerBoundsInts[j],upperBoundsInts[j]);
                    DebugUtils.dbgLn("Chosen int for "+paramNames.get(j)+": "+chosenInts[j]);
                }else if(type.equals("double")){
                    chosenDoubles[j] = getRandomDouble(lowerBoundsDouble[j],upperBoundsDouble[j]);
                    DebugUtils.dbgLn("Chosen double for "+paramNames.get(j)+": "+chosenDoubles[j]);
                }else if(type.equals("boolean")||type.equals("java.lang.Boolean")){
                    chosenBools[j] = Math.random()>0.5;
                    DebugUtils.dbgLn("Chosen bool for "+paramNames.get(j)+": "+chosenBools[j]);
                }
            }


        }



        keyboard.close();
    }
    static double getRandomDouble(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }
}
