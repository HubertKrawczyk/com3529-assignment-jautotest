package jautotest.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import jautotest.app.StringUtils.StringCharTypes;

/** 
 * Random search approach to find test cases
 */
public class RandomSearch extends Search {

  /**
   * Constructor
   * @param testedMethod tested method
   * @param testedClass tested class
   */
  public RandomSearch(Method testedMethod, Class<?> testedClass) {
    super(testedMethod, testedClass);
  }

  /**
   * Fill the arrays for parameter types, names, lower/upper bounds with inputs from given Scanner keyboard object
   * @param keyboard Scanner object used for input
   * @return true if no errors occured
   */
  boolean readValues(Scanner keyboard, ArrayList<String> paramTypes, ArrayList<String> paramNames, int numberOfParams,
      int[] lowerBoundsInts, int[] upperBoundsInts, double[] lowerBoundsDouble, double[] upperBoundsDouble,
      StringCharTypes[] stringCharTypes) {
    try {
      for (int i = 0; i < numberOfParams; i++) {
        String type = paramTypes.get(i);
        DebugUtils.printLn(
            "Parameter " + (i + 1) + "/" + (numberOfParams) + " '" + paramNames.get(i) + "' is of type: " + type);

        if (type.equals("int") || type.equals("byte") || type.equals("short") || type.equals("Integer")) {
          if (type.equals("byte") || type.equals("short")) {
            DebugUtils.printLn("Int will be used to store byte/short/Integer value please use correct min/max values");
          }
          DebugUtils.printLn("Please provide lower int bound");
          lowerBoundsInts[i] = keyboard.nextInt();
          DebugUtils.printLn("Please provide upper int bound");
          upperBoundsInts[i] = keyboard.nextInt();
          if (lowerBoundsInts[i] > upperBoundsInts[i]) {
            DebugUtils.printLn("ERROR: lower bound cannot be greater than upper bound!");
            i--;
          }
        } else if (type.equals("double") || type.equals("float")) {
          if (type.equals("float")) {
            DebugUtils.printLn("Double will be used to store float value please use correct min/max values");
          }
          DebugUtils.printLn("Please provide lower double bound");
          lowerBoundsDouble[i] = keyboard.nextDouble();
          DebugUtils.printLn("Please provide upper double bound");
          upperBoundsDouble[i] = keyboard.nextDouble();
          if (lowerBoundsDouble[i] > upperBoundsDouble[i]) {
            DebugUtils.printLn("ERROR: lower bound cannot be greater than upper bound!");
            i--;
          }
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
          DebugUtils.printLn("Next type is boolean, no need for lower/upper bounds");
        } else if (type.equals("java.lang.String")) {
          DebugUtils.printLn(
              "Next type is java.lang.String, Strings will be generated randomly, character range as well as min/max string lengths need to be specified.");
          DebugUtils.printLn(
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
            DebugUtils.printLn("ERROR: invalid choice");
            i--;
          }
          if (stringCharTypes[i] != null) {
            DebugUtils.printLn("Please provide min string lenght value");
            lowerBoundsInts[i] = keyboard.nextInt();
            DebugUtils.printLn("Please provide max string lenght value");
            upperBoundsInts[i] = keyboard.nextInt();
            if (lowerBoundsInts[i] > upperBoundsInts[i]) {
              DebugUtils.printLn("ERROR: lower bound cannot be greater than upper bound!");
              i--;
            } else if (lowerBoundsInts[i] < 0) {
              DebugUtils.printLn("ERROR: lower bound cannot be less than 0!");
              i--;
            }
          }
        } else if (type.equals("char")) {
          DebugUtils.printLn("Please provide lower int bound >= 32 (int will be casted to char)");
          lowerBoundsInts[i] = keyboard.nextInt();
          DebugUtils.printLn("Please provide upper int bound <= 1023(int will be casted to char)");
          upperBoundsInts[i] = keyboard.nextInt();
          if (lowerBoundsInts[i] > upperBoundsInts[i]) {
            DebugUtils.printLn("ERROR: lower bound cannot be greater than upper bound!");
            i--;
          } else if (lowerBoundsInts[i] < 32 || upperBoundsInts[i] > 1023) {
            DebugUtils.printLn("ERROR: lower bound needs to be >= 32 and upper bound <= 1023");
            i--;
          }
        } else {
          DebugUtils.printLn("Unsupported type: '" + type + "', 'null' will be used");
        }

      }

    } catch (InputMismatchException e) {
      DebugUtils.printLn("ERROR: Typed wrong value type");
      DebugUtils.printLn("Exiting...");
      return false;
    }
    return true;
  }


   
  @Override
  /**
   * Ask for lower/upper bounds for method and constructor parameters.
   * Generate random values for the parameters and invoke tested method.
   * Fills object instance floowing arrays: successfulInputs, successfulConstructorInputs, successfulOutputs,
   *  coveredBranches, coveredConditions, coveredMasterConditions;
   * @param keyboard Scanner object used for input
   */
  public boolean search(Scanner keyboard) {

    Method meth = this.testedMethod;
    Class<?> cls = this.testedClass;

    ArrayList<String> paramNames = new ArrayList<String>();
    ArrayList<String> paramTypes = new ArrayList<String>();
    Class<?>[] methArgsTypes = meth.getParameterTypes();
    Parameter[] methParams = meth.getParameters();

    int numberOfParams = methArgsTypes.length - 2; // ignore the last 2 added during parsing

    for (int i = 0; i < numberOfParams; i++) {
        paramNames.add(methParams[i].toString());
        String paramType = methArgsTypes[i].getName();
        paramTypes.add(paramType);
    }

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
    ArrayList<String> constructorParamNames = null;

    boolean isStatic = Modifier.isStatic(meth.getModifiers());

    if (!isStatic) {
      // choose the constructor
      Constructor[] constructors = cls.getConstructors();
      if (constructors.length == 1) {
        DebugUtils.printLn("Only one constructor found");
        this.chosenConstructor = constructors[0];
      } else {
        DebugUtils.printLn("Available constructors: " + constructors.length);
        for (int i = 0; i < constructors.length; i++) {
          Parameter[] constrParams = constructors[i].getParameters();
          DebugUtils.print((i + 1) + " : ");
          for (int j = 0; j < constrParams.length; j++) {
            DebugUtils.print(constrParams[j]);
            if (j < constrParams.length - 1)
              DebugUtils.print(", ");
          }
          DebugUtils.printLn("");
          
        }

        DebugUtils.printLn("Which one should be chosen?");
        int choice = keyboard.nextInt();
        if (choice > 0 && choice <= constructors.length) {
          this.chosenConstructor = constructors[choice - 1];
        } else {
          DebugUtils.printLn("ERROR: invalid choice");
          DebugUtils.printLn("Exiting...");
          return false;
        }

      }

      constructorLowerBoundsInts = new int[chosenConstructor.getParameterCount()];
      constructorUpperBoundsInts = new int[chosenConstructor.getParameterCount()];
      constructorLowerBoundsDouble = new double[chosenConstructor.getParameterCount()];
      constructorUpperBoundsDouble = new double[chosenConstructor.getParameterCount()];
      constructorStringCharTypes = new StringCharTypes[chosenConstructor.getParameterCount()];

      // check constructor parameters
      for (int i = 0; i < chosenConstructor.getParameterCount(); i++) {
        Class<?>[] constrArgsTypes = chosenConstructor.getParameterTypes();
        String paramType = constrArgsTypes[i].getName();
        paramTypes.add(paramType);
        if (!(paramType.equals("int") || paramType.equals("double") || paramType.equals("boolean")
            || paramType.equals("byte") || paramType.equals("short") || paramType.equals("char")
            || paramType.equals("flaot") || paramType.equals("java.lang.Boolean")
            || paramType.equals("java.lang.String"))) {
          DebugUtils.printLn("Unsupported parameter type '" + paramType + "'");
          DebugUtils.printLn(
              "Supported types: int, byte, short, char, double, float, boolean, java.lang.Boolean, java.lang.String");
          if (!constrArgsTypes[i].isPrimitive()) {
            DebugUtils.printLn("'null' will be used");
          } else { // long
            DebugUtils.printLn("'Exiting");
            return false;
          }
        }
      }
    }

    // choose parameters range for the method call
    DebugUtils.printLn("Please specify parameters for METHOD CALL");
    if(!readValues(keyboard, paramTypes, paramNames, numberOfParams, paramsLowerBoundsInts,
        paramsUpperBoundsInts, paramsLowerBoundsDouble, paramsUpperBoundsDouble, paramsStringCharTypes)){
          return false;
    }

    DebugUtils.printLn("Please specify number of iterations:");
    int numOfIterations = keyboard.nextInt();
    if (numOfIterations < 0) {
      DebugUtils.printLn("ERROR: number of iterations must be positive!");
      DebugUtils.printLn("Exiting...");
      return false;
    }

    
    if (!isStatic) {
      // choose parameters range for the constructor call
      Parameter[] constructorParams = chosenConstructor.getParameters();
      constructorParamNames = new ArrayList<String>();
      Class<?>[] argsTypes = chosenConstructor.getParameterTypes();
      constructorParamTypes = new ArrayList<String>();

      for (int i = 0; i < chosenConstructor.getParameterCount(); i++) {
        constructorParamNames.add(constructorParams[i].toString());
        constructorParamTypes.add(argsTypes[i].getName());
      }
      DebugUtils.printLn("Please specify parameters for CONSTRUCTOR CALL");
      if (!readValues(keyboard, constructorParamTypes, constructorParamNames, chosenConstructor.getParameterCount(),
          constructorLowerBoundsInts, constructorUpperBoundsInts, constructorLowerBoundsDouble,
          constructorUpperBoundsDouble, constructorStringCharTypes)) {
        return false;
      }
    }

    // read the data about number of branches and conditions from the parsed file
    int numOfBranches;
    int numOfConditions;
    Integer[][] branchesPredicatesConditions; // e.g. {{1}, {2, 3}, {4, 5}, {}}

    try {
      numOfBranches = (int) cls.getDeclaredField("numberOfBranches").get(null);
      numOfConditions = (int) cls.getDeclaredField("numberOfConditions").get(null);
      branchesPredicatesConditions = (Integer[][]) cls.getDeclaredField("branchesPredicatesConditions").get(null);
    } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e2) {
      e2.printStackTrace();
      return false;
    }

