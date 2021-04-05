// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (4/4)
Condition coverage: 100.0% (3/3)
Test file generated automatically, please check the assertations.
*/
public class BMICalculatorTest {

    @Test()
    public void test1() {
        // Object result = BMICalculator.calculate(86.91254087316355, 24, 49);
        Object result = input.BMICalculator.calculate(86.91254087316355, 24, 49, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==BMICalculator.Type.UNDERWEIGHT)
        assertTrue(result == input.BMICalculator.Type.UNDERWEIGHT);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(72.3944455650542, 1, 12);
        Object result = input.BMICalculator.calculate(72.3944455650542, 1, 12, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==BMICalculator.Type.OBESE)
        assertTrue(result == input.BMICalculator.Type.OBESE);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(96.91347568038061, 0, 58);
        Object result = input.BMICalculator.calculate(96.91347568038061, 0, 58, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==BMICalculator.Type.NORMAL)
        assertTrue(result == input.BMICalculator.Type.NORMAL);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(56.21204508423621, 1, 25);
        Object result = input.BMICalculator.calculate(56.21204508423621, 1, 25, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==BMICalculator.Type.OVERWEIGHT)
        assertTrue(result == input.BMICalculator.Type.OVERWEIGHT);
        // remove the line above and uncomment upper one to test original code
    }
}
