package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.utils.MapTypes;
import agh.ics.oop.utils.SimulationOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Simulation implements Runnable {
    private final DarwinSimulationMap map;
    private boolean running = false;
    private boolean stopped = false;
    private SimulationOptions options;


    public Simulation(List<Vector2d> startingPositions, DarwinSimulationMap map) {
        this.map = map;
        for (var animalPosition : startingPositions) {
            try {
                map.place(animalPosition, options);
            } catch (IncorrectPositionException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public Simulation(List<Vector2d> startingPositions, SimulationOptions options, int simulationId) {
        this.options = options;
        if(options.mapType() == MapTypes.WATER_MAP){this.map = new DarwinSimulationMapWithWater(options,simulationId);}
        else{this.map = new DarwinSimulationMap(options,simulationId);
        }
        for (var animalPosition : startingPositions) {
            try {
                map.place(animalPosition, options);
            } catch (IncorrectPositionException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public Simulation(SimulationOptions options, int simulationId) {
        this.options = options;
        var startingPositions = generateStartingPositions(options.initialAnimalsCount());
        if(options.mapType() == MapTypes.WATER_MAP){this.map = new DarwinSimulationMapWithWater(options,simulationId);}
        else{this.map = new DarwinSimulationMap(options,simulationId);
        }
        for (var animalPosition : startingPositions) {
            try {
                map.place(animalPosition, options);
            } catch (IncorrectPositionException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private List<Vector2d> generateStartingPositions(int startingAnimalsCount) {
        List<Vector2d> startingPositions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < startingAnimalsCount; i++) {
            Vector2d position = new Vector2d(random.nextInt(options.simulationWidth()), random.nextInt(options.simulationHeigth()));
            while (startingPositions.contains(position)) {
                position = new Vector2d(random.nextInt(options.simulationWidth()), random.nextInt(options.simulationHeigth()));
            }
            startingPositions.add(position);
        }
        return startingPositions;
    }


    public void toggle() {
        running = !running;
    }
    public void stop() {stopped = true;}
// grass energy and day energy consumption will be set in by user
    public void run() {
        int dayCounter = 0;
        while(!map.getOrderedAnimals().isEmpty() && !stopped) {
            if(running){
            dayCounter++;
            map.nextDay();
            System.out.println("Day " + dayCounter + " has ended");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        }

    }
}

