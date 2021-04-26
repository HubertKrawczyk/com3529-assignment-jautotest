
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

1. Navigate to "/jautotest" and use `mvn package`


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

3. Use

``` bash
mvn compile
```

to make sure the parsed class is readable by the program.
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
The tested method cannot have parameters with names: *<span style="color:red">coveredBranches</span>* and *<span style="color:red">coveredConditions</span>* .

The tested class cannot have variables with names: *<span style="color:red">numberOfBranches</span>*, *<span style="color:red">numberOfConditions</span>* and *<span style="color:red">branchesPredicatesConditions</span>*.

In parsing process the tested class package is changed to "input", make sure it does not affect your code.

#### Multiple methods with the same name
The program takes the first public method with matching name. The problem arises if there are many methods with the same name. To specify which method should be chosen, its parameters need to be specified. In place of *METHOD_NAME* please put method declaration like: `calculate(double weightInPounds, int heightFeet, int heightInches)`

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

<details>
<summary>See full example generated test file</summary>
<p>

```java
// please remove/change this line if used outside of this application
package jautotest.app.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (4/4)
Branch coverage - covered branches: [1, 2, 3, 4]

Condition coverage: 80,00% (8/10)
Condition coverage - covered conditions: [-5, -3, -1, 1, 2, 3, 4, 5]

Correlated MCDC coverage: 60,00% (3/5)
Correlated MCDC - covered conditions: [1, 3, 5]

Number of tests generated: 4
Test file generated automatically at 26-04-2021 12:47:09, please check the assertations.
*/
public class BMICalculatorCalculateTest {

    @Test()
    public void test1() {
        // Object result = BMICalculator.calculate(87.32045359488193, 72, 53);
        Object result = input.BMICalculator.calculate(87.32045359488193, 72, 53, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(98.59786459991898, 0, 7);
        Object result = input.BMICalculator.calculate(98.59786459991898, 0, 7, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OBESE))
        assertTrue(result.equals(input.BMICalculator.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(81.21167804429088, 2, 21);
        Object result = input.BMICalculator.calculate(81.21167804429088, 2, 21, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(63.05060856231389, 3, 7);
        Object result = input.BMICalculator.calculate(63.05060856231389, 3, 7, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculator.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }
}

```

</p>
</details>  


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

<details>
<summary>See full example generated test file</summary>
<p>

```java
// please remove/change this line if used outside of this application
package jautotest.app.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (7/7)
Branch coverage - covered branches: [1, 2, 3, 4, 5, 6, 7]

Condition coverage: 100,00% (8/8)
Condition coverage - covered conditions: [-4, -3, -2, -1, 1, 2, 3, 4]

Correlated MCDC coverage: 100,00% (4/4)
Correlated MCDC - covered conditions: [1, 2, 3, 4]

Number of tests generated: 5
Test file generated automatically at 26-04-2021 13:01:36, please check the assertations.
*/
public class SimpleBankAccountWithdrawCoinsTest {

    @Test()
    public void test1() {
        // SimpleBankAccount testedObject = new SimpleBankAccount("Alog", 5, false);
        input.SimpleBankAccount testedObject = new input.SimpleBankAccount("Alog", 5, false);
        // Object result = testedObject.withdrawCoins(43);
        Object result = testedObject.withdrawCoins(43, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(false));
    }

    @Test()
    public void test2() {
        // SimpleBankAccount testedObject = new SimpleBankAccount("KPhY", 8, true);
        input.SimpleBankAccount testedObject = new input.SimpleBankAccount("KPhY", 8, true);
        // Object result = testedObject.withdrawCoins(87);
        Object result = testedObject.withdrawCoins(87, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(false));
    }

    @Test()
    public void test3() {
        // SimpleBankAccount testedObject = new SimpleBankAccount("mAku", 74, false);
        input.SimpleBankAccount testedObject = new input.SimpleBankAccount("mAku", 74, false);
        // Object result = testedObject.withdrawCoins(-88);
        Object result = testedObject.withdrawCoins(-88, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(false));
    }

    @Test()
    public void test4() {
        // SimpleBankAccount testedObject = new SimpleBankAccount("saWm", 59, true);
        input.SimpleBankAccount testedObject = new input.SimpleBankAccount("saWm", 59, true);
        // Object result = testedObject.withdrawCoins(6);
        Object result = testedObject.withdrawCoins(6, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(true));
    }

    @Test()
    public void test5() {
        // SimpleBankAccount testedObject = new SimpleBankAccount("qkVH", 91, false);
        input.SimpleBankAccount testedObject = new input.SimpleBankAccount("qkVH", 91, false);
        // Object result = testedObject.withdrawCoins(7);
        Object result = testedObject.withdrawCoins(7, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(true));
    }
}

```

</p>
</details>  

#### RemoveNonAlphanumericChars


