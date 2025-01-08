package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassFieldTest {
    @Test
    void grassFieldIsGeneratedWithProperAmountOfGrass() {
        int grassCount = 10;
        var testGrassField = new GrassField(grassCount, 0);

        Assertions.assertEquals(grassCount, testGrassField.getElements().size());
    }

    @Test
    void animalIsPlacedOnValidCoordinates() {
        var testMap = new GrassField(10, 0);
        var properPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(properPosition);

        try {
            testMap.place(testAnimal);
            Assertions.assertEquals(testAnimal, testMap.objectAt(properPosition).get());
        } catch (IncorrectPositionException ex) {
            Assertions.fail("Exception was thrown");
        }

    }


    @Test
    void animalIsNotPlacedOnOtherAnimal() {
        var testMap = new GrassField(10, 0);
        var properPosition = new Vector2d(1, 1);
        var exampleAnimal = new Animal(properPosition);
        var testAnimal = new Animal(properPosition);

        try {
            testMap.place(exampleAnimal);
            Assertions.assertThrows(IncorrectPositionException.class, () -> {
                testMap.place(testAnimal);
            });
        } catch (IncorrectPositionException ex) {
            Assertions.fail("Exception was thrown" + ex.getMessage());
        }
    }

    @Test
    void animalMovesIfPositionValid() {
        var testMap = new GrassField(10, 0);
        var testPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(testPosition);

        try {
            testMap.place(testAnimal);
            testMap.move(testAnimal, MoveDirection.FORWARD);

            var expectedPosition = new Vector2d(1, 2);

            Assertions.assertEquals(testAnimal, testMap.objectAt(expectedPosition).get());
        } catch (IncorrectPositionException ex) {
            Assertions.fail("Exception was thrown" + ex.getMessage());
        }
    }

    @Test
    void animalWontMoveIfPositionInvalid() {
        var testMap = new GrassField(10, 0);
        var testPosition = new Vector2d(0, 0);
        var futurePosition = new Vector2d(0, -1);
        var testAnimal = new Animal(testPosition);
        var blockingAnimal = new Animal(futurePosition);

        try {
            testMap.place(testAnimal);
            testMap.place(blockingAnimal);
            testMap.move(testAnimal, MoveDirection.BACKWARD);
            Assertions.assertEquals(testAnimal, testMap.objectAt(testPosition).get());
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
    }

    @Test
    void occupiedPlaceIsOccupied() {
        var testMap = new GrassField(10, 0);
        var testPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(testPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }

        Assertions.assertTrue(testMap.isOccupied(testPosition));
    }

    @Test
    void unoccupiedPlaceIsUnoccupied() {
        var testMap = new GrassField(10, 0);
        var testPosition = new Vector2d(1000, 1000);

        Assertions.assertFalse(testMap.isOccupied(testPosition));
    }


    @Test
    void objectThatIsOnPositionIsReturned() {
        var testMap = new RectangularMap(4, 5, 0);
        var occupiedPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(occupiedPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }

        Assertions.assertEquals(testAnimal, testMap.objectAt(occupiedPosition).get());
    }

    @Test
    void canMoveToValidPosition() {
        var testMap = new GrassField(10, 0);
        var testPosition = new Vector2d(1, 1);


        Assertions.assertTrue(testMap.canMoveTo(testPosition));
    }

    @Test
    void cantMoveToOccupiedPosition() {
        var testMap = new GrassField(10, 0);
        var occupiedPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(occupiedPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }

        Assertions.assertFalse(testMap.canMoveTo(occupiedPosition));
    }

    @Test
    void grassFieldReturnsProperAmountOfElements() {
        GrassField defaultMap = new GrassField(10, 0);
        Animal animal1 = new Animal(new Vector2d(0, 0));
        Animal animal2 = new Animal(new Vector2d(1, 1));
        try {
            defaultMap.place(animal1);
            defaultMap.place(animal2);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
        Assertions.assertEquals(12, defaultMap.getElements().size());
    }
}
