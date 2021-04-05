// please remove/change this line if used outside of this application
package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
Branch coverage: 100.0% (9/9)
Condition coverage: 100.0% (7/7)
Test file generated automatically, please check the assertations.
*/
public class TriangleTest {

    @Test()
    public void test1() {
        // Object result = Triangle.classify(4, 5, 4);
        Object result = input.Triangle.classify(4, 5, 4, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==Triangle.Type.SCALENE)
        assertTrue(result == input.Triangle.Type.SCALENE);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test2() {
        // Object result = Triangle.classify(7, 4, 5);
        Object result = input.Triangle.classify(7, 4, 5, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==Triangle.Type.SCALENE)
        assertTrue(result == input.Triangle.Type.SCALENE);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test3() {
        // Object result = Triangle.classify(2, 3, 5);
        Object result = input.Triangle.classify(2, 3, 5, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==Triangle.Type.INVALID)
        assertTrue(result == input.Triangle.Type.INVALID);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test4() {
        // Object result = Triangle.classify(8, 5, 2);
        Object result = input.Triangle.classify(8, 5, 2, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==Triangle.Type.INVALID)
        assertTrue(result == input.Triangle.Type.INVALID);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test5() {
        // Object result = Triangle.classify(6, 1, 6);
        Object result = input.Triangle.classify(6, 1, 6, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==Triangle.Type.ISOSCELES)
        assertTrue(result == input.Triangle.Type.ISOSCELES);
        // remove the line above and uncomment upper one to test original code
    }

    @Test()
    public void test6() {
        // Object result = Triangle.classify(6, 6, 6);
        Object result = input.Triangle.classify(6, 6, 6, null, null);
        // remove the line above and uncomment upper one to test original code
        // assertTrue(result==Triangle.Type.EQUILATERAL)
        assertTrue(result == input.Triangle.Type.EQUILATERAL);
        // remove the line above and uncomment upper one to test original code
    }
}
