package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    public void simulationInit(){
        //given
        List<Vector2d> startingPositions = List.of(new Vector2d(0,0),new Vector2d(2,3));
        List<MoveDirection> moves = List.of();
        WorldMap map = new RectangularMap(5,5);
        //when
        Simulation simulation = new Simulation(startingPositions,moves,map);
        //then
        assertEquals(2,simulation.getAnimalsList().size());
        assertEquals(new Vector2d(0,0),simulation.getAnimalsList().get(0).getPosition());
        assertEquals(new Vector2d(2,3),simulation.getAnimalsList().get(1).getPosition());
    }

    @Test
    public void simulationSingleAnimal(){
        //given
        List<Vector2d> startingPositions = List.of(new Vector2d(2,2));
        List<MoveDirection> moves = List.of(MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.BACKWARD);
        WorldMap map = new RectangularMap(5,5);
        //when
        Simulation simulation = new Simulation(startingPositions,moves,map);
        simulation.run();
        //then
        assertEquals(new Vector2d(1,3),simulation.getAnimalsList().getFirst().getPosition());
    }

    @Test
    public void simulationMultiAnimal(){
        //given
        List<Vector2d> startingPositions = List.of(new Vector2d(2,2),new Vector2d(3,4));
        List<MoveDirection> moves = List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD);
        WorldMap map = new RectangularMap(5,5);
        //when
        Simulation simulation = new Simulation(startingPositions,moves,map);
        simulation.run();
        //then
        assertEquals(new Vector2d(2,2),simulation.getAnimalsList().get(0).getPosition());
        assertEquals(new Vector2d(3,4),simulation.getAnimalsList().get(1).getPosition());
    }
}