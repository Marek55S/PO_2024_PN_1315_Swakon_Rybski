package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void vectorEualToItself() {
        var orginalVector = new Vector2d(0, 0);
        Assertions.assertEquals(orginalVector, orginalVector);

    }

    @Test
    public void vectorEqualsToDiffrentVectorSameValues() {
        var orginalVector = new Vector2d(0, 0);
        var diffrentVectorOfClassSameValues = new Vector2d(0, 0);
        Assertions.assertEquals(orginalVector, diffrentVectorOfClassSameValues);
    }

    @Test
    public void vectorNotEqualsToDiffrentVectorDiffrentValues() {
        var orginalVector = new Vector2d(0, 0);
        var diffrentVectorOfClassDiffrentValues = new Vector2d(1, 1);
        Assertions.assertNotEquals(orginalVector, diffrentVectorOfClassDiffrentValues);
    }

    @Test
    public void vectorNotEqualsToGenericObject() {
        var orginalVector = new Vector2d(0, 0);
        var genericVector = new Object();
        Assertions.assertNotEquals(orginalVector, genericVector);
    }

    @Test
    public void vectorParsesToStringWithNonNegativeCoordinates() {
        var orginalVector = new Vector2d(0, 0);
        Assertions.assertEquals("(0,0)", orginalVector.toString());
    }

    @Test
    public void vectorParsesToStringWithNegativeCoordinates() {
        var negativeVector = new Vector2d(-1, -1);
        Assertions.assertEquals("(-1,-1)", negativeVector.toString());
    }

    @Test
    public void vectorPreceeds() {
        //Basic tests - test basic cases
        var orginalVector = new Vector2d(0, 0);
        //Self preceding test
        Assertions.assertTrue(orginalVector.precedes(orginalVector));

        var twoSmallerCoordsVector = new Vector2d(-1, -1);

        Assertions.assertFalse(orginalVector.precedes(twoSmallerCoordsVector));

        var xSmallerYEqualVector = new Vector2d(-1, 0);

        Assertions.assertFalse(orginalVector.precedes(xSmallerYEqualVector));

        var xSmallerYBigger = new Vector2d(-1, 1);

        Assertions.assertFalse(orginalVector.precedes(xSmallerYBigger));

        var xEqualYSmaller = new Vector2d(0, -1);

        Assertions.assertFalse(orginalVector.precedes(xEqualYSmaller));

        var xEqualYBigger = new Vector2d(0, 1);
        Assertions.assertTrue(orginalVector.precedes(xEqualYBigger));

        var xBiggerYsmaller = new Vector2d(1, -1);
        Assertions.assertFalse(orginalVector.precedes(xBiggerYsmaller));
        var xBiggerYEqual = new Vector2d(1, 0);
        Assertions.assertTrue(orginalVector.precedes(xBiggerYEqual));
        var xBiggerYBigger = new Vector2d(1, 1);
        Assertions.assertTrue(orginalVector.precedes(xBiggerYBigger));

    }

    @Test
    public void vectorFollows() {
        //Basic tests - test basic cases
        var orginalVector = new Vector2d(0, 0);
        //Self preceding test
        Assertions.assertTrue(orginalVector.follows(orginalVector));

        var twoSmallerCoordsVector = new Vector2d(-1, -1);

        Assertions.assertTrue(orginalVector.follows(twoSmallerCoordsVector));

        var xSmallerYEqualVector = new Vector2d(-1, 0);

        Assertions.assertTrue(orginalVector.follows(xSmallerYEqualVector));

        var xSmallerYBigger = new Vector2d(-1, 1);

        Assertions.assertFalse(orginalVector.follows(xSmallerYBigger));

        var xEqualYSmaller = new Vector2d(0, -1);

        Assertions.assertTrue(orginalVector.follows(xEqualYSmaller));

        var xEqualYBigger = new Vector2d(0, 1);
        Assertions.assertFalse(orginalVector.follows(xEqualYBigger));

        var xBiggerYsmaller = new Vector2d(1, -1);
        Assertions.assertFalse(orginalVector.follows(xBiggerYsmaller));
        var xBiggerYEqual = new Vector2d(1, 0);
        Assertions.assertFalse(orginalVector.follows(xBiggerYEqual));
        var xBiggerYBigger = new Vector2d(1, 1);
        Assertions.assertFalse(orginalVector.follows(xBiggerYBigger));
    }

    @Test
    public void vectorReturnsUpperRightBasicTests() {
        //Basic tests - test basic cases
        var orginalVector = new Vector2d(0, 0);

        var twoSmallerCoordsVector = new Vector2d(-1, -1);

        Assertions.assertEquals(orginalVector, orginalVector.upperRight(twoSmallerCoordsVector));

        var twoBiggerCoordsVector = new Vector2d(1, 1);

        Assertions.assertEquals(twoBiggerCoordsVector, orginalVector.upperRight(twoBiggerCoordsVector));

        var oneBigerOneLower = new Vector2d(-1, 1);

        Assertions.assertEquals(new Vector2d(0, 1), orginalVector.upperRight(oneBigerOneLower));

    }


    @Test
    public void vectorReturnsLowerLeftBasicTests() {
        //Basic tests - test basic cases
        var orginalVector = new Vector2d(0, 0);

        var twoSmallerCoordsVector = new Vector2d(-1, -1);

        Assertions.assertEquals(twoSmallerCoordsVector, orginalVector.lowerLeft(twoSmallerCoordsVector));

        var twoBiggerCoordsVector = new Vector2d(1, 1);

        Assertions.assertEquals(orginalVector, orginalVector.lowerLeft(twoBiggerCoordsVector));

        var oneBigerOneLower = new Vector2d(-1, 1);

        Assertions.assertEquals(new Vector2d(-1, 0), orginalVector.lowerLeft(oneBigerOneLower));
    }


    @Test
    public void vectorReturnsAdditionOfSuppliedVectorBasicTests() {
        //Basic tests - test basic cases
        var twoZeros = new Vector2d(0, 0);

        var twoMinusOnes = new Vector2d(-1, -1);

        Assertions.assertEquals(twoMinusOnes, twoZeros.add(twoMinusOnes));

        var twoOnes = new Vector2d(1, 1);

        Assertions.assertEquals(twoZeros, twoMinusOnes.add(twoOnes));

        var minusOneOne = new Vector2d(-1, 1);

        Assertions.assertEquals(new Vector2d(0, 2), minusOneOne.add(twoOnes));
    }


    @Test
    public void vectorReturnsSubstractionOfSuppliedVectorBasicTests() {
        //Basic tests - test basic cases
        var twoZeros = new Vector2d(0, 0);

        var twoMinusOnes = new Vector2d(-1, -1);

        var twoOnes = new Vector2d(1, 1);

        var minusOneOne = new Vector2d(-1, 1);

        Assertions.assertEquals(twoOnes, twoZeros.subtract(twoMinusOnes));

        Assertions.assertEquals(new Vector2d(-2, -2), twoMinusOnes.subtract(twoOnes));

        Assertions.assertEquals(new Vector2d(0, -2), twoMinusOnes.subtract(minusOneOne));
    }

    @Test
    public void vectorReturnsOppositeVectorSimpleTests() {
        //Basic tests - test basic cases
        var twoZeros = new Vector2d(0, 0);

        var twoMinusOnes = new Vector2d(-1, -1);

        var twoOnes = new Vector2d(1, 1);

        var minusOneOne = new Vector2d(-1, 1);

        Assertions.assertEquals(twoZeros, twoZeros.opposite());
        Assertions.assertEquals(twoMinusOnes, twoOnes.opposite());
        Assertions.assertEquals(new Vector2d(1, -1), minusOneOne.opposite());
    }


}
