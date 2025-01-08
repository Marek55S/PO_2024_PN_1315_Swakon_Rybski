package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    MoveValidator validator = new RectangularMap(4, 4, 0);

    @Test
    void animalIsProperlyOrientedWhenCreated() {
        var testAnimal = new Animal();

        Assertions.assertEquals(MapDirection.NORTH, testAnimal.getFacingDirection());
    }

    @Test
    void animalRotatesLeft() {
        var testAnimal = new Animal();
        testAnimal.move(MoveDirection.LEFT, validator);
        Assertions.assertEquals(MapDirection.WEST, testAnimal.getFacingDirection());
    }

    @Test
    void animalRotatesRight() {
        var testAnimal = new Animal();
        testAnimal.move(MoveDirection.RIGHT, validator);
        Assertions.assertEquals(MapDirection.EAST, testAnimal.getFacingDirection());
    }

    @Test
    void animalMovesForward() {
        var testAnimal = new Animal();
        var finalPosition = new Vector2d(2, 3);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());
    }

    @Test
    void animalMovesBackward() {
        var testAnimal = new Animal();
        var finalPosition = new Vector2d(2, 1);
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());
    }

    @Test
    void animalMovesAfterRotation() {
        var testAnimal = new Animal();
        var finalPosition = new Vector2d(1, 2);
        testAnimal.move(MoveDirection.LEFT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());
    }

    @Test
    void animalWontGoOutOfTheMap() {

        //upper and right boundary
        var startingPosition = new Vector2d(4, 4);
        var testAnimal = new Animal(startingPosition);
        var finalPosition = new Vector2d(4, 4);

        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        startingPosition = new Vector2d(0, 0);
        testAnimal = new Animal(startingPosition);
        finalPosition = new Vector2d(0, 0);

        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());
    }


}
