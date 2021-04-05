// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (9/9)
Condition coverage: 100.0% (14/14)
Number of tests generated: 8
Test file generated automatically, please check the assertations.
*/
public class TriangleTest {

    @Test()
    public void test1() {
        // Object result = Triangle.classify(1, 18, 38);
        Object result = input.Triangle.classify(1, 18, 38, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = Triangle.classify(25, 96, 39);
        Object result = input.Triangle.classify(25, 96, 39, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = Triangle.classify(64, 69, 75);
        Object result = input.Triangle.classify(64, 69, 75, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = Triangle.classify(96, 1, 90);
        Object result = input.Triangle.classify(96, 1, 90, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = Triangle.classify(72, 91, 1);
        Object result = input.Triangle.classify(72, 91, 1, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = Triangle.classify(25, 46, 25);
        Object result = input.Triangle.classify(25, 46, 25, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test7() {
        // Object result = Triangle.classify(68, 68, 59);
        Object result = input.Triangle.classify(68, 68, 59, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.ISOSCELES))
        assertTrue(result.equals(input.Triangle.Type.ISOSCELES));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test8() {
        // Object result = Triangle.classify(4, 4, 4);
        Object result = input.Triangle.classify(4, 4, 4, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.EQUILATERAL))
        assertTrue(result.equals(input.Triangle.Type.EQUILATERAL));
        // remove the line above and uncomment upper one to test original code
    }
}
