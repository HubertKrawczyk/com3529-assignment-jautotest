
# com3529-assignment, jautotest

## What it is
(University of Sheffield COM3529 — SOFTWARE TESTING & ANALYSIS Assignment submission)

A Java tool for automatic test cases generating for a given class method. Tests are generated to maximize the code coverage. The final coverage and the generated test cases are saved in a JUnit tests file. 

## How it works
The tests are generated using a random search. However to achieve good results the program asks for information such as lower and upper bounds for int parameter to increase chances of produce high coverage tests. 

The process consists of two parts: 1: automatic parsing the class and method given by user, 2: using the parsed and instrumented code to generate tests.

The program makes use of: javaparser https://javaparser.org/ and Java's Reflection https://www.oracle.com/technical-resources/articles/java/javareflection.html .

The following coverage criterias are calculated: Branch coverage, Condition coverage and correlated MCDC (Modified Condition Decision Coverage) coverage.

If the returned value is any of primitive types, String, or enum, an assertation is included to the test case. Note: It does not solve the Oracle problem - user still needs to decide if the returned value is correct.

## How to use it
The program takes as input a Java class file with a method to be tested. The method needs to be public. Supported types for parameters: java.lang.Integer, java.lang.String, java.lang.Boolean and all primiteves excluding long. For other types null will be used.

### 0 Prerequisites
Java 11 or newer (was tested with versions 11.0.10 and 13.0.1)

Maven (was tested with Apache Maven 3.6.3)

1. Navigate to "/jautotest" and use `mvn install`


### 1 Prepare (parse) the file
1. Navigate to "/jautotest"
2. Given the class to be tested is *CLASS_NAME*, the method to be tested is *METHOD_NAME*, and the path to the file containing the class is *PATH_TO_CLASS* the command is:

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='PATH_TO_CLASS/CLASS_NAME.java' 'CLASS_NAME' 'METHOD_NAME'" -e
```
(replace '/' delimiter to the correct one for your platform)

for example:

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/BMICalculator.java' 'BMICalculator' 'calculate'" -e
```

### 2 Generate tests
1. The previous step needs to be fulfilled before generating tests.
2. Navigate to "/jautotest"
3. Given the class to be tested is *CLASS_NAME*, the method to be tested is *METHOD_NAME*:

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='CLASS_NAME' 'METHOD_NAME:'" -e
```

for example:

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='BMICalculator' 'calculate'" -e
```

4. Follow the instructions on the terminal

### 3 Run the generated tests

The test files are saved in /jautotest/src/test/java/jautotest/app/CLASS_NAMEMETHOD_NAMETest.java and can be run using `mvn test`.


Individual test files can be run using additional argument specifying its name (method name is capitalized) e.g.:

``` bash
mvn test -Dtest=BMICalculatorCalculateTest
``` 

### Problems, limitations, notes
#### Forbidden keywords
The tested method cannot have parameters with names: <span style="color:red">coveredBranches</span> and <span style="color:red">coveredConditions</span> .

The tested class cannot have variables with names: <span style="color:red">numberOfBranches</span>, <span style="color:red">numberOfConditions</span>  and <span style="color:red">branchesPredicatesConditions</span>.

#### Multiple methods with the same name
The program takes the first public method with matching name. The problem arises if there are many methods with the same name. To specify which method should be chosen, its parameters need to be specified. In place of *METHOD_NAME* please put method declaration structure like: `calculate(double weightInPounds, int heightFeet, int heightInches)`

The method for parsing 'BMICalculator' 'calculate' method becomes:

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/BMICalculator.java' 'BMICalculator' 'calculate(double weightInPounds, int heightFeet, int heightInches)'" -e
```

and generating tests:

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='BMICalculator' 'calculate(double weightInPounds, int heightFeet, int heightInches)'" -e
```

Note that the parameters are delimitered by commas and a maximum of 1 space!

## Examples

Folder 'testfiles/' contains some simple programs that can be used as examples.
(Some examples come from Phil McMinn https://mcminn.io/ as part of the course https://github.com/philmcminn/com3529-code)

#### BMICalculator

Parse: 
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/BMICalculator.java' 'BMICalculator' 'calculate'" -e
```
GenerateTests:
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='BMICalculator' 'calculate'" -e
```

A simple code which given 3 numbers calculates the BMI Type enum (UNDERWEIGHT,  NORMAL, OVERWEIGHT,  OBESE). This program supports enums in assertations so they are automatically added to the generated test case:

``` Java
  public void test1() {
      // Object result = BMICalculator.calculate(29.1568199726307, 38, 1);
      Object result = input.BMICalculator.calculate(29.1568199726307, 38, 1, null, null, null);
      // remove the line above and uncomment upper one to test original code
      // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
      assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
      // remove the line above and uncomment upper one to test original code
  }
```

No matter how many iterations the program will perform, the condition and thus cMCDC coverages will never reach 100%:

``` Java
/*
Branch coverage: 100,00% (4/4)
Branch coverage - covered branches: [1, 2, 3, 4]

Condition coverage: 80,00% (8/10)
Condition coverage - covered conditions: [-5, -3, -1, 1, 2, 3, 4, 5]

Correlated MCDC coverage: 60,00% (3/5)
Correlated MCDC - covered conditions: [1, 3, 5]

Number of tests generated: 4
Test file generated automatically at 24-04-2021 18:47:14, please check the assertations.
*/
```

The obious reason is that conditions 2 (bmi >= 17.5) and 4 (bmi >= 25) can never be evaluated false.




#### SimpleBankAccount

Parse:
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/SimpleBankAccount.java' 'SimpleBankAccount' 'withdrawCoins'" -e
``` 
GenerateTests:
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='SimpleBankAccount' 'withdrawCoins'" -e
``` 
This example demonstrates that the program supports constructors and can test non static methods.
``` Java
  @Test()
  public void test2() {
      // SimpleBankAccount testedObject = new SimpleBankAccount("nXRO", 79, true);
      input.SimpleBankAccount testedObject = new input.SimpleBankAccount("nXRO", 79, true);
      // Object result = testedObject.withdrawCoins(-44);
      Object result = testedObject.withdrawCoins(-44, null, null, null);
      // remove the line above and uncomment upper one to test original code
      assertTrue(result.equals(false));
  }
``` 

#### RemoveNonAlphanumericChars


``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/TestFunctions.java' 'TestFunctions' 'removeNonAlphanumericChars'" -e
``` 
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='TestFunctions' 'removeNonAlphanumericChars'" -e
``` 

#### CalendarDaysBetweenTwoDates

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/Calendar.java' 'Calendar' 'daysBetweenTwoDates'" -e
``` 
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='Calendar' 'daysBetweenTwoDates'" -e
``` 

#### Triangle

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/Triangle.java' 'Triangle' 'classify'" -e
``` 
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='Triangle' 'classify'" -e
``` 