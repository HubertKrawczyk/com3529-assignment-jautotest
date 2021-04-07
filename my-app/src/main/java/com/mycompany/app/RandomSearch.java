package com.mycompany.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

public class RandomSearch extends Search {

  public RandomSearch(Method testedMethod, Class<?> testedClass) {
    super(testedMethod, testedClass);
  }

  int readValues(Scanner keyboard, ArrayList<String> paramTypes, ArrayList<String> paramNames,int numberOfParams, int[] lowerBoundsInts, int[] upperBoundsInts,
  double[] lowerBoundsDouble, double[] upperBoundsDouble, StringCharTypes[] stringCharTypes){
    return readValues(keyboard, paramTypes, paramNames, numberOfParams, lowerBoundsInts, upperBoundsInts,
    lowerBoundsDouble, upperBoundsDouble, stringCharTypes, false);
  }
  int readValues(Scanner keyboard, ArrayList<String> paramTypes, ArrayList<String> paramNames,int numberOfParams, int[] lowerBoundsInts, int[] upperBoundsInts,
  double[] lowerBoundsDouble, double[] upperBoundsDouble, StringCharTypes[] stringCharTypes, boolean forConstructor){
    int numOfIterations = 1;
    try {
      for (int i = 0; i < numberOfParams; i++) {
        String type = paramTypes.get(i);
        DebugUtils.dbgLn(
            "Parameter " + (i + 1) + "/" + (numberOfParams) + " '" + paramNames.get(i) + "' is of type: " + type);

        if (type.equals("int") || type.equals("byte") || type.equals("short") || type.equals("Integer")) {
          if(type.equals("byte") || type.equals("short")){
            DebugUtils.dbgLn("Int will be used to store byte/short/Integer value please use correct min/max values");
          }
          DebugUtils.dbgLn("Please provide lower int bound");
          lowerBoundsInts[i] = keyboard.nextInt();
          DebugUtils.dbgLn("Please provide upper int bound");
          upperBoundsInts[i] = keyboard.nextInt();
          if (lowerBoundsInts[i] > upperBoundsInts[i]) {
            DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
            i--;
          }
        } else if (type.equals("double") || type.equals("float")){
          if(type.equals("float")){
            DebugUtils.dbgLn("Double will be used to store float value please use correct min/max values");
          }
          DebugUtils.dbgLn("Please provide lower double bound");
          lowerBoundsDouble[i] = keyboard.nextDouble();
          DebugUtils.dbgLn("Please provide upper double bound");
          upperBoundsDouble[i] = keyboard.nextDouble();
          if (lowerBoundsDouble[i] > upperBoundsDouble[i]) {
            DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
            i--;
          }
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
          DebugUtils.dbgLn("Next type is boolean, no need for lower/upper bounds");
        } else if (type.equals("java.lang.String")) {
          DebugUtils.dbgLn(
              "Next type is java.lang.String, Strings will be generated randomly, character range as well as min/max string lengths need to be specified.");
          DebugUtils.dbgLn(
              "Please character types:\n 1 -> alphabet(upper and lowercase),\n 2 -> alphabet with digits,\n 3 -> digits,\n 4 -> ASCII characters");
          int choice = keyboard.nextInt();
          switch (choice) {
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
          if (stringCharTypes[i] != null) {
            DebugUtils.dbgLn("Please provide min string lenght value");
            lowerBoundsInts[i] = keyboard.nextInt();
            DebugUtils.dbgLn("Please provide max string lenght value");
            upperBoundsInts[i] = keyboard.nextInt();
            if (lowerBoundsInts[i] > upperBoundsInts[i]) {
              DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
              i--;
            } else if (lowerBoundsInts[i] < 0) {
              DebugUtils.dbgLn("Error: lower bound cannot be less than 0!");
              i--;
            }
          }
        } else if (type.equals("char")) {
          DebugUtils.dbgLn("Please provide lower int bound >= 32 (int will be casted to char)");
          lowerBoundsInts[i] = keyboard.nextInt();
          DebugUtils.dbgLn("Please provide upper int bound <= 1023(int will be casted to char)");
          upperBoundsInts[i] = keyboard.nextInt();
          if (lowerBoundsInts[i] > upperBoundsInts[i]) {
            DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
            i--;
          }
          else if(lowerBoundsInts[i]<32 ||  upperBoundsInts[i]>1023){
            DebugUtils.dbgLn("lower bound needs to be >= 32 and upper bound <= 1023");
            i--;
          }
        } else {
          DebugUtils.dbgLn(
            "Unsupported type: '"+type+"', 'null' will be used");
        }

      }
      if(!forConstructor){
        DebugUtils.dbgLn("Please specify number of iterations:");
        numOfIterations = keyboard.nextInt();
        if (numOfIterations < 0) {
          DebugUtils.dbgLn("Error: number of iterations must be positive!");
          DebugUtils.dbgLn("Exiting...");
          return -1;
        }
      }

    } catch (InputMismatchException e) {
      DebugUtils.dbgLn("Typed wrong value type");
      DebugUtils.dbgLn("Exiting...");
      return -1;
    }
    return numOfIterations;
  }

  /**
   * ask for lower/upper bounds to generate random values method is tested with
   * random values, inputs that add to the coverage are saved and later used by
   * "generateTests" to create file with unit tests
   */
  @Override
  public boolean search(Scanner keyboard, Method meth, Class<?> cls, ArrayList<String> paramTypes, ArrayList<String> paramNames) {
    
    int numberOfParams = paramTypes.size();
    int[] paramsLowerBoundsInts = new int[numberOfParams];
    int[] paramsUpperBoundsInts = new int[numberOfParams];
    double[] paramsLowerBoundsDouble = new double[numberOfParams];
    double[] paramsUpperBoundsDouble = new double[numberOfParams];
    StringCharTypes[] paramsStringCharTypes = new StringCharTypes[numberOfParams];

    int[] constructorLowerBoundsInts = null;
    int[] constructorUpperBoundsInts = null;
    double[] constructorLowerBoundsDouble = null;
    double[] constructorUpperBoundsDouble = null;
    StringCharTypes[] constructorStringCharTypes = null;

    ArrayList<String> constructorParamTypes = null;


    boolean isStatic = Modifier.isStatic(meth.getModifiers());
    if(!isStatic){
      // choose the constructor
      Constructor[] constructors = cls.getConstructors();
      if(constructors.length == 1){
        DebugUtils.dbgLn("Only one constructor found");
        this.chosenConstructor = constructors[0];
      }else{
        DebugUtils.dbgLn("Available constructors: " + constructors.length);
        for (int i = 0; i < constructors.length; i++) {
          Parameter[] params = constructors[i].getParameters();
          DebugUtils.dbg((i + 1) + " : ");
          for (int j = 0; j < params.length; j++) {
            DebugUtils.dbg(params[j]);
            if (j < params.length - 1)
              DebugUtils.dbg(", ");
          }
          DebugUtils.dbgLn("");
          DebugUtils.dbgLn("Which one should be chosen?");
          int choice = keyboard.nextInt();
          if(choice>0 && choice<=constructors.length){
            this.chosenConstructor = constructors[choice-1];
          }else{
            DebugUtils.dbgLn("Error: invalid choice");
            DebugUtils.dbgLn("Exiting...");
            return false;
          }
        }
      }
      constructorLowerBoundsInts = new int[chosenConstructor.getParameterCount()];
      constructorUpperBoundsInts = new int[chosenConstructor.getParameterCount()];
      constructorLowerBoundsDouble = new double[chosenConstructor.getParameterCount()];
      constructorUpperBoundsDouble = new double[chosenConstructor.getParameterCount()];
      constructorStringCharTypes = new StringCharTypes[chosenConstructor.getParameterCount()];
      // check constructor parameters
      for (int i = 0; i < numberOfParams; i++) {
        Class<?>[] argsTypes = meth.getParameterTypes();
        String paramType = argsTypes[i].getName();
        paramTypes.add(paramType);
        if (!(paramType.equals("int") || paramType.equals("double") || paramType.equals("boolean") ||
                paramType.equals("byte") || paramType.equals("short") || paramType.equals("char") ||
                paramType.equals("flaot") || paramType.equals("java.lang.Boolean") || paramType.equals("java.lang.String"))) {
            DebugUtils.dbgLn("Unsupported parameter type '" + paramType + "'");
            DebugUtils.dbgLn("Supported types: int, byte, short, char, double, float, boolean, java.lang.Boolean, java.lang.String");
            if(!argsTypes[i].isPrimitive()){
                DebugUtils.dbgLn("'null' will be used");
            }else{ // long
                DebugUtils.dbgLn("'Exiting");
                return false;
            } 
        } 
      }
    }

    // choose parameters range for the method
    DebugUtils.dbgLn("Please specify parameters for METHOD CALL");
    int numOfIterations = readValues(keyboard, paramTypes, paramNames, numberOfParams, paramsLowerBoundsInts, paramsUpperBoundsInts, paramsLowerBoundsDouble, paramsUpperBoundsDouble, paramsStringCharTypes);
    
    if(numOfIterations==-1){
      return false;
    }

    // choose parameters range for constructor
    if(!isStatic){
      Parameter[] constructorParams = chosenConstructor.getParameters();
      ArrayList<String> constructorParamNames = new ArrayList<String>();
      Class<?>[] argsTypes = chosenConstructor.getParameterTypes();
      constructorParamTypes = new ArrayList<String>();

      for (int i = 0; i < chosenConstructor.getParameterCount(); i++) {
        constructorParamNames.add(constructorParams[i].toString());
        constructorParamTypes.add(argsTypes[i].getName());
      }
      DebugUtils.dbgLn("Please specify parameters for CONSTRUCTOR CALL");
      if(readValues(keyboard, constructorParamTypes, constructorParamNames, chosenConstructor.getParameterCount(), constructorLowerBoundsInts, constructorUpperBoundsInts, constructorLowerBoundsDouble, constructorUpperBoundsDouble, constructorStringCharTypes, true)
        == -1){
        return false;
      }
    }
    
    



    // the actual search
    this.coveredBranches = new TreeSet<Integer>();
    this.coveredConditions = new TreeSet<Integer>();
    int oldCoveredBranchesSize = 0;
    int oldCoveredConditionsSize = 0;
    this.successfulInputs = new ArrayList<Object[]>();
    if(!isStatic){
      this.successfulConstructorInputs = new ArrayList<Object[]>();
    }
    this.successfulOutputs = new ArrayList<Object>();

    DebugUtils.dbgLn("All set, performing search...");

    int actualNumberOfParams = numberOfParams + 2;

    int progressBarSteps = 30;
    boolean showProgressbar = numOfIterations > 10000;
    for (int i = 0; i < numOfIterations; i++) {
      Object invokedObject = null;
      Object[] constructorArguments = new Object[chosenConstructor.getParameterCount()];
      if(!isStatic){
        for (int j = 0; j < chosenConstructor.getParameterCount(); j++) {
          String type = constructorParamTypes.get(j);

          if (type.equals("int")) {
            constructorArguments[j] = (int) getRandomDouble(constructorLowerBoundsInts[j], constructorUpperBoundsInts[j]);
          } else if (type.equals("byte")) {
            constructorArguments[j] = (byte) getRandomDouble(constructorLowerBoundsDouble[j], constructorUpperBoundsDouble[j]);
          } else if (type.equals("short")) {
            constructorArguments[j] = (short) getRandomDouble(constructorLowerBoundsDouble[j], constructorUpperBoundsDouble[j]);
          } else if (type.equals("double")) {
            constructorArguments[j] = getRandomDouble(constructorLowerBoundsDouble[j], constructorUpperBoundsDouble[j]);
          } else if (type.equals("float")) {
            constructorArguments[j] = (float) getRandomDouble(constructorLowerBoundsDouble[j], constructorUpperBoundsDouble[j]);
          } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
            constructorArguments[j] = Math.random() > 0.5;
          } else if (type.equals("java.lang.String")) {
            constructorArguments[j] = genRandomString(constructorStringCharTypes[j], constructorLowerBoundsInts[j], constructorUpperBoundsInts[j]);
          } else if (type.equals("char")) {
            constructorArguments[j] = (char)(int)getRandomDouble(constructorLowerBoundsInts[j], constructorUpperBoundsInts[j]);
          } else {
            constructorArguments[j] = null;
          }
        }
        try {
          invokedObject = chosenConstructor.newInstance(constructorArguments);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
          e.printStackTrace();
          return false;
        }
      }


      Object[] arguments = new Object[actualNumberOfParams];
      for (int j = 0; j < numberOfParams; j++) {
        String type = paramTypes.get(j);

        if (type.equals("int")) {
          arguments[j] = (int) getRandomDouble(paramsLowerBoundsInts[j], paramsUpperBoundsInts[j]);
        } else if (type.equals("double")) {
          arguments[j] = getRandomDouble(paramsLowerBoundsDouble[j], paramsUpperBoundsDouble[j]);
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
          arguments[j] = Math.random() > 0.5;
        } else if (type.equals("java.lang.String")) {
          arguments[j] = genRandomString(paramsStringCharTypes[j], paramsLowerBoundsInts[j], paramsUpperBoundsInts[j]);
        } else if (type.equals("char")) {
          constructorArguments[j] = (char)(int)getRandomDouble(paramsLowerBoundsInts[j], paramsUpperBoundsInts[j]);
        } else{
          arguments[j] = null;
        }
      }
      arguments[actualNumberOfParams - 2] = coveredBranches;
      arguments[actualNumberOfParams - 1] = coveredConditions;
      Object invokeResult;
      try {
        invokeResult = meth.invoke(invokedObject, arguments);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
        return false;
      }
      if (coveredBranches.size() > oldCoveredBranchesSize || coveredConditions.size() > oldCoveredConditionsSize) {
        oldCoveredBranchesSize = coveredBranches.size();
        oldCoveredConditionsSize = coveredConditions.size();
        successfulInputs.add(arguments);
        successfulConstructorInputs.add(constructorArguments);
        successfulOutputs.add(invokeResult);
      }
      if (showProgressbar && i % 1000 == 0) {
        double progress = (i + 1) / (double) numOfIterations;
        int barsCompleted = (int) (progressBarSteps * progress);
        DebugUtils.dbg("[" + "=".repeat(barsCompleted) + " ".repeat(progressBarSteps - barsCompleted) + "] "
            + String.format("%.2f", progress * 100.0, 2) + "%\r");
      }

    }
    DebugUtils.dbgLn("\n");

    DebugUtils.dbgLn("Search complete");
    return true;
  }

}
