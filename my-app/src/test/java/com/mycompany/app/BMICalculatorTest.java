// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (4/4)
Branch coverage - covered branches:[1, 2, 3, 4]

Condition coverage: 320,00% (8/10)
Condition coverage - covered conditions:[-5, -3, -1, 1, 2, 3, 4, 5]

Restricted MCDC coverage: 60,00% (3/5)
Restricted MCDC - covered conditions:[1, 3, 5]

Number of tests generated: 7
Test file generated automatically, please check the assertations.
*/
public class BMICalculatorTest {

    @Test()
    public void test1() {
        // Object result = BMICalculator.calculate(47.8850425980211, 80, 59);
        Object result = input.BMICalculator.calculate(47.8850425980211, 80, 59, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(89.04699875134415, 2, 30);
        Object result = input.BMICalculator.calculate(89.04699875134415, 2, 30, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculator.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(34.74261816810552, 13, 66);
        Object result = input.BMICalculator.calculate(34.74261816810552, 13, 66, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(91.38979532957318, 3, 0);
        Object result = input.BMICalculator.calculate(91.38979532957318, 3, 0, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OBESE))
        assertTrue(result.equals(input.BMICalculator.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = BMICalculator.calculate(63.95237710608648, 9, 75);
        Object result = input.BMICalculator.calculate(63.95237710608648, 9, 75, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = BMICalculator.calculate(93.55777114147979, 4, 2);
        Object result = input.BMICalculator.calculate(93.55777114147979, 4, 2, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test7() {
        // Object result = BMICalculator.calculate(77.85136686045678, 19, 37);
        Object result = input.BMICalculator.calculate(77.85136686045678, 19, 37, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }
}
