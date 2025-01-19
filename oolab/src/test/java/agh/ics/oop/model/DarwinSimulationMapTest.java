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

        try {
            testMap.place(properPosition);
            assertSame(testMap.objectAt(properPosition).get().getClass(), Animal.class);
        } catch (IncorrectPositionException ex) {
            Assertions.fail("Exception was thrown");
        }

    }


    @Test
    void animalIsNotPlacedOutsideMap() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var properPosition = new Vector2d(1, 1);
        var improperPosition = new Vector2d(-1, 1);

        try {
            testMap.place(properPosition);
            Assertions.assertThrows(IncorrectPositionException.class, () -> {
                testMap.place(improperPosition);
            });
        } catch (IncorrectPositionException ex) {
            Assertions.fail("Exception was thrown" + ex.getMessage());
        }
    }

    @Test
    void animalMovesIfPositionValid() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var testPosition = new Vector2d(1, 1);

        try {
            testMap.place(testPosition);
            var testAnimal = testMap.getOrderedAnimals().getFirst();
            testMap.move(testAnimal, MoveDirection.FORWARD);
            var expectedPosition = new Vector2d(1, 2);
            Assertions.assertTrue(testMap.isOccupied(expectedPosition));
            Assertions.assertEquals(testAnimal, testMap.objectAt(expectedPosition).get());
        } catch (IncorrectPositionException ex) {
            Assertions.fail("Exception was thrown" + ex.getMessage());
        }
    }


    @Test
    void occupiedPlaceIsOccupied() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var testPosition = new Vector2d(1, 1);

        try {
            testMap.place(testPosition);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }

        Assertions.assertTrue(testMap.isOccupied(testPosition));
    }


    @Test
    void canMoveToValidPosition() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var testPosition = new Vector2d(1, 1);

        Assertions.assertTrue(testMap.canMoveTo(testPosition));
    }

    @Test
    void canMoveToOccupiedPosition() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var occupiedPosition = new Vector2d(1, 1);

        try {
            testMap.place(occupiedPosition);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }

        Assertions.assertTrue(testMap.canMoveTo(occupiedPosition));
    }

    @Test
    void removeDeadAnimalsTest() {
        var testMap = new DarwinSimulationMap(10, 10, 0);

        try {
            testMap.place(new Vector2d(1,1));

        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }
        var testAnimal = testMap.getOrderedAnimals().getFirst();
        testAnimal.subtractEnergy(100);
        Assertions.assertEquals(1, testMap.getOrderedAnimals().size());
        testMap.removeDeadAnimals();
        Assertions.assertEquals(0, testMap.getOrderedAnimals().size());
    }

    @Test
    void eatGrassTest() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var grassPosition = testMap.getElements().getFirst().getPosition();
        try {
            testMap.place(grassPosition);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }
        int allElementsNumber = testMap.getElements().size();
        testMap.eatGrass(10);
        Assertions.assertEquals(allElementsNumber - 1, testMap.getElements().size());
    }

    @Test
    void reproduceAnimalsTest() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var startPosition = new Vector2d(1, 1);
        try {
            testMap.place(startPosition);
            testMap.place(startPosition);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }
        int allElementsNumber = testMap.getElements().size();
        testMap.reproduceAnimals();
        Assertions.assertEquals(allElementsNumber + 1, testMap.getElements().size());
    }

    @Test
    void takeEnergyFromAnimalsTest() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var startPosition = (new Vector2d(1, 1));
        try {
            testMap.place(startPosition);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }
        var testAnimal = testMap.getOrderedAnimals().getFirst();
        testMap.takeEnergyFromAnimals(10);
        Assertions.assertEquals(90, testAnimal.getEnergy());
    }

    // something should be fixed
    @Test
    void moveAllAnimalsByGenomeTest() {
        var testMap = new DarwinSimulationMap(10, 10, 0);
        var startPosition = new Vector2d(1, 1);
        var startPosition2 = new Vector2d(2, 2);

        try {
            testMap.place(startPosition);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown " + e.getMessage());
        }
        var testAnimal = testMap.getOrderedAnimals().getFirst();
        testMap.moveAllAnimals();
        Assertions.assertNotEquals(startPosition, testMap.getOrderedAnimals().getFirst().getPosition());
    }


}