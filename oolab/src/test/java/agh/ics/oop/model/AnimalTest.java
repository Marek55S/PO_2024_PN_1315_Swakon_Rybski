package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnimalTest {
    AbstractWorldMap validator = new DarwinSimulationMap(4, 4, 0);

    @Test
    void animalIsProperlyOrientedWhenCreated() {

        var testAnimal = new Animal(new Vector2d(2, 2), List.of(0, 1, 2, 3, 4, 5, 6, 7),null,null,MapDirection.NORTH);

        Assertions.assertEquals(MapDirection.NORTH, testAnimal.getFacingDirection());
    }


    @Test
    void animalTeleportsOnXAxis() {
        var startingPositionX = new Vector2d(3, 2);
        var genome = List.of(2,4,0,0,0,0,0,0);
        var animalX = new Animal(startingPositionX,genome,null,null,MapDirection.NORTH);

        animalX.moveByGenome(validator);
        Assertions.assertEquals(new Vector2d(0, 2), animalX.getPosition());
        animalX.moveByGenome(validator);
        Assertions.assertEquals(new Vector2d(3, 2), animalX.getPosition());
    }

    @Test
    void animalTeleportsAndMoves(){
        var startingPositionX = new Vector2d(3, 1);
        var genome = List.of(1,4,0,0,0,0,0,0);
        var animalX = new Animal(startingPositionX,genome,null,null,MapDirection.NORTH);

        animalX.moveByGenome( validator);
        Assertions.assertEquals(new Vector2d(0, 2), animalX.getPosition());

        animalX.moveByGenome( validator);
        Assertions.assertEquals(new Vector2d(3, 1), animalX.getPosition());
    }

    @Test
    void animalRotatesOnUpperYAxis() {
        var startingPositionY = new Vector2d(2, 3);
        var genome = List.of(0,0,0,0,0,0,0,0);
        var animalY = new Animal(startingPositionY,genome,null,null,MapDirection.NORTH);
        animalY.moveByGenome( validator);
        Assertions.assertEquals(new Vector2d(2, 3), animalY.getPosition());
        Assertions.assertEquals(MapDirection.SOUTH, animalY.getFacingDirection());
    }

    @Test
    void animalRotatesOnLowerYAxis() {
        var bottomPosition = new Vector2d(2, 0);
        var genome = List.of(4,0,0,0,0,0,0,0);
        var animalBottom = new Animal(bottomPosition,genome,null,null,MapDirection.NORTH);
        animalBottom.moveByGenome(validator);
        Assertions.assertEquals(new Vector2d(2, 0), animalBottom.getPosition());
        Assertions.assertEquals(MapDirection.NORTH, animalBottom.getFacingDirection());
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
    void reproductionEnergyAndGenomeSize(){
        //given
        Animal parent1 = new Animal(new Vector2d(0, 0), List.of(0, 1, 2, 3, 4, 5, 6, 7));
        Animal parent2 = new Animal(new Vector2d(0, 0), List.of(7, 6, 5, 4, 3, 2, 1, 0));

        //when
        Animal child = parent1.reproduce(parent2);

        //then
        Assertions.assertEquals(parent1.genomeLength, child.getGenome().size());
        Assertions.assertEquals(parent1.newbornsEnergy,child.getEnergy());
        Assertions.assertEquals(50, parent1.getEnergy());
        Assertions.assertEquals(50, parent2.getEnergy());
    }

    @Test
    void addAndSubstractEnergy(){
        //given
        var genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        var position = new Vector2d(2, 2);
        Animal testAnimal1 = new Animal(position,genome);
        Animal testAnimal2 = new Animal(position,genome);
        //when
        testAnimal1.addEnergy(10);
        testAnimal2.subtractEnergy(10);
        //then
        Assertions.assertEquals(testAnimal1.newbornsEnergy + 10, testAnimal1.getEnergy());
        Assertions.assertEquals(testAnimal1.newbornsEnergy - 10, testAnimal2.getEnergy());
    }

    @Test
    void canAnimalReproduce(){
        //given
        var genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        var position = new Vector2d(2, 2);
        Animal testAnimal1 = new Animal(position,genome);
        Animal testAnimal2 = new Animal(position,genome);

        //when
        testAnimal1.addEnergy(testAnimal1.energyToReproduce);
        testAnimal2.subtractEnergy(testAnimal1.energyToReproduce - 1);

        //then
        Assertions.assertTrue(testAnimal1.canReproduce());
        Assertions.assertFalse(testAnimal2.canReproduce());
    }

    @Test
    void animalMovesByGenome(){
        // given
        List<Integer> genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        Animal testAnimal = new Animal(new Vector2d(2, 2), genome, null, null, MapDirection.NORTH);
        DarwinSimulationMap map = new DarwinSimulationMap(10, 10, 0);
        Vector2d currentPosition = testAnimal.getPosition();
        int currentDirectionIndex = 0;

        for (int gene : genome) {
            currentDirectionIndex = (currentDirectionIndex + gene) % 8;

            MapDirection currentDirection = MapDirection.values()[currentDirectionIndex];

            Vector2d expectedPosition = currentPosition.add(currentDirection.toUnitVector());

            testAnimal.moveByGenome(map);

            currentPosition = testAnimal.getPosition();

            Assertions.assertEquals(expectedPosition, currentPosition,
                    "Animal moved incorrectly for gene: " + gene);
        }
    }

    @Test
    void rotateAnimalByGenome(){
        var validator = new DarwinSimulationMap(10, 10, 0);
        var genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        var testAnimal = new Animal(new Vector2d(5, 5), genome);

        MapDirection initialDirection = testAnimal.getFacingDirection();

        for (int gene : genome) {
            testAnimal.moveByGenome(validator);
            MapDirection expectedDirection = initialDirection;

            for (int i = 0; i < gene; i++) {
                expectedDirection = expectedDirection.next();
            }

            Assertions.assertEquals(expectedDirection, testAnimal.getFacingDirection());

            initialDirection = testAnimal.getFacingDirection();
        }
    }


    @Test
    void moveByGenomeTest() {
        var validator = new DarwinSimulationMap(5, 5, 0);
        var genome = List.of(1, 2, 3, 4, 5, 6, 7, 0);
        var testAnimal = new Animal(new Vector2d(2, 2), genome);

        Vector2d initialPosition = testAnimal.getPosition();
        MapDirection initialDirection = testAnimal.getFacingDirection();

        for (int gene : genome) {
            MapDirection expectedDirection = initialDirection;
            for (int i = 0; i < gene; i++) {
                expectedDirection = expectedDirection.next();
            }

            Vector2d expectedPosition = initialPosition;
            Vector2d moveVector = expectedDirection.toUnitVector();
            Vector2d potentialNewPosition = expectedPosition.add(moveVector);
            if (validator.canMoveTo(potentialNewPosition)) {
                expectedPosition = potentialNewPosition;
            }

            testAnimal.moveByGenome(validator);

            Assertions.assertEquals(expectedDirection, testAnimal.getFacingDirection());
            Assertions.assertEquals(expectedPosition, testAnimal.getPosition());

            initialDirection = testAnimal.getFacingDirection();
            initialPosition = testAnimal.getPosition();
        }
    }

    @Test
    void childrenAndDescendantsCount(){
        var Animal1 = new Animal(new Vector2d(2, 2), List.of(0, 1, 2, 3, 4, 5, 6, 7));
        var Animal2 = new Animal(new Vector2d(2, 2), List.of(0, 1, 2, 3, 4, 5, 6, 7));

        var child1 = Animal1.reproduce(Animal2);
        var child2 = Animal1.reproduce(Animal2);
        var child3 = Animal1.reproduce(Animal2);


        Assertions.assertEquals(3, Animal1.getChildrenCount());
        Assertions.assertEquals(0, child1.getChildrenCount());
        Assertions.assertEquals(0, child1.getTotalDescendantsCount());
        Assertions.assertEquals(3, Animal1.getTotalDescendantsCount());

        var grandchild1 = child1.reproduce(child2);
        Assertions.assertEquals(0, grandchild1.getChildrenCount());
        Assertions.assertEquals(0, grandchild1.getTotalDescendantsCount());
        Assertions.assertEquals(1, child1.getChildrenCount());
        Assertions.assertEquals(1, child1.getTotalDescendantsCount());
        Assertions.assertEquals(3, Animal1.getChildrenCount());
        Assertions.assertEquals(4, Animal1.getTotalDescendantsCount());
        for(Animal child : Animal1.getChildren()){
            System.out.println(child);
        }
    }

}
