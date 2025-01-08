package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {

    private int updatesRecieved;

    public ConsoleMapDisplay() {
        updatesRecieved = 0;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        synchronized (System.out) {
            System.out.println(String.format("%s \n %s \n %s \n map id: %s", message, worldMap, updatesRecieved, worldMap.getId()));
        }
        synchronized (this) {
            updatesRecieved += 1;
        }
    }
}
