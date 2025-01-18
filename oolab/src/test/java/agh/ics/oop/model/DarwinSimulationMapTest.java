package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DarwinSimulationMapTest {

    @Test
    void animalIsPlacedOnValidCoordinates() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var testPosition = new Vector2d(1, 1);

        Assertions.assertFalse(testMap.isOccupied(testPosition));
    }


    @Test
    void objectThatIsOnPositionIsReturned() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var testPosition = new Vector2d(1, 1);


        Assertions.assertTrue(testMap.canMoveTo(testPosition));
    }

    @Test
    void cantMoveToOccupiedPosition() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
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
    void doesGrassGrow() {
        var testMap = new DarwinSimulationMap(10, 10, 0);

        Assertions.assertTrue(testMap.getGrassCount() > 0);
    }


}