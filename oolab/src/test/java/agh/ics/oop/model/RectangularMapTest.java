package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RectangularMapTest {

    @Test
    void animalIsPlacedOnValidCoordinates() {
        var testMap = new RectangularMap(4, 5, 0);
        var properPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(properPosition);

        try {
            testMap.place(testAnimal);
            Assertions.assertEquals(testAnimal, testMap.objectAt(properPosition).get());
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }

    }

    @Test
    void animalIsNotPlacedOutsideMap() {
        var testMap = new RectangularMap(4, 5, 0);
        var properPosition = new Vector2d(10, 10);
        var testAnimal = new Animal(properPosition);

        Assertions.assertThrows(IncorrectPositionException.class, () -> {
            testMap.place(testAnimal);
        });

    }

    @Test
    void animalIsNotPlacedOnOtherAnimal() {
        var testMap = new RectangularMap(4, 5, 0);
        var properPosition = new Vector2d(1, 1);
        var exampleAnimal = new Animal(properPosition);
        var testAnimal = new Animal(properPosition);

        try {
            testMap.place(exampleAnimal);
            Assertions.assertThrows(IncorrectPositionException.class, () -> {
                testMap.place(testAnimal);
            });
        } catch (Exception ex) {
            Assertions.fail("Exception was thrown" + ex.getMessage());
        }
    }

    @Test
    void animalMovesIfPositionValid() {
        var testMap = new RectangularMap(4, 5, 0);
        var testPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(testPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
        testMap.move(testAnimal, MoveDirection.FORWARD);

        var expectedPosition = new Vector2d(1, 2);

        Assertions.assertFalse(testMap.isOccupied(testPosition));
        Assertions.assertEquals(testAnimal, testMap.objectAt(expectedPosition).get());
    }

    @Test
    void animalWontMoveIfPositionInvalid() {
        var testMap = new RectangularMap(4, 5, 0);
        var testPosition = new Vector2d(0, 0);
        var testAnimal = new Animal(testPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
        testMap.move(testAnimal, MoveDirection.BACKWARD);

        Assertions.assertTrue(testMap.isOccupied(testPosition));
        Assertions.assertEquals(testAnimal, testMap.objectAt(testPosition).get());
    }

    @Test
    void occupiedPlaceIsOccupied() {
        var testMap = new RectangularMap(4, 5, 0);
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
    void unoccupiedPlaceIsNotOccupied() {
        var testMap = new RectangularMap(4, 5, 0);
        var occupiedPosition = new Vector2d(1, 1);
        var unoccupiedPosition = new Vector2d(2, 2);
        var testAnimal = new Animal(occupiedPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }

        Assertions.assertFalse(testMap.isOccupied(unoccupiedPosition));
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
        var testMap = new RectangularMap(4, 5, 0);
        var testPosition = new Vector2d(1, 1);


        Assertions.assertTrue(testMap.canMoveTo(testPosition));
    }

    @Test
    void cantMoveToPositionOutsideMap() {
        var testMap = new RectangularMap(4, 5, 0);
        var testPosition = new Vector2d(10, 10);


        Assertions.assertFalse(testMap.canMoveTo(testPosition));
    }

    @Test
    void cantMoveToOccupiedPosition() {
        var testMap = new RectangularMap(4, 5, 0);
        var occupiedPosition = new Vector2d(1, 1);
        var testAnimal = new Animal(occupiedPosition);

        try {
            testMap.place(testAnimal);
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }

        Assertions.assertFalse(testMap.canMoveTo(occupiedPosition));
    }

    @Test
    void rectangularMapReturnsProperAmountOfElements() {
        RectangularMap testMap = new RectangularMap(10, 10, 0);
        Animal animal1 = new Animal(new Vector2d(0, 0));
        Animal animal2 = new Animal(new Vector2d(1, 1));
        try {
            testMap.place(animal1);
            testMap.place(animal2);
            Assertions.assertEquals(2, testMap.getElements().size());
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
    }

    @Test
    void animalsAreSortedByX() {
        RectangularMap testMap = new RectangularMap(10, 10, 0);
        Animal animal1 = new Animal(new Vector2d(0, 1));
        Animal animal2 = new Animal(new Vector2d(1, 1));
        List<Animal> expectedAnimals = List.of(animal1, animal2);
        try {
            testMap.place(animal1);
            testMap.place(animal2);
            Assertions.assertEquals(expectedAnimals, testMap.getOrderedAnimals());
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
    }

    @Test
    void animalsAreSortedByY() {
        RectangularMap testMap = new RectangularMap(10, 10, 0);
        Animal animal1 = new Animal(new Vector2d(1, 0));
        Animal animal2 = new Animal(new Vector2d(1, 1));
        List<Animal> expectedAnimals = List.of(animal1, animal2);
        try {
            testMap.place(animal1);
            testMap.place(animal2);
            Assertions.assertEquals(expectedAnimals, testMap.getOrderedAnimals());
        } catch (IncorrectPositionException e) {
            Assertions.fail("Exception was thrown" + e.getMessage());
        }
    }
}
