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

  /**
   * ask for lower/upper bounds to generate random values method is tested with
   * random values, inputs that add to the coverage are saved and later used by
   * "generateTests" to create file with unit tests
   */
  @Override
  public boolean search(Method meth, Class<?> cls, ArrayList<String> paramTypes, ArrayList<String> paramNames) {
    Scanner keyboard = new Scanner(System.in);
    int numberOfParams = paramTypes.size();
    int[] lowerBoundsInts = new int[numberOfParams];
    int[] upperBoundsInts = new int[numberOfParams];
    double[] lowerBoundsDouble = new double[numberOfParams];
    double[] upperBoundsDouble = new double[numberOfParams];
    StringCharTypes[] stringCharTypes = new StringCharTypes[numberOfParams];

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
      // parameters for constructor
      //TODO
    }
    int numOfIterations = 0;
    try {
      for (int i = 0; i < numberOfParams; i++) {
        String type = paramTypes.get(i);
        DebugUtils.dbgLn(
            "Parameter " + (i + 1) + "/" + (numberOfParams) + " '" + paramNames.get(i) + "' is of type: " + type);

        if (type.equals("int")) {
          DebugUtils.dbgLn("Please provide lower int bound");
          lowerBoundsInts[i] = keyboard.nextInt();
          DebugUtils.dbgLn("Please provide upper int bound");
          upperBoundsInts[i] = keyboard.nextInt();
          if (lowerBoundsInts[i] > upperBoundsInts[i]) {
            DebugUtils.dbgLn("Error: lower bound cannot be greater than upper bound!");
            i--;
          }
        } else if (type.equals("double")) {
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
        }

      }
      DebugUtils.dbgLn("Please specify number of iterations:");
      numOfIterations = keyboard.nextInt();
      if (numOfIterations < 0) {
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

    // the actual search
    this.coveredBranches = new TreeSet<Integer>();
    this.coveredConditions = new TreeSet<Integer>();
    int oldCoveredBranchesSize = 0;
    int oldCoveredConditionsSize = 0;
    this.successfulInputs = new ArrayList<Object[]>();
    this.successfulOutputs = new ArrayList<Object>();

    DebugUtils.dbgLn("All set, performing search...");

    int actualNumberOfParams = numberOfParams + 2;

    int progressBarSteps = 30;
    boolean showProgressbar = numOfIterations > 10000;
    for (int i = 0; i < numOfIterations; i++) {
      
      if(!isStatic){
        //TODO
        DebugUtils.dbgLn("Non static search not implemented yet");
        return false;
      }


      Object[] arguments = new Object[actualNumberOfParams];
      for (int j = 0; j < numberOfParams; j++) {
        String type = paramTypes.get(j);

        if (type.equals("int")) {
          arguments[j] = (int) getRandomDouble(lowerBoundsInts[j], upperBoundsInts[j]);
        } else if (type.equals("double")) {
          arguments[j] = getRandomDouble(lowerBoundsDouble[j], upperBoundsDouble[j]);
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
          arguments[j] = Math.random() > 0.5;
        } else if (type.equals("java.lang.String")) {
          arguments[j] = genRandomString(stringCharTypes[j], lowerBoundsInts[j], upperBoundsInts[j]);
        }
      }
      arguments[actualNumberOfParams - 2] = coveredBranches;
      arguments[actualNumberOfParams - 1] = coveredConditions;
      Object invokeResult;
      try {
        invokeResult = meth.invoke(null, arguments);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
        keyboard.close();
        return false;
      }
      if (coveredBranches.size() > oldCoveredBranchesSize || coveredConditions.size() > oldCoveredConditionsSize) {
        oldCoveredBranchesSize = coveredBranches.size();
        oldCoveredConditionsSize = coveredConditions.size();
        successfulInputs.add(arguments);
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
    keyboard.close();
    return true;
  }

}