    // determine to which branch/predicate each condition belogns to
    // if: branchesPredicatesConditions = {{1}, {2, 3}, {4, 5}, {}}
    // then: whichPredicate = {undefined, {1}, {2}, {2}, {3}, {3}}
    int[] whichPredicate = new int[numOfConditions + 1]; // ignore first value, conditions start at 1 not 0

    for (int branchNo = 0; branchNo < numOfBranches; branchNo++) {
      Integer[] branchConditions = branchesPredicatesConditions[branchNo];
      for (int conditionNo = 0; conditionNo < branchConditions.length; conditionNo++) {
        whichPredicate[branchConditions[conditionNo]] = branchNo + 1;
      }
    }

    // the actual search
    this.coveredBranches = new TreeSet<Integer>(); // also tells which branch predicates were derived true
    this.coveredConditions = new TreeSet<Integer>();
    this.coveredMasterConditions = new TreeSet<Integer>(); // cMCDC

    // keep track of predicates and conditions results for each saved run (cMCDC)
    ArrayList<TreeSet<Integer>> successfulCoveredBranchesArchive = new ArrayList<TreeSet<Integer>>();
    ArrayList<TreeSet<Integer>> successfulCoveredConditionsArchive = new ArrayList<TreeSet<Integer>>();

    this.successfulInputs = new ArrayList<Object[]>();
    if (!isStatic) {
      this.successfulConstructorInputs = new ArrayList<Object[]>();
    }
    this.successfulOutputs = new ArrayList<Object>();

