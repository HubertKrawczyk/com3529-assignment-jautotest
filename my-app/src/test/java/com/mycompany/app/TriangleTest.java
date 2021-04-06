// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (9/9)
Condition coverage: 100.0% (14/14)
Number of tests generated: 6
Test file generated automatically, please check the assertations.
*/
public class TriangleTest {

    @Test()
    public void test1() {
        // Object result = Triangle.classify(46, 59, 47);
        Object result = input.Triangle.classify(46, 59, 47, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = Triangle.classify(7, 38, 82);
        Object result = input.Triangle.classify(7, 38, 82, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = Triangle.classify(40, 40, 55);
        Object result = input.Triangle.classify(40, 40, 55, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = Triangle.classify(74, 53, 17);
        Object result = input.Triangle.classify(74, 53, 17, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = Triangle.classify(64, 29, 64);
        Object result = input.Triangle.classify(64, 29, 64, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.ISOSCELES))
        assertTrue(result.equals(input.Triangle.Type.ISOSCELES));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = Triangle.classify(42, 42, 42);
        Object result = input.Triangle.classify(42, 42, 42, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.EQUILATERAL))
        assertTrue(result.equals(input.Triangle.Type.EQUILATERAL));
        // remove the line above and uncomment upper one to test original code
    }
}
