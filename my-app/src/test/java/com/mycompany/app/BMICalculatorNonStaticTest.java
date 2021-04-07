// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (4/4)
Condition coverage: 80.0% (8/10)
Number of tests generated: 4
Test file generated automatically, please check the assertations.
*/
public class BMICalculatorNonStaticTest {

    @Test()
    public void test1() {
        input.BMICalculatorNonStatic testedObject = new input.BMICalculatorNonStatic((char) 542);
        // Object result = testedObject.calculate(87.96005411980472, 27, 98);
        Object result = testedObject.calculate(87.96005411980472, 27, 98, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculatorNonStatic.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculatorNonStatic.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        input.BMICalculatorNonStatic testedObject = new input.BMICalculatorNonStatic((char) 803);
        // Object result = testedObject.calculate(75.77124620970774, 0, 22);
        Object result = testedObject.calculate(75.77124620970774, 0, 22, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculatorNonStatic.Type.OBESE))
        assertTrue(result.equals(input.BMICalculatorNonStatic.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        input.BMICalculatorNonStatic testedObject = new input.BMICalculatorNonStatic((char) 67);
        // Object result = testedObject.calculate(98.95168635596512, 3, 23);
        Object result = testedObject.calculate(98.95168635596512, 3, 23, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculatorNonStatic.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculatorNonStatic.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        input.BMICalculatorNonStatic testedObject = new input.BMICalculatorNonStatic((char) 786);
        // Object result = testedObject.calculate(23.372602893343974, 0, 25);
        Object result = testedObject.calculate(23.372602893343974, 0, 25, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculatorNonStatic.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculatorNonStatic.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }
}