    int actualNumberOfParams = numberOfParams + 2; // include Set<Integer> coveredBranches and Set<Integer> coveredConditions

    int progressBarSteps = 30;
    boolean showProgressbar = numOfIterations > 10000;

    DebugUtils.printLn("All set, performing search...");
    for (int i = 0; i < numOfIterations; i++) {
      Object invokedObject = null;
      Object[] constructorArguments = null;
      Object[] arguments = new Object[actualNumberOfParams];

      // get random constructor arguments
      if (!isStatic) {
        constructorArguments = new Object[chosenConstructor.getParameterCount()];
        for (int j = 0; j < chosenConstructor.getParameterCount(); j++) {
          String type = constructorParamTypes.get(j);

          constructorArguments[j] = NumberUtils.getRandomValue(type, constructorLowerBoundsInts[j],
              constructorUpperBoundsInts[j], constructorLowerBoundsDouble[j], constructorUpperBoundsDouble[j],
              constructorStringCharTypes[j]);

        }
        try {
          invokedObject = chosenConstructor.newInstance(constructorArguments);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
          e.printStackTrace();
          return false;
        }
      }

      // get random method arguments
      for (int j = 0; j < numberOfParams; j++) {
        String type = paramTypes.get(j);

        arguments[j] = NumberUtils.getRandomValue(type, paramsLowerBoundsInts[j], paramsUpperBoundsInts[j],
            paramsLowerBoundsDouble[j], paramsUpperBoundsDouble[j], paramsStringCharTypes[j]);
      }

      // invoke the method
      TreeSet<Integer> newCoveredBranches = new TreeSet<Integer>();
      TreeSet<Integer> newCoveredConditions = new TreeSet<Integer>();
      arguments[actualNumberOfParams - 2] = newCoveredBranches;
      arguments[actualNumberOfParams - 1] = newCoveredConditions;
      Object invokeResult;
      try {
        invokeResult = meth.invoke(invokedObject, arguments);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
        return false;
      }
      

      // check for new cMCDC coverage
      boolean toBeAdded = false;

      // loop over the all possible conditions
      for (int condition = 1; condition < numOfConditions + 1; condition++) {
        // check if the condition was ever reached in this run (and thus evaluated true or false)
        if (coveredConditions.contains(condition) || coveredConditions.contains(-condition)) {

          // check if this master condition is not already covered
          if (!coveredMasterConditions.contains(Math.abs(condition))) {

            // check against all saved runs
            for (int j = 0; j < successfulInputs.size(); j++) {

              Integer negCondition = -condition;
              // check if the same condition was evaluated differently
              if (successfulCoveredConditionsArchive.get(j).contains(negCondition)) {
                // check what is the result of the predicate (in current run)
                if (coveredBranches.contains(whichPredicate[Math.abs(condition)])) { 
                  // predicate was evaluated true

                  // check if the predicate for the archived run was evaluated false
                  if (!successfulCoveredBranchesArchive.get(j).contains(whichPredicate[Math.abs(condition)])) {
                    // success
                    toBeAdded = true;
                    coveredMasterConditions.add(condition);
                    //break;
                  }
                } else { 
                  // predicate was evaluated false (did not reach this branch-block)

                  // check if the predicate for the archived run was evaluated true
                  if (successfulCoveredBranchesArchive.get(j).contains(whichPredicate[Math.abs(condition)])) {
                    // success
                    toBeAdded = true;
                    coveredMasterConditions.add(condition);
                    //break;
                  }
                }
              }
            }

          }
        }

      }

      if (toBeAdded || addsNewElements(coveredBranches, newCoveredBranches)
          || addsNewElements(coveredConditions, newCoveredConditions)) {
        coveredBranches.addAll(newCoveredBranches);
        coveredConditions.addAll(newCoveredConditions);

        successfulInputs.add(arguments);
        if (!isStatic) {
          successfulConstructorInputs.add(constructorArguments);
        }

        successfulOutputs.add(invokeResult);

        successfulCoveredBranchesArchive.add(newCoveredBranches);
        successfulCoveredConditionsArchive.add(newCoveredConditions);

        DebugUtils.print("Added input:");
        for (int argNo = 0; argNo < arguments.length - 2; argNo++) {
          DebugUtils.print(arguments[argNo]);
          if (argNo < arguments.length - 3) {
            DebugUtils.print(", ");
          }
        }
        DebugUtils.printLn(".");

      }

      if (showProgressbar && i % 1000 == 0) {
        double progress = (i + 1) / (double) numOfIterations;
        int barsCompleted = (int) (progressBarSteps * progress);
        DebugUtils.printProgressBar(progressBarSteps, barsCompleted, progress);
      }

    }
    DebugUtils.printLn("\n");

    DebugUtils.printLn("Search complete");
    return true;
  }

  /**
   * Checks if Collection 'b' contains any elements not present in Collection 'a'
   * @param a Collection a
   * @param b Collection b
   * @return true if 'b' contains any elements not present in 'a', false otherwise
   */
  static boolean addsNewElements(Collection<?> a, Collection<?> b) {
    for (Object element : b) {
      if (!a.contains(element)) {
        return true;
      }
    }

    return false;
  }



}
