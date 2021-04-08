// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (14/14)
Branch coverage - covered branches: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]

Condition coverage: 100,00% (36/36)
Condition coverage - covered conditions: [-18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]

Correlated MCDC coverage: 94,44% (17/18)
Correlated MCDC - covered conditions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]

Number of tests generated: 18
Test file generated automatically, please check the assertations.
*/
public class CalendarTest {

    @Test()
    public void test1() {
        // Object result = Calendar.daysBetweenTwoDates(98, 65, 70, 98, 1, 13);
        Object result = input.Calendar.daysBetweenTwoDates(98, 65, 70, 98, 1, 13, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(352));
    }

    @Test()
    public void test2() {
        // Object result = Calendar.daysBetweenTwoDates(36, 27, 4, 68, 51, 4);
        Object result = input.Calendar.daysBetweenTwoDates(36, 27, 4, 68, 51, 4, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(11688));
    }

    @Test()
    public void test3() {
        // Object result = Calendar.daysBetweenTwoDates(23, 97, 56, 74, 61, 91);
        Object result = input.Calendar.daysBetweenTwoDates(23, 97, 56, 74, 61, 91, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(18628));
    }

    @Test()
    public void test4() {
        // Object result = Calendar.daysBetweenTwoDates(95, 2, 37, 56, 24, 9);
        Object result = input.Calendar.daysBetweenTwoDates(95, 2, 37, 56, 24, 9, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(13960));
    }

    @Test()
    public void test5() {
        // Object result = Calendar.daysBetweenTwoDates(98, 53, 99, 40, 57, 40);
        Object result = input.Calendar.daysBetweenTwoDates(98, 53, 99, 40, 57, 40, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(21184));
    }

    @Test()
    public void test6() {
        // Object result = Calendar.daysBetweenTwoDates(65, 46, 69, 13, 0, 5);
        Object result = input.Calendar.daysBetweenTwoDates(65, 46, 69, 13, 0, 5, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(19353));
    }

    @Test()
    public void test7() {
        // Object result = Calendar.daysBetweenTwoDates(71, 21, 62, 97, 42, 13);
        Object result = input.Calendar.daysBetweenTwoDates(71, 21, 62, 97, 42, 13, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(9479));
    }

    @Test()
    public void test8() {
        // Object result = Calendar.daysBetweenTwoDates(53, 0, 40, 60, 13, 37);
        Object result = input.Calendar.daysBetweenTwoDates(53, 0, 40, 60, 13, 37, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(2891));
    }

    @Test()
    public void test9() {
        // Object result = Calendar.daysBetweenTwoDates(53, 56, 46, 95, 16, 4);
        Object result = input.Calendar.daysBetweenTwoDates(53, 56, 46, 95, 16, 4, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(15313));
    }

    @Test()
    public void test10() {
        // Object result = Calendar.daysBetweenTwoDates(63, 53, 0, 95, 14, 83);
        Object result = input.Calendar.daysBetweenTwoDates(63, 53, 0, 95, 14, 83, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(11718));
    }

    @Test()
    public void test11() {
        // Object result = Calendar.daysBetweenTwoDates(86, 52, 23, 73, 75, 81);
        Object result = input.Calendar.daysBetweenTwoDates(86, 52, 23, 73, 75, 81, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(4740));
    }

    @Test()
    public void test12() {
        // Object result = Calendar.daysBetweenTwoDates(7, 6, 91, 61, 57, 0);
        Object result = input.Calendar.daysBetweenTwoDates(7, 6, 91, 61, 57, 0, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(19878));
    }

    @Test()
    public void test13() {
        // Object result = Calendar.daysBetweenTwoDates(55, 41, 70, 1, 24, 87);
        Object result = input.Calendar.daysBetweenTwoDates(55, 41, 70, 1, 24, 87, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(19723));
    }

    @Test()
    public void test14() {
        // Object result = Calendar.daysBetweenTwoDates(47, 21, 57, 47, 74, 77);
        Object result = input.Calendar.daysBetweenTwoDates(47, 21, 57, 47, 74, 77, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(0));
    }

    @Test()
    public void test15() {
        // Object result = Calendar.daysBetweenTwoDates(4, 5, 4, 16, 78, 97);
        Object result = input.Calendar.daysBetweenTwoDates(4, 5, 4, 16, 78, 97, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(4624));
    }

    @Test()
    public void test16() {
        // Object result = Calendar.daysBetweenTwoDates(30, 31, 66, 30, 13, 25);
        Object result = input.Calendar.daysBetweenTwoDates(30, 31, 66, 30, 13, 25, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(6));
    }

    @Test()
    public void test17() {
        // Object result = Calendar.daysBetweenTwoDates(78, 4, 15, 78, 24, 99);
        Object result = input.Calendar.daysBetweenTwoDates(78, 4, 15, 78, 24, 99, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(260));
    }

    @Test()
    public void test18() {
        // Object result = Calendar.daysBetweenTwoDates(89, 18, 51, 55, 79, 82);
        Object result = input.Calendar.daysBetweenTwoDates(89, 18, 51, 55, 79, 82, null, null);
        // remove the line above and uncomment upper one to test original code
        assertTrue(result.equals(12419));
    }
}
