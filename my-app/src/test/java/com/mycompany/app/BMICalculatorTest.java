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
        // Object result = BMICalculator.calculate(34.96740107256689, 20, 35);
        Object result = input.BMICalculator.calculate(34.96740107256689, 20, 35, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.UNDERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.UNDERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(20.852589232790343, 1, 14);
        Object result = input.BMICalculator.calculate(20.852589232790343, 1, 14, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.NORMAL))
        assertTrue(result.equals(input.BMICalculator.Type.NORMAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(64.42677263505371, 2, 14);
        Object result = input.BMICalculator.calculate(64.42677263505371, 2, 14, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OBESE))
        assertTrue(result.equals(input.BMICalculator.Type.OBESE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(10.402111810139425, 1, 5);
        Object result = input.BMICalculator.calculate(10.402111810139425, 1, 5, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(BMICalculator.Type.OVERWEIGHT))
        assertTrue(result.equals(input.BMICalculator.Type.OVERWEIGHT));
        // remove the line above and uncomment upper one to test original code
    }
}
