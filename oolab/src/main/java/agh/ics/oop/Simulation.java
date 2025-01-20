package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final DarwinSimulationMap map;

    public Simulation(List<Vector2d> startingPositions, DarwinSimulationMap map) {
        for (var animalPosition : startingPositions) {
            try {
                map.place(animalPosition);
            } catch (IncorrectPositionException ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.map = map;
    }


// grass energy and day energy consumption will be set in by user
    public void run() {
        int dayCounter = 0;
        while(!map.getOrderedAnimals().isEmpty()) {
            dayCounter++;
            map.removeDeadAnimals();
            map.moveAllAnimals();
            map.eatGrass(15);
            map.reproduceAnimals();
            map.growGrass();
            map.takeEnergyFromAnimals(5);
            System.out.println("Day " + dayCounter + " has ended");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }

    }
}