``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/TestFunctions.java' 'TestFunctions' 'removeNonAlphanumericChars'" -e
``` 

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='TestFunctions' 'removeNonAlphanumericChars'" -e
``` 

<details>
<summary>See full example generated test file</summary>
<p>

```java
// please remove/change this line if used outside of this application
package jautotest.app.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (1/1)
Branch coverage - covered branches: [1]

Condition coverage: 100,00% (12/12)
Condition coverage - covered conditions: [-6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6]

Correlated MCDC coverage: 100,00% (6/6)
Correlated MCDC - covered conditions: [1, 2, 3, 4, 5, 6]

Number of tests generated: 2
Test file generated automatically at 26-04-2021 13:02:36, please check the assertations.
*/
public class TestFunctionsRemoveNonAlphanumericCharsTest {

    @Test()
    public void test1() {
        // TestFunctions testedObject = new TestFunctions();
        input.TestFunctions testedObject = new input.TestFunctions();
        // Object result = testedObject.removeNonAlphanumericChars("?<*n9)f");
        Object result = testedObject.removeNonAlphanumericChars("?<*n9)f", null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals("n9f"));
    }

    @Test()
    public void test2() {
        // TestFunctions testedObject = new TestFunctions();
        input.TestFunctions testedObject = new input.TestFunctions();
        // Object result = testedObject.removeNonAlphanumericChars("*2,?GvJS}R|\\\\q");
        Object result = testedObject.removeNonAlphanumericChars("*2,?GvJS}R|\\\\q", null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals("2GvJSRq"));
    }
}

```

</p>
</details>

#### LongestRepetition


``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/TestFunctions.java' 'TestFunctions' 'longestRepetition'" -e
``` 

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='TestFunctions' 'longestRepetition'" -e
``` 

<details>
<summary>See full example generated test file</summary>
<p>

```java
// please remove/change this line if used outside of this application
package jautotest.app.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (4/4)
Branch coverage - covered branches: [1, 2, 3, 4]

Condition coverage: 100,00% (6/6)
Condition coverage - covered conditions: [-3, -2, -1, 1, 2, 3]

Correlated MCDC coverage: 100,00% (3/3)
Correlated MCDC - covered conditions: [1, 2, 3]

Number of tests generated: 3
Test file generated automatically at 26-04-2021 13:22:19, please check the assertations.
*/
public class TestFunctionsLongestRepetitionTest {

    @Test()
    public void test1() {
        // Object result = TestFunctions.longestRepetition("mZtZdiNZJLXQhaMRdHAYOWZqNMyEiLSdTxuRaWibLGEHIejHaUibK", (char)61);
        Object result = input.TestFunctions.longestRepetition("mZtZdiNZJLXQhaMRdHAYOWZqNMyEiLSdTxuRaWibLGEHIejHaUibK", (char) 61, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(0));
    }

    @Test()
    public void test2() {
        // Object result = TestFunctions.longestRepetition("KInkWnmOJcSbdRWADVHoRfVHxqHIYGYZnYLlRCCZiURNgmjHiZYAZcOLjOkvwLnnogYsEvecVMXscZCZzJwjrwQyty", (char)72);
        Object result = input.TestFunctions.longestRepetition("KInkWnmOJcSbdRWADVHoRfVHxqHIYGYZnYLlRCCZiURNgmjHiZYAZcOLjOkvwLnnogYsEvecVMXscZCZzJwjrwQyty", (char) 72, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(1));
    }

    @Test()
    public void test3() {
        // Object result = TestFunctions.longestRepetition("", (char)87);
        Object result = input.TestFunctions.longestRepetition("", (char) 87, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(0));
    }
}

```

</p>
</details>

#### CalendarDaysBetweenTwoDates

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/Calendar.java' 'Calendar' 'daysBetweenTwoDates'" -e
``` 
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='Calendar' 'daysBetweenTwoDates'" -e
``` 

<details>
<summary>See full example generated test file</summary>
<p>

