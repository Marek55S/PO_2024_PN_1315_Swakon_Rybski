package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.ConsoleMapDisplay;
import agh.ics.oop.model.util.FileMapDisplay;

import java.time.LocalDateTime;
import java.util.List;

public class World {
    public static void main(String[] args) {
//        try {
//            List<MoveDirection> moves = OptionsParser.parseOptions("f b r l f f r r f f f f f f f f".split(" "));
//            List<Vector2d> positions = List.of(new Vector2d(1, 1));
//            var grassField = new GrassField(10, 0);
//            var rectangularMap = new RectangularMap(10, 10, 1);
//            var observer = new ConsoleMapDisplay();
//            grassField.addObserver(observer);
//            grassField.addObserver((worldMap, message) -> {
//                System.out.println(String.format("%s %s", LocalDateTime.now(), message));
//            });
//            FileMapDisplay fileMapDisplay = new FileMapDisplay();
//            grassField.addObserver(fileMapDisplay);
//            rectangularMap.addObserver(observer);
//            var simulation = new Simulation(positions, grassField);
//            var simulation2 = new Simulation(positions, rectangularMap);
//            var simulationEngine = new SimulationEngine(List.of(simulation, simulation2));
//            simulationEngine.runAsync();
//            try {
//                simulationEngine.awaitSimulationEnd();
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
//            System.out.println("System zakończył działanie");
//        } catch (IllegalArgumentException ex) {
//            System.out.println("Error: " + ex.getMessage());
//            return;
//        }
    }

    DarwinSimulationMap map = new DarwinSimulationMap(10, 10, 1);
    List<Vector2d> startingPositions = List.of(new Vector2d(1, 1), new Vector2d(3, 1));


}
