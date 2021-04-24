
# com3529-assignment

## What it is

## What can it do

## How it works

## How to use it

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