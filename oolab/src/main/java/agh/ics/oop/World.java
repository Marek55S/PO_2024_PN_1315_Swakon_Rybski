package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.ConsoleMapDisplay;
import agh.ics.oop.model.util.FileMapDisplay;

import java.time.LocalDateTime;
import java.util.List;

public class World {
    public static void main(String[] args) {
        DarwinSimulationMap map = new DarwinSimulationMap(10, 10, 1);
        map.addObserver(new ConsoleMapDisplay());
        List<Vector2d> startingPositions = List.of(new Vector2d(1,1));
        Simulation simulation = new Simulation(startingPositions,map);
        simulation.run();
    }

}
