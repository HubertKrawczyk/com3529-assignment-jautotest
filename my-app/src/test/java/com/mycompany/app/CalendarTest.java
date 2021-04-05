// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (14/14)
Condition coverage: 100.0% (14/14)
Test file generated automatically, please check the assertations.
*/
public class CalendarTest {

    @Test()
    public void test1() {
        // Object result = Calendar.daysBetweenTwoDates(65, 1, 64, 10, 41, 54);
        Object result = input.Calendar.daysBetweenTwoDates(65, 1, 64, 10, 41, 54, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(19755));
    }

    @Test()
    public void test2() {
        // Object result = Calendar.daysBetweenTwoDates(49, 96, 20, 37, 96, 10);
        Object result = input.Calendar.daysBetweenTwoDates(49, 96, 20, 37, 96, 10, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(4393));
    }

    @Test()
    public void test3() {
        // Object result = Calendar.daysBetweenTwoDates(64, 11, 51, 93, 67, 9);
        Object result = input.Calendar.daysBetweenTwoDates(64, 11, 51, 93, 67, 9, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(10601));
    }

    @Test()
    public void test4() {
        // Object result = Calendar.daysBetweenTwoDates(0, 87, 36, 95, 0, 68);
        Object result = input.Calendar.daysBetweenTwoDates(0, 87, 36, 95, 0, 68, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(34364));
    }

    @Test()
    public void test5() {
        // Object result = Calendar.daysBetweenTwoDates(90, 83, 0, 41, 84, 12);
        Object result = input.Calendar.daysBetweenTwoDates(90, 83, 0, 41, 84, 12, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(17886));
    }

    @Test()
    public void test6() {
        // Object result = Calendar.daysBetweenTwoDates(97, 0, 57, 85, 17, 93);
        Object result = input.Calendar.daysBetweenTwoDates(97, 0, 57, 85, 17, 93, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(4049));
    }

    @Test()
    public void test7() {
        // Object result = Calendar.daysBetweenTwoDates(76, 68, 6, 76, 56, 61);
        Object result = input.Calendar.daysBetweenTwoDates(76, 68, 6, 76, 56, 61, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(25));
    }

    @Test()
    public void test8() {
        // Object result = Calendar.daysBetweenTwoDates(28, 61, 6, 76, 22, 0);
        Object result = input.Calendar.daysBetweenTwoDates(28, 61, 6, 76, 22, 0, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(17527));
    }

    @Test()
    public void test9() {
        // Object result = Calendar.daysBetweenTwoDates(8, 94, 13, 8, 0, 94);
        Object result = input.Calendar.daysBetweenTwoDates(8, 94, 13, 8, 0, 94, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(317));
    }
}
