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
        // Object result = BMICalculator.calculate(87.27029535051442, 70, 43);
        Object result = input.BMICalculator.calculate(87.27029535051442, 70, 43, null, null);
        // remove the line above and uncomment upper one to test original code
        // method output: UNDERWEIGHT
        // please change the contents of assertation to a condition involving 'result'
        assertTrue(true);
    }

    @Test()
    public void test2() {
        // Object result = BMICalculator.calculate(15.732878817868567, 1, 6);
        Object result = input.BMICalculator.calculate(15.732878817868567, 1, 6, null, null);
        // remove the line above and uncomment upper one to test original code
        // method output: OBESE
        // please change the contents of assertation to a condition involving 'result'
        assertTrue(true);
    }

    @Test()
    public void test3() {
        // Object result = BMICalculator.calculate(87.54170900189875, 0, 48);
        Object result = input.BMICalculator.calculate(87.54170900189875, 0, 48, null, null);
        // remove the line above and uncomment upper one to test original code
        // method output: OVERWEIGHT
        // please change the contents of assertation to a condition involving 'result'
        assertTrue(true);
    }

    @Test()
    public void test4() {
        // Object result = BMICalculator.calculate(27.01965270816237, 0, 31);
        Object result = input.BMICalculator.calculate(27.01965270816237, 0, 31, null, null);
        // remove the line above and uncomment upper one to test original code
        // method output: NORMAL
        // please change the contents of assertation to a condition involving 'result'
        assertTrue(true);
    }
}
