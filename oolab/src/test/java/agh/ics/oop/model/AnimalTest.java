package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        Assertions.assertEquals(MapDirection.NORTH_WEST, testAnimal.getFacingDirection());
    }

    @Test
    void animalRotatesRight() {
        var testAnimal = new Animal();
        testAnimal.move(MoveDirection.RIGHT, validator);
        Assertions.assertEquals(MapDirection.NORTH_EAST, testAnimal.getFacingDirection());
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
        var finalPosition = new Vector2d(1, 3);
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

        //new position - (0, -1)
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        //new
        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());

        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.RIGHT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getLocalizationOnMap());
    }

    @Test
    void childGenomeCorrectRange(){
        //given
        List<Integer> genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        Animal parent1 = new Animal(new Vector2d(2, 2), genome);
        Animal parent2 = new Animal(new Vector2d(2, 2), genome);
        //when
        Animal child = parent1.reproduce(parent2);
        //then
        List<Integer> childGenome = child.getGenome();
        for (Integer integer : childGenome) {
            Assertions.assertTrue(integer >= 0 && integer <= 7);
        }
        Assertions.assertEquals(genome.size(), childGenome.size());
    }

    @Test
    void childSlightlyMutatedGenome(){
        //given
        List<Integer> genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        Animal parent1 = new Animal(new Vector2d(2, 2), genome);
        Animal parent2 = new Animal(new Vector2d(2, 2), genome);
        //when
        Animal child = parent1.reproduce(parent2);
        //then
        List<Integer> childGenome = child.getGenome();
        for (int i = 0; i < Animal.GENOM_LENGTH; i++) {
            Integer gene = childGenome.get(i);
            Assertions.assertTrue(gene == genome.get(i) || gene == (genome.get(i) + 1) % 8 || gene == (genome.get(i) +7) % 8);
        }
    }

    @Test
    void reproductionEnergyAndGenomeSize(){
        //given
        Animal parent1 = new Animal(new Vector2d(0, 0), List.of(0, 1, 2, 3, 4, 5, 6, 7));
        Animal parent2 = new Animal(new Vector2d(0, 0), List.of(7, 6, 5, 4, 3, 2, 1, 0));

        //when
        Animal child = parent1.reproduce(parent2);

        //then
        Assertions.assertEquals(Animal.GENOM_LENGTH, child.getGenome().size());
        Assertions.assertEquals(Animal.NEWBORNS_ENERGY,child.getEnergy());
        Assertions.assertEquals(50, parent1.getEnergy());
        Assertions.assertEquals(50, parent2.getEnergy());
    }

    @Test
    void addAndSubstractEnergy(){
        //given
        Animal testAnimal1 = new Animal();
        Animal testAnimal2 = new Animal();
        //when
        testAnimal1.addEnergy(10);
        testAnimal2.subtractEnergy(10);
        //then
        Assertions.assertEquals(Animal.NEWBORNS_ENERGY + 10, testAnimal1.getEnergy());
        Assertions.assertEquals(Animal.NEWBORNS_ENERGY - 10, testAnimal2.getEnergy());
    }

    @Test
    void canAnimalReproduce(){
        //given
        Animal testAnimal1 = new Animal();
        Animal testAnimal2 = new Animal();

        //when
        testAnimal1.addEnergy(Animal.ENERGY_TO_REPRODUCE);
        testAnimal2.subtractEnergy(Animal.ENERGY_TO_REPRODUCE - 1);

        //then
        Assertions.assertTrue(testAnimal1.canReproduce());
        Assertions.assertFalse(testAnimal2.canReproduce());
    }

    @Test
    void animalMovesByGenome(){
        //given
        List<Integer> genome = List.of(0, 1, 2, 3, 4, 5, 6, 7,0);
        Animal testAnimal = new Animal(new Vector2d(2, 2), genome);
        GrassField map = new GrassField(10,0);
        Vector2d currentPosition = testAnimal.getLocalizationOnMap();
        //when
        for (int gene : genome) {
            currentPosition = currentPosition.add(MapDirection.values()[gene].toUnitVector());
            testAnimal.moveByGenome(map);
            Assertions.assertEquals(currentPosition, testAnimal.getLocalizationOnMap(),
                    "Animal moved incorrectly for gene: " + gene);
        }
    }


}
