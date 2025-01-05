package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private final Vector2d upperRight = new Vector2d(4,4);
    private final Vector2d lowerLeft = new Vector2d(0,0);
    @Test
    void animalDefalutInit(){
        //when
        Animal animal = new Animal();
        //then
        assertTrue(animal.isAt(new Vector2d(2, 2)));
        assertEquals(MapDirection.NORTH,animal.getOrientation());
    }

    @Test
    void animalCustomPositionInit(){
        //when
        Animal animal = new Animal(new Vector2d(3,0));
        //then
        assertTrue(animal.isAt(new Vector2d(3,0)));
        assertEquals(MapDirection.NORTH,animal.getOrientation());
    }

    @Test
    void testToString(){
        //when
        Animal animal = new Animal(new Vector2d(3,0));
        //then
        assertEquals("N", animal.toString());
    }

    @Test
    void testIsAt(){
        //when
        Animal animal = new Animal(new Vector2d(3,0));
        //then
        assertTrue(animal.isAt(new Vector2d(3,0)));
        assertFalse(animal.isAt(new Vector2d(0,0)));
    }

    @Test
    void moveRotationLeft(){
        //given
        Animal animal = new Animal();
        WorldMap map = new RectangularMap(5,5);
        //when
        MapDirection noRotation = animal.getOrientation();
        animal.move(MoveDirection.LEFT,map);
        MapDirection oneRotation = animal.getOrientation();
        animal.move(MoveDirection.LEFT,map);
        MapDirection twoRotation = animal.getOrientation();
        animal.move(MoveDirection.LEFT,map);
        MapDirection threeRotation = animal.getOrientation();
        animal.move(MoveDirection.LEFT,map);
        MapDirection fourRotation = animal.getOrientation();
        //then
        assertEquals(MapDirection.NORTH,noRotation);
        assertEquals(MapDirection.WEST,oneRotation);
        assertEquals(MapDirection.SOUTH,twoRotation);
        assertEquals(MapDirection.EAST,threeRotation);
        assertEquals(MapDirection.NORTH,fourRotation);
    }

    @Test
    void moveRotationRight(){
        //given
        Animal animal = new Animal();
        WorldMap map = new RectangularMap(5,5);
        //when
        MapDirection noRotation = animal.getOrientation();
        animal.move(MoveDirection.RIGHT,map);
        MapDirection oneRotation = animal.getOrientation();
        animal.move(MoveDirection.RIGHT,map);
        MapDirection twoRotation = animal.getOrientation();
        animal.move(MoveDirection.RIGHT,map);
        MapDirection threeRotation = animal.getOrientation();
        animal.move(MoveDirection.RIGHT,map);
        MapDirection fourRotation = animal.getOrientation();
        //then
        assertEquals(MapDirection.NORTH,noRotation);
        assertEquals(MapDirection.EAST,oneRotation);
        assertEquals(MapDirection.SOUTH,twoRotation);
        assertEquals(MapDirection.WEST,threeRotation);
        assertEquals(MapDirection.NORTH,fourRotation);
    }

    @Test
    void moveForwardDefault(){
        //given
        Animal animalDefault = new Animal();
        WorldMap map = new RectangularMap(5,5);
        //when
        animalDefault.move(MoveDirection.FORWARD,map);
        Vector2d northForward = animalDefault.getPosition();
        animalDefault.move(MoveDirection.RIGHT,map);
        animalDefault.move(MoveDirection.FORWARD,map);
        Vector2d eastForward = animalDefault.getPosition();
        animalDefault.move(MoveDirection.RIGHT,map);
        animalDefault.move(MoveDirection.FORWARD,map);
        Vector2d southForward = animalDefault.getPosition();
        animalDefault.move(MoveDirection.RIGHT,map);
        animalDefault.move(MoveDirection.FORWARD,map);
        Vector2d westForward = animalDefault.getPosition();
        //then
        assertEquals(new Vector2d(2,3),northForward);
        assertEquals(new Vector2d(3,3),eastForward);
        assertEquals(new Vector2d(3,2),southForward);
        assertEquals(new Vector2d(2,2),westForward);
    }

    @Test
    void moveForwardBorders(){
        //given
        Animal northAnimal = new Animal(upperRight);
        Animal eastAnimal = new Animal(upperRight);
        Animal southAnimal = new Animal(lowerLeft);
        Animal westAnimal = new Animal(lowerLeft);
        WorldMap map = new RectangularMap(5,5);
        //when
        northAnimal.move(MoveDirection.FORWARD,map);
        Vector2d northAnimalPosition = northAnimal.getPosition();

        eastAnimal.move(MoveDirection.RIGHT,map);
        eastAnimal.move(MoveDirection.FORWARD,map);
        Vector2d eastAnimalPosition = eastAnimal.getPosition();

        southAnimal.move(MoveDirection.RIGHT,map);
        southAnimal.move(MoveDirection.RIGHT,map);
        southAnimal.move(MoveDirection.FORWARD,map);
        Vector2d southAnimalPosition = southAnimal.getPosition();

        westAnimal.move(MoveDirection.LEFT,map);
        westAnimal.move(MoveDirection.FORWARD,map);
        Vector2d westAnimalPosition = westAnimal.getPosition();

        //then
        assertEquals(new Vector2d(4,4),northAnimalPosition);
        assertEquals(new Vector2d(4,4),eastAnimalPosition);
        assertEquals(new Vector2d(0,0),southAnimalPosition);
        assertEquals(new Vector2d(0,0),westAnimalPosition);
    }

    @Test
    void moveBackwardDefault(){
        //given
        Animal animalDefault = new Animal();
        WorldMap map = new RectangularMap(5,5);
        //when
        animalDefault.move(MoveDirection.BACKWARD,map);
        Vector2d northBackward = animalDefault.getPosition();
        animalDefault.move(MoveDirection.RIGHT,map);
        animalDefault.move(MoveDirection.BACKWARD,map);
        Vector2d eastBackward = animalDefault.getPosition();
        animalDefault.move(MoveDirection.RIGHT,map);
        animalDefault.move(MoveDirection.BACKWARD,map);
        Vector2d southBackward = animalDefault.getPosition();
        animalDefault.move(MoveDirection.RIGHT,map);
        animalDefault.move(MoveDirection.BACKWARD,map);
        Vector2d westBackward = animalDefault.getPosition();
        //then
        assertEquals(new Vector2d(2,1),northBackward);
        assertEquals(new Vector2d(1,1),eastBackward);
        assertEquals(new Vector2d(1,2),southBackward);
        assertEquals(new Vector2d(2,2),westBackward);
    }

    @Test
    void moveBackwardBorders(){
        //given
        Animal northAnimal = new Animal(lowerLeft);
        Animal eastAnimal = new Animal(lowerLeft);
        Animal southAnimal = new Animal(upperRight);
        Animal westAnimal = new Animal(upperRight);
        WorldMap map = new RectangularMap(5,5);
        //when
        northAnimal.move(MoveDirection.BACKWARD,map);
        Vector2d northAnimalPosition = northAnimal.getPosition();

        eastAnimal.move(MoveDirection.RIGHT,map);
        eastAnimal.move(MoveDirection.BACKWARD,map);
        Vector2d eastAnimalPosition = eastAnimal.getPosition();

        southAnimal.move(MoveDirection.RIGHT,map);
        southAnimal.move(MoveDirection.RIGHT,map);
        southAnimal.move(MoveDirection.BACKWARD,map);
        Vector2d southAnimalPosition = southAnimal.getPosition();

        westAnimal.move(MoveDirection.LEFT,map);
        westAnimal.move(MoveDirection.BACKWARD,map);
        Vector2d westAnimalPosition = westAnimal.getPosition();

        //then
        assertEquals(new Vector2d(0,0),northAnimalPosition);
        assertEquals(new Vector2d(0,0),eastAnimalPosition);
        assertEquals(new Vector2d(4,4),southAnimalPosition);
        assertEquals(new Vector2d(4,4),westAnimalPosition);
    }
}