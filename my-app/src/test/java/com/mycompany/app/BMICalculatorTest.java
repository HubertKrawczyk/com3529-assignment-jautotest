// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (4/4)
Condition coverage: 80.0% (8/10)
Test file generated automatically, please check the assertations.
*/
public class BMICalculatorTest {

    @Test()
    public void test1() {
        // Object result = BMICalculator.calculate(71.44608519895766, 60, 29);
        Object result = input.BMICalculator.calculate(71.44608519895766, 60, 29, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(76.10849514548832, 4, 2);
        Object result = input.BMICalculator.calculate(76.10849514548832, 4, 2, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculator.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(48.43311414241964, 1, 11);
        Object result = input.BMICalculator.calculate(48.43311414241964, 1, 11, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OBESE))
        assertTrue(result.equals(input.BMICalculator.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(23.26576371219604, 2, 1);
        Object result = input.BMICalculator.calculate(23.26576371219604, 2, 1, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }
}
