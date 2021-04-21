// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.00% (4/4)
Branch coverage - covered branches: [1, 2, 3, 4]

Condition coverage: 80.00% (8/10)
Condition coverage - covered conditions: [-5, -3, -1, 1, 2, 3, 4, 5]

Correlated MCDC coverage: 60.00% (3/5)
Correlated MCDC - covered conditions: [1, 3, 5]

Number of tests generated: 7
Test file generated automatically at 21-04-2021 15:48:05, please check the assertations.
*/
public class BMICalculatorCalculateTest {

    @Test()
    public void test1() {
        // Object result = BMICalculator.calculate(70.17876002544757, 9, 97);
        Object result = input.BMICalculator.calculate(70.17876002544757, 9, 97, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(90.24993388539164, 0, 6);
        Object result = input.BMICalculator.calculate(90.24993388539164, 0, 6, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OBESE))
        assertTrue(result.equals(input.BMICalculator.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(18.014330001885327, 89, 80);
        Object result = input.BMICalculator.calculate(18.014330001885327, 89, 80, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(65.21691686207456, 3, 12);
        Object result = input.BMICalculator.calculate(65.21691686207456, 3, 12, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculator.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = BMICalculator.calculate(76.7069157348516, 0, 75);
        Object result = input.BMICalculator.calculate(76.7069157348516, 0, 75, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = BMICalculator.calculate(88.2752638457937, 2, 23);
        Object result = input.BMICalculator.calculate(88.2752638457937, 2, 23, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test7() {
        // Object result = BMICalculator.calculate(31.212177496138295, 92, 66);
        Object result = input.BMICalculator.calculate(31.212177496138295, 92, 66, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }
}