```java
// please remove/change this line if used outside of this application
package jautotest.app.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (14/14)
Branch coverage - covered branches: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]

Condition coverage: 100,00% (36/36)
Condition coverage - covered conditions: [-18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]

Correlated MCDC coverage: 100,00% (18/18)
Correlated MCDC - covered conditions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]

Number of tests generated: 12
Test file generated automatically at 26-04-2021 13:18:03, please check the assertations.
*/
public class CalendarDaysBetweenTwoDatesTest {

    @Test()
    public void test1() {
        // Object result = Calendar.daysBetweenTwoDates(65, 12, 58, 91, 70, 97);
        Object result = input.Calendar.daysBetweenTwoDates(65, 12, 58, 91, 70, 97, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(9496));
    }

    @Test()
    public void test2() {
        // Object result = Calendar.daysBetweenTwoDates(94, 95, 69, 85, 77, 18);
        Object result = input.Calendar.daysBetweenTwoDates(94, 95, 69, 85, 77, 18, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(3300));
    }

    @Test()
    public void test3() {
        // Object result = Calendar.daysBetweenTwoDates(44, 74, 45, 91, 3, 75);
        Object result = input.Calendar.daysBetweenTwoDates(44, 74, 45, 91, 3, 75, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(16891));
    }

    @Test()
    public void test4() {
        // Object result = Calendar.daysBetweenTwoDates(26, 1, 8, 17, 85, 14);
        Object result = input.Calendar.daysBetweenTwoDates(26, 1, 8, 17, 85, 14, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(2947));
    }

    @Test()
    public void test5() {
        // Object result = Calendar.daysBetweenTwoDates(69, 64, 0, 83, 53, 55);
        Object result = input.Calendar.daysBetweenTwoDates(69, 64, 0, 83, 53, 55, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(5143));
    }

    @Test()
    public void test6() {
        // Object result = Calendar.daysBetweenTwoDates(55, 48, 21, 55, 22, 45);
        Object result = input.Calendar.daysBetweenTwoDates(55, 48, 21, 55, 22, 45, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(10));
    }

    @Test()
    public void test7() {
        // Object result = Calendar.daysBetweenTwoDates(75, 0, 68, 53, 57, 85);
        Object result = input.Calendar.daysBetweenTwoDates(75, 0, 68, 53, 57, 85, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(7701));
    }

    @Test()
    public void test8() {
        // Object result = Calendar.daysBetweenTwoDates(20, 31, 38, 5, 73, 0);
        Object result = input.Calendar.daysBetweenTwoDates(20, 31, 38, 5, 73, 0, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(5509));
    }

    @Test()
    public void test9() {
        // Object result = Calendar.daysBetweenTwoDates(57, 45, 84, 57, 27, 5);
        Object result = input.Calendar.daysBetweenTwoDates(57, 45, 84, 57, 27, 5, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(26));
    }

    @Test()
    public void test10() {
        // Object result = Calendar.daysBetweenTwoDates(59, 86, 47, 37, 0, 26);
        Object result = input.Calendar.daysBetweenTwoDates(59, 86, 47, 37, 0, 26, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(8374));
    }

    @Test()
    public void test11() {
        // Object result = Calendar.daysBetweenTwoDates(52, 3, 72, 52, 45, 32);
        Object result = input.Calendar.daysBetweenTwoDates(52, 3, 72, 52, 45, 32, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(275));
    }

    @Test()
    public void test12() {
        // Object result = Calendar.daysBetweenTwoDates(2, 46, 36, 2, 3, 96);
        Object result = input.Calendar.daysBetweenTwoDates(2, 46, 36, 2, 3, 96, null, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(275));
    }
}

```

</p>
</details>  

#### Triangle

``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.Parse" "-Dexec.args='../testfiles/Triangle.java' 'Triangle' 'classify'" -e
``` 
``` bash
mvn exec:java "-Dexec.mainClass=jautotest.app.GenerateTests" "-Dexec.args='Triangle' 'classify'" -e
``` 

<details>
<summary>See full example generated test file</summary>
<p>

```java
// please remove/change this line if used outside of this application
package jautotest.app.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (9/9)
Branch coverage - covered branches: [1, 2, 3, 4, 5, 6, 7, 8, 9]

Condition coverage: 100,00% (14/14)
Condition coverage - covered conditions: [-7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7]

Correlated MCDC coverage: 100,00% (7/7)
Correlated MCDC - covered conditions: [1, 2, 3, 4, 5, 6, 7]

Number of tests generated: 7
Test file generated automatically at 26-04-2021 13:11:45, please check the assertations.
*/
public class TriangleClassifyTest {

    @Test()
    public void test1() {
        // Object result = Triangle.classify(17, 99, 74);
        Object result = input.Triangle.classify(17, 99, 74, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = Triangle.classify(49, 15, 9);
        Object result = input.Triangle.classify(49, 15, 9, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = Triangle.classify(73, 33, 42);
        Object result = input.Triangle.classify(73, 33, 42, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = Triangle.classify(38, 57, 72);
        Object result = input.Triangle.classify(38, 57, 72, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = Triangle.classify(70, 70, 29);
        Object result = input.Triangle.classify(70, 70, 29, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.ISOSCELES))
        assertTrue(result.equals(input.Triangle.Type.ISOSCELES));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = Triangle.classify(88, 70, 70);
        Object result = input.Triangle.classify(88, 70, 70, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test7() {
        // Object result = Triangle.classify(75, 75, 75);
        Object result = input.Triangle.classify(75, 75, 75, null, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.EQUILATERAL))
        assertTrue(result.equals(input.Triangle.Type.EQUILATERAL));
        // remove the line above and uncomment upper one to test original code
    }
}

```

</p>
</details>  
