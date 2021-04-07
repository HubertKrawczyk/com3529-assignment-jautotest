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
public class BMICalculatorTest {

    @Test()
    public void test1() {
        // Object result = BMICalculator.calculate(45.433483798205486, 86, 89);
        Object result = input.BMICalculator.calculate(45.433483798205486, 86, 89, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(24.49102557601496, 0, 17);
        Object result = input.BMICalculator.calculate(24.49102557601496, 0, 17, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OBESE))
        assertTrue(result.equals(input.BMICalculator.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(49.09316603610813, 0, 42);
        Object result = input.BMICalculator.calculate(49.09316603610813, 0, 42, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculator.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(94.42956781878887, 1, 37);
        Object result = input.BMICalculator.calculate(94.42956781878887, 1, 37, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }
}
