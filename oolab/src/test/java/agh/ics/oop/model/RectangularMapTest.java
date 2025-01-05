package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void firstBasicTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Animal animal = new Animal();
        Boundary mapBounds = defaultMap.getCurrentBounds();
        //when
        try {
            defaultMap.place(animal);
        } catch(IncorrectPositionException e){
            fail(e.getMessage() + " exception should not be thrown");
        }
        defaultMap.move(animal,MoveDirection.FORWARD);
        //then
        assertEquals(new Vector2d(2,3),animal.getPosition());
        assertEquals(MapDirection.NORTH,animal.getOrientation());
        assertEquals(new Vector2d(0,0),mapBounds.LowerLeft());
        assertEquals(new Vector2d(4,4),mapBounds.UpperRight());
    }

    @Test
    void CanMoveToTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Vector2d offTheMap = new Vector2d(7,8);
        Vector2d occupiedCell = new Vector2d(2,2);
        Vector2d freeCell = new Vector2d(1,0);
        Animal animal = new Animal();
        //when
        try {
            defaultMap.place(animal);
        } catch(IncorrectPositionException e){
            fail(e.getMessage() + " exception should not be thrown");
        }
        //then
        assertFalse(defaultMap.canMoveTo(offTheMap));
        assertFalse(defaultMap.canMoveTo(occupiedCell));
        assertTrue(defaultMap.canMoveTo(freeCell));
    }

    @Test
    void placeTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3,3));
        Animal animal3 = new Animal(new Vector2d(8,8));
        Animal animal4 = new Animal();
        //then
        try {
            defaultMap.place(animal1);
            defaultMap.place(animal2);
        }catch(IncorrectPositionException e){
                fail(e.getMessage() + " exception should not be thrown");
        }
        assertThrows(IncorrectPositionException.class, () -> defaultMap.place(animal3));
        assertThrows(IncorrectPositionException.class, () -> defaultMap.place(animal4));
    }

    @Test
    void moveTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2,3));
        //when
        try {
            defaultMap.place(animal1);
            defaultMap.place(animal2);
        } catch(IncorrectPositionException e){
            fail(e.getMessage() + " exception should not be thrown");
        }
        defaultMap.move(animal1,MoveDirection.FORWARD);
        defaultMap.move(animal1,MoveDirection.RIGHT);
        defaultMap.move(animal2,MoveDirection.LEFT);
        defaultMap.move(animal2,MoveDirection.FORWARD);
        //then
        assertEquals(new Vector2d(2,2),animal1.getPosition());
        assertEquals(MapDirection.EAST,animal1.getOrientation());
        assertEquals(new Vector2d(1,3),animal2.getPosition());
        assertEquals(MapDirection.WEST,animal2.getOrientation());
    }

    @Test
    void isOccupiedTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Animal animal1 = new Animal();
        //when
        try {
            defaultMap.place(animal1);
        } catch(IncorrectPositionException e){
            fail(e.getMessage() + " exception should not be thrown");
        }
        //then
        assertTrue(defaultMap.isOccupied(new Vector2d(2,2)));
        assertFalse(defaultMap.isOccupied(new Vector2d(3,3)));
    }

    @Test
    void objectAtTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3,3));
        //when
        animal2.move(MoveDirection.RIGHT,defaultMap);
        try {
            defaultMap.place(animal1);
            defaultMap.place(animal2);
        } catch(IncorrectPositionException e){
            fail(e.getMessage() + " exception should not be thrown");
        }
        //then
        assertEquals(animal1,defaultMap.objectAt(new Vector2d(2,2)));
        assertEquals(animal2,defaultMap.objectAt(new Vector2d(3,3)));
    }

    //ufam, że MapVisualizer działa poprawnie

    @Test
    void isInMapBoundsTest(){
        //given
        RectangularMap otherMap = new RectangularMap(10,5);
        //then
        assertTrue(otherMap.isInMapBounds(new Vector2d(2,2)));
        assertFalse(otherMap.isInMapBounds(new Vector2d(11,-2)));
        assertFalse(otherMap.isInMapBounds(new Vector2d(7,7)));
    }

    @Test
    void getElementsTest(){
        //given
        RectangularMap defaultMap = new RectangularMap(5,5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3,3));
        Animal animal3 = new Animal(new Vector2d(1,2));
        //when
        try {
            defaultMap.place(animal1);
            defaultMap.place(animal2);
            defaultMap.place(animal3);
        } catch(IncorrectPositionException e){
            fail(e.getMessage() + " exception should not be thrown");
        }
        //then
        assertEquals(3,defaultMap.getElements().size());
    }
}