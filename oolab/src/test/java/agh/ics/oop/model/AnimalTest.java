package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnimalTest {
    AbstractWorldMap validator = new DarwinSimulationMap(4, 4, 0);

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
        Assertions.assertEquals(finalPosition, testAnimal.getPosition());
    }

    @Test
    void animalMovesBackward() {
        var testAnimal = new Animal();
        var finalPosition = new Vector2d(2, 1);
        testAnimal.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getPosition());
    }

    @Test
    void animalMovesAfterRotation() {
        var testAnimal = new Animal();
        var finalPosition = new Vector2d(1, 3);
        testAnimal.move(MoveDirection.LEFT, validator);
        testAnimal.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(finalPosition, testAnimal.getPosition());
    }

    @Test
    void animalTeleportsOnXAxis() {
        var startingPositionX = new Vector2d(3, 2);
        var animalX = new Animal(startingPositionX);

        animalX.move(MoveDirection.RIGHT, validator);
        animalX.move(MoveDirection.RIGHT, validator);
        Assertions.assertEquals(new Vector2d(3, 2), animalX.getPosition());

        animalX.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(new Vector2d(0, 2), animalX.getPosition());

        animalX.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(new Vector2d(3, 2), animalX.getPosition());
    }

    @Test
    void animalTeleportsAndMoves(){
        var startingPositionX = new Vector2d(3, 1);
        var animalX = new Animal(startingPositionX);

        animalX.move(MoveDirection.RIGHT, validator);
        Assertions.assertEquals(new Vector2d(3, 1), animalX.getPosition());

        animalX.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(new Vector2d(0, 2), animalX.getPosition());

        animalX.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(new Vector2d(3, 1), animalX.getPosition());
    }

    @Test
    void animalRotatesOnUpperYAxis() {
        var startingPositionY = new Vector2d(2, 3);
        var animalY = new Animal(startingPositionY);
        animalY.move(MoveDirection.FORWARD, validator);
        Assertions.assertEquals(new Vector2d(2, 3), animalY.getPosition());
        Assertions.assertEquals(MapDirection.SOUTH, animalY.getFacingDirection());
    }

    @Test
    void animalRotatesOnLowerYAxis() {
        var bottomPosition = new Vector2d(2, 0);
        var animalBottom = new Animal(bottomPosition);
        animalBottom.move(MoveDirection.BACKWARD, validator);
        Assertions.assertEquals(new Vector2d(2, 0), animalBottom.getPosition());
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
        for (int i = 0; i < Animal.GENOME_LENGTH; i++) {
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
        Assertions.assertEquals(Animal.GENOME_LENGTH, child.getGenome().size());
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
        DarwinSimulationMap map = new DarwinSimulationMap(10,10,0);
        Vector2d currentPosition = testAnimal.getPosition();
        //when
        for (int gene : genome) {
            currentPosition = currentPosition.add(MapDirection.values()[gene].toUnitVector());
            testAnimal.moveByGenome(map);
            Assertions.assertEquals(currentPosition, testAnimal.getPosition(),
                    "Animal moved incorrectly for gene: " + gene);
        }
    }

    @Test
    void rotateAnimalByGenome(){
        var validator = new DarwinSimulationMap(4, 4, 0);
        var genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        var testAnimal = new Animal(new Vector2d(2, 2), genome);

        MapDirection initialDirection = testAnimal.getFacingDirection();

        for (int gene : genome) {
            testAnimal.rotateAnimal();
            MapDirection expectedDirection = initialDirection;

            for (int i = 0; i < gene; i++) {
                expectedDirection = expectedDirection.next();
            }

            Assertions.assertEquals(expectedDirection, testAnimal.getFacingDirection());

            initialDirection = testAnimal.getFacingDirection();
        }
    }

    @Test
    void moveForwardByGenome(){
        var validator = new DarwinSimulationMap(10, 10, 0);
        var genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        var testAnimal = new Animal(new Vector2d(4, 4), genome);

        Vector2d initialPosition = testAnimal.getPosition();

        for (int gene : genome) {
            Vector2d expectedPosition = initialPosition.add(MapDirection.values()[gene].toUnitVector());
            testAnimal.moveForward(validator);
            Assertions.assertEquals(expectedPosition, testAnimal.getPosition());

            initialPosition = testAnimal.getPosition();
        }
    }


}
