package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SimulationTest {
    @Test
    void animalsFacingNorthOnCreation() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);

        var animalsAmount = simulation.getAnimalsAmount();

        for (int i = 0; i < animalsAmount; ++i) {
            Assertions.assertEquals(MapDirection.NORTH, simulation.getAnimalFacingDirection(i));
        }
    }

    @Test
    void animalRotateLeft() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2));

        directions.add(MoveDirection.LEFT);

        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(0));
    }

    @Test
    void animalRotateRight() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2));

        directions.add(MoveDirection.RIGHT);

        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(MapDirection.EAST, simulation.getAnimalFacingDirection(0));
    }

    @Test
    void animalsCreatedOnCoordinates() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));


        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);

        for (int i = 0; i < 2; ++i) {
            Assertions.assertEquals(positions.get(i), simulation.getAnimalLocalisation(i));
        }
    }

    @Test
    void animalMovesForward() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        var finalPosition = new Vector2d(2, 3);
        directions.add(MoveDirection.FORWARD);
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));
    }

    @Test
    void animalMovesBackward() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        var finalPosition = new Vector2d(2, 1);
        directions.add(MoveDirection.BACKWARD);
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));
    }

    @Test
    void animalMovesAfterRotation() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        var finalPosition = new Vector2d(1, 2);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.FORWARD);
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));
    }

    @Test
    void animalWontGoOutOfTheMap() {

        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(4, 4));
        directions.add(MoveDirection.FORWARD);
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        //upper and right boundary
        var finalPosition = new Vector2d(4, 4);

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        directions = List.of(MoveDirection.RIGHT, MoveDirection.FORWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        directions = List.of(MoveDirection.LEFT, MoveDirection.BACKWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        directions = List.of(MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.BACKWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        //bottom and left boundary
        positions = List.of(new Vector2d(0, 0));
        finalPosition = new Vector2d(0, 0);
        directions = List.of(MoveDirection.BACKWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        directions = List.of(MoveDirection.RIGHT, MoveDirection.BACKWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        directions = List.of(MoveDirection.LEFT, MoveDirection.FORWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));

        directions = List.of(MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.FORWARD);
        map = new RectangularMap(5, 5, 0);
        simulation = new Simulation(positions, directions, map);
        simulation.run();

        Assertions.assertEquals(finalPosition, simulation.getAnimalLocalisation(0));
    }

    @Test
    void simulationRunsWithWalidDirections() {
        List<MoveDirection> directions = List.of(MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.BACKWARD);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();


        Assertions.assertEquals(new Vector2d(3, 3), simulation.getAnimalLocalisation(0));
        Assertions.assertEquals(new Vector2d(4, 4), simulation.getAnimalLocalisation(1));
        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(0));
        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(1));
    }

    @Test
    void simulationRunsWithInwalidDirections() {
        List<MoveDirection> directions = List.of(MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();


        Assertions.assertEquals(new Vector2d(3, 4), simulation.getAnimalLocalisation(0));
        Assertions.assertEquals(new Vector2d(4, 4), simulation.getAnimalLocalisation(1));
        Assertions.assertEquals(MapDirection.EAST, simulation.getAnimalFacingDirection(0));
        Assertions.assertEquals(MapDirection.EAST, simulation.getAnimalFacingDirection(1));
    }

    @Test
    void inputStringWithWalidMovesIsProperlySimulated() {
        String[] testArgs = {"f", "f", "l", "l", "b", "b"};
        List<MoveDirection> directions = OptionsParser.parseOptions(testArgs);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
        WorldMap map = new RectangularMap(5, 5, 0);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();


        Assertions.assertEquals(new Vector2d(3, 3), simulation.getAnimalLocalisation(0));
        Assertions.assertEquals(new Vector2d(4, 4), simulation.getAnimalLocalisation(1));
        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(0));
        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(1));
    }

    //This test becomes hard to use with new specification, will be removed after pr
//    @Test
//    void inputStringWithInwalidMovesIsProperlySimulated() {
//        String[] testArgs = {"f", "x", "example", "f", "rubbish", "l", "lbr", "l", "b", "b"};
//        List<MoveDirection> directions = OptionsParser.parseOptions(testArgs);
//        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
//        WorldMap map = new RectangularMap(5, 5);
//        Simulation simulation = new Simulation(positions, directions, map);
//        simulation.run();
//
//
//        Assertions.assertEquals(new Vector2d(3, 3), simulation.getAnimalLocalisation(0));
//        Assertions.assertEquals(new Vector2d(4, 4), simulation.getAnimalLocalisation(1));
//        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(0));
//        Assertions.assertEquals(MapDirection.WEST, simulation.getAnimalFacingDirection(1));
//    }

}
