package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void equalsFourCases(){
        //when
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(2,2);

        //then
        assertEquals(v1, v1);
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(v1,v1.toString());

    }

    @Test
    void toStringPositiveAndNegative(){
        //when
        Vector2d positive = new Vector2d(1,2);
        Vector2d negative = new Vector2d(-1,-2);
        Vector2d zero = new Vector2d(0,0);

        //then
        assertEquals("(1,2)",positive.toString());
        assertEquals("(-1,-2)",negative.toString());
        assertEquals("(0,0)",zero.toString());

    }

    @Test
    void precedesBiggerSmallerEqual(){
        //when
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(2,3);

        //then
        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v1));
        assertTrue(v2.precedes(v3));
        assertFalse(v2.precedes(v1));
        assertFalse(v3.precedes(v2));
    }

    @Test
    void followsBiggerSmallerEqual(){
        //when
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(2,3);

        //then
        assertTrue(v3.follows(v1));
        assertTrue(v3.follows(v2));
        assertTrue(v3.follows(v3));
        assertFalse(v2.follows(v3));
        assertFalse(v1.follows(v2));
    }

    @Test
    void addPositiveNegativeMixedAndZero(){
        //given
        Vector2d v1 = new Vector2d(-1,2);

        //when
        Vector2d positive = v1.add(new Vector2d(2,3));
        Vector2d negative = v1.add(new Vector2d(-2,-1));
        Vector2d zero = v1.add(new Vector2d(0,0));
        Vector2d mixed = v1.add(new Vector2d(1,-2));

        //then
        assertEquals(new Vector2d(1,5),positive);
        assertEquals(new Vector2d(-3,1),negative);
        assertEquals(new Vector2d(-1,2),zero);
        assertEquals(new Vector2d(0,0),mixed);

    }

    @Test
    void subtractPositiveNegativeMixedAndZero(){
        //given
        Vector2d v1 = new Vector2d(-1,2);

        //when
        Vector2d positive = v1.subtract(new Vector2d(2,3));
        Vector2d negative = v1.subtract(new Vector2d(-2,-1));
        Vector2d zero = v1.subtract(new Vector2d(0,0));
        Vector2d mixed = v1.subtract(new Vector2d(1,-2));

        //then
        assertEquals(new Vector2d(-3,-1),positive);
        assertEquals(new Vector2d(1,3),negative);
        assertEquals(new Vector2d(-1,2),zero);
        assertEquals(new Vector2d(-2,4),mixed);
    }

    @Test
    void upperRightBiggerSmallerEqualAndMixed(){
        //given
        Vector2d v1 = new Vector2d(2,1);

        //when
        Vector2d bigger = v1.upperRight(new Vector2d(3,3));
        Vector2d smaller = v1.upperRight(new Vector2d(-2,-1));
        Vector2d equal = v1.upperRight(v1);
        Vector2d mixed = v1.upperRight(new Vector2d(3,-2));

        //then
        assertEquals(new Vector2d(3,3),bigger);
        assertEquals(new Vector2d(2,1),smaller);
        assertEquals(new Vector2d(2,1),equal);
        assertEquals(new Vector2d(3,1),mixed);
    }

    @Test
    void lowerLeftBiggerSmallerEqualAndMixed(){
        //given
        Vector2d v1 = new Vector2d(2,1);

        //when
        Vector2d bigger = v1.lowerLeft(new Vector2d(3,3));
        Vector2d smaller = v1.lowerLeft(new Vector2d(-2,-1));
        Vector2d equal = v1.lowerLeft(v1);
        Vector2d mixed = v1.lowerLeft(new Vector2d(3,-2));

        //then
        assertEquals(new Vector2d(2,1),bigger);
        assertEquals(new Vector2d(-2,-1),smaller);
        assertEquals(new Vector2d(2,1),equal);
        assertEquals(new Vector2d(2,-2),mixed);
    }

    @Test
    void oppositePositiveNegativeZeroAndMixed(){
        //when
        Vector2d positive = new Vector2d(1,2);
        Vector2d negative = new Vector2d(-1,-2);
        Vector2d zero = new Vector2d(0,0);
        Vector2d mixed = new Vector2d(1,-2);

        //then
        assertEquals(negative,positive.opposite());
        assertEquals(positive,negative.opposite());
        assertEquals(zero,zero.opposite());
        assertEquals(new Vector2d(-1,2),mixed.opposite());
    }


}