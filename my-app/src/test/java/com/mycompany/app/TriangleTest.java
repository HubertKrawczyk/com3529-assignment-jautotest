// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (9/9)
Condition coverage: 100.0% (14/14)
Number of tests generated: 5
Test file generated automatically, please check the assertations.
*/
public class TriangleTest {

    @Test()
    public void test1() {
        // Object result = Triangle.classify(99, 24, 4);
        Object result = input.Triangle.classify(99, 24, 4, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.INVALID))
        assertTrue(result.equals(input.Triangle.Type.INVALID));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = Triangle.classify(53, 60, 95);
        Object result = input.Triangle.classify(53, 60, 95, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = Triangle.classify(63, 8, 63);
        Object result = input.Triangle.classify(63, 8, 63, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.ISOSCELES))
        assertTrue(result.equals(input.Triangle.Type.ISOSCELES));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = Triangle.classify(88, 84, 84);
        Object result = input.Triangle.classify(88, 84, 84, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.SCALENE))
        assertTrue(result.equals(input.Triangle.Type.SCALENE));
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = Triangle.classify(1, 1, 1);
        Object result = input.Triangle.classify(1, 1, 1, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result.equals(Triangle.Type.EQUILATERAL))
        assertTrue(result.equals(input.Triangle.Type.EQUILATERAL));
        // remove the line above and uncomment upper one to test original code
    }
}
