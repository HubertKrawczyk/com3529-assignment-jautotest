// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100,00% (9/9)
Condition coverage: 400,00% (14/14)
Restricted MCDC coverage: 100,00% (7/7)
Restricted MCDC - covered conditions:[1, 2, 3, 4, 5, 6, 7]

Number of tests generated: 13
Test file generated automatically, please check the assertations.
*/
public class TriangleTest {

    @Test()
    public void test1() {
        // Object result = Triangle.classify(70, 20, 77);
        Object result = input.Triangle.classify(70, 20, 77, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = Triangle.classify(18, 49, 40);
        Object result = input.Triangle.classify(18, 49, 40, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = Triangle.classify(26, 65, 63);
        Object result = input.Triangle.classify(26, 65, 63, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = Triangle.classify(9, 64, 38);
        Object result = input.Triangle.classify(9, 64, 38, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = Triangle.classify(96, 37, 56);
        Object result = input.Triangle.classify(96, 37, 56, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = Triangle.classify(64, 77, 57);
        Object result = input.Triangle.classify(64, 77, 57, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test7() {
        // Object result = Triangle.classify(80, 90, 89);
        Object result = input.Triangle.classify(80, 90, 89, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test8() {
        // Object result = Triangle.classify(89, 89, 64);
        Object result = input.Triangle.classify(89, 89, 64, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.ISOSCELES))
        assertTrue(result.equals(input.Triangle.Type.ISOSCELES));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test9() {
        // Object result = Triangle.classify(27, 34, 80);
        Object result = input.Triangle.classify(27, 34, 80, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test10() {
        // Object result = Triangle.classify(54, 54, 58);
        Object result = input.Triangle.classify(54, 54, 58, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test11() {
        // Object result = Triangle.classify(37, 31, 77);
        Object result = input.Triangle.classify(37, 31, 77, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test12() {
        // Object result = Triangle.classify(94, 94, 94);
        Object result = input.Triangle.classify(94, 94, 94, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.EQUILATERAL))
        assertTrue(result.equals(input.Triangle.Type.EQUILATERAL));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test13() {
        // Object result = Triangle.classify(79, 45, 80);
        Object result = input.Triangle.classify(79, 45, 80, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }
}
