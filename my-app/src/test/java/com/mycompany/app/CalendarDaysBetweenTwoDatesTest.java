// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (14/14)
Branch coverage - covered branches: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]

Condition coverage: 100,00% (36/36)
Condition coverage - covered conditions: [-18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]

Correlated MCDC coverage: 100,00% (18/18)
Correlated MCDC - covered conditions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]

Number of tests generated: 21
Test file generated automatically, please check the assertations.
*/
public class CalendarDaysBetweenTwoDatesTest {

    @Test()
    public void test1() {
        // Object result = Calendar.daysBetweenTwoDates(26, 12, 67, 47, 12, 57);
        Object result = input.Calendar.daysBetweenTwoDates(26, 12, 67, 47, 12, 57, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(7670));
    }

    @Test()
    public void test2() {
        // Object result = Calendar.daysBetweenTwoDates(62, 75, 24, 9, 35, 2);
        Object result = input.Calendar.daysBetweenTwoDates(62, 75, 24, 9, 35, 2, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(19380));
    }

    @Test()
    public void test3() {
        // Object result = Calendar.daysBetweenTwoDates(41, 87, 98, 59, 61, 10);
        Object result = input.Calendar.daysBetweenTwoDates(41, 87, 98, 59, 61, 10, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(6553));
    }

    @Test()
    public void test4() {
        // Object result = Calendar.daysBetweenTwoDates(56, 69, 43, 81, 4, 43);
        Object result = input.Calendar.daysBetweenTwoDates(56, 69, 43, 81, 4, 43, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(8886));
    }

    @Test()
    public void test5() {
        // Object result = Calendar.daysBetweenTwoDates(61, 0, 63, 44, 89, 13);
        Object result = input.Calendar.daysBetweenTwoDates(61, 0, 63, 44, 89, 13, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(5893));
    }

    @Test()
    public void test6() {
        // Object result = Calendar.daysBetweenTwoDates(87, 94, 53, 80, 53, 87);
        Object result = input.Calendar.daysBetweenTwoDates(87, 94, 53, 80, 53, 87, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(2556));
    }

    @Test()
    public void test7() {
        // Object result = Calendar.daysBetweenTwoDates(96, 50, 22, 96, 7, 71);
        Object result = input.Calendar.daysBetweenTwoDates(96, 50, 22, 96, 7, 71, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(144));
    }

    @Test()
    public void test8() {
        // Object result = Calendar.daysBetweenTwoDates(56, 42, 43, 58, 1, 3);
        Object result = input.Calendar.daysBetweenTwoDates(56, 42, 43, 58, 1, 3, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(368));
    }

    @Test()
    public void test9() {
        // Object result = Calendar.daysBetweenTwoDates(51, 3, 59, 87, 19, 52);
        Object result = input.Calendar.daysBetweenTwoDates(51, 3, 59, 87, 19, 52, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(13424));
    }

    @Test()
    public void test10() {
        // Object result = Calendar.daysBetweenTwoDates(82, 14, 45, 26, 10, 0);
        Object result = input.Calendar.daysBetweenTwoDates(82, 14, 45, 26, 10, 0, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(20545));
    }

    @Test()
    public void test11() {
        // Object result = Calendar.daysBetweenTwoDates(58, 25, 93, 46, 55, 22);
        Object result = input.Calendar.daysBetweenTwoDates(58, 25, 93, 46, 55, 22, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(4392));
    }

    @Test()
    public void test12() {
        // Object result = Calendar.daysBetweenTwoDates(54, 15, 88, 52, 0, 12);
        Object result = input.Calendar.daysBetweenTwoDates(54, 15, 88, 52, 0, 12, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(1084));
    }

    @Test()
    public void test13() {
        // Object result = Calendar.daysBetweenTwoDates(71, 41, 91, 86, 91, 69);
        Object result = input.Calendar.daysBetweenTwoDates(71, 41, 91, 86, 91, 69, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(5479));
    }

    @Test()
    public void test14() {
        // Object result = Calendar.daysBetweenTwoDates(5, 63, 0, 64, 68, 39);
        Object result = input.Calendar.daysBetweenTwoDates(5, 63, 0, 64, 68, 39, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(21580));
    }

    @Test()
    public void test15() {
        // Object result = Calendar.daysBetweenTwoDates(52, 67, 98, 27, 14, 94);
        Object result = input.Calendar.daysBetweenTwoDates(52, 67, 98, 27, 14, 94, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(9132));
    }

    @Test()
    public void test16() {
        // Object result = Calendar.daysBetweenTwoDates(97, 67, 59, 97, 34, 10);
        Object result = input.Calendar.daysBetweenTwoDates(97, 67, 59, 97, 34, 10, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(21));
    }

    @Test()
    public void test17() {
        // Object result = Calendar.daysBetweenTwoDates(48, 64, 67, 45, 45, 42);
        Object result = input.Calendar.daysBetweenTwoDates(48, 64, 67, 45, 45, 42, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(1096));
    }

    @Test()
    public void test18() {
        // Object result = Calendar.daysBetweenTwoDates(68, 5, 32, 68, 42, 94);
        Object result = input.Calendar.daysBetweenTwoDates(68, 5, 32, 68, 42, 94, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(214));
    }

    @Test()
    public void test19() {
        // Object result = Calendar.daysBetweenTwoDates(62, 50, 55, 43, 36, 6);
        Object result = input.Calendar.daysBetweenTwoDates(62, 50, 55, 43, 36, 6, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(6965));
    }

    @Test()
    public void test20() {
        // Object result = Calendar.daysBetweenTwoDates(75, 93, 10, 75, 90, 91);
        Object result = input.Calendar.daysBetweenTwoDates(75, 93, 10, 75, 90, 91, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(21));
    }

    @Test()
    public void test21() {
        // Object result = Calendar.daysBetweenTwoDates(41, 69, 84, 61, 83, 55);
        Object result = input.Calendar.daysBetweenTwoDates(41, 69, 84, 61, 83, 55, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(7305));
    }
}
