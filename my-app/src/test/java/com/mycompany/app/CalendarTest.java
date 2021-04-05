// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (14/14)
Condition coverage: 100.0% (36/36)
Number of tests generated: 11
Test file generated automatically, please check the assertations.
*/
public class CalendarTest {

    @Test()
    public void test1() {
        // Object result = Calendar.daysBetweenTwoDates(61, 3, 57, 46, 5, 82);
        Object result = input.Calendar.daysBetweenTwoDates(61, 3, 57, 46, 5, 82, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(5418));
    }

    @Test()
    public void test2() {
        // Object result = Calendar.daysBetweenTwoDates(17, 99, 94, 58, 5, 1);
        Object result = input.Calendar.daysBetweenTwoDates(17, 99, 94, 58, 5, 1, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(14731));
    }

    @Test()
    public void test3() {
        // Object result = Calendar.daysBetweenTwoDates(98, 23, 11, 97, 19, 30);
        Object result = input.Calendar.daysBetweenTwoDates(98, 23, 11, 97, 19, 30, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(346));
    }

    @Test()
    public void test4() {
        // Object result = Calendar.daysBetweenTwoDates(11, 88, 87, 11, 69, 88);
        Object result = input.Calendar.daysBetweenTwoDates(11, 88, 87, 11, 69, 88, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(0));
    }

    @Test()
    public void test5() {
        // Object result = Calendar.daysBetweenTwoDates(21, 48, 51, 64, 68, 0);
        Object result = input.Calendar.daysBetweenTwoDates(21, 48, 51, 64, 68, 0, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(15676));
    }

    @Test()
    public void test6() {
        // Object result = Calendar.daysBetweenTwoDates(11, 19, 22, 58, 0, 16);
        Object result = input.Calendar.daysBetweenTwoDates(11, 19, 22, 58, 0, 16, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(16827));
    }

    @Test()
    public void test7() {
        // Object result = Calendar.daysBetweenTwoDates(52, 0, 71, 89, 96, 8);
        Object result = input.Calendar.daysBetweenTwoDates(52, 0, 71, 89, 96, 8, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(13826));
    }

    @Test()
    public void test8() {
        // Object result = Calendar.daysBetweenTwoDates(81, 22, 0, 36, 65, 46);
        Object result = input.Calendar.daysBetweenTwoDates(81, 22, 0, 36, 65, 46, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(16406));
    }

    @Test()
    public void test9() {
        // Object result = Calendar.daysBetweenTwoDates(9, 9, 22, 9, 92, 42);
        Object result = input.Calendar.daysBetweenTwoDates(9, 9, 22, 9, 92, 42, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(100));
    }

    @Test()
    public void test10() {
        // Object result = Calendar.daysBetweenTwoDates(33, 64, 44, 33, 15, 26);
        Object result = input.Calendar.daysBetweenTwoDates(33, 64, 44, 33, 15, 26, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(5));
    }

    @Test()
    public void test11() {
        // Object result = Calendar.daysBetweenTwoDates(68, 97, 37, 68, 0, 90);
        Object result = input.Calendar.daysBetweenTwoDates(68, 97, 37, 68, 0, 90, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(335));
    }
}
