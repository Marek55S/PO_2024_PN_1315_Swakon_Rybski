package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int updatesCounter = 0;

    @Override
    public void mapChanged(WorldMap map, String message) {
        synchronized (System.out) {
            updatesCounter++;
            System.out.printf("on map: %s, updates so far: %d%n", map.getID(), updatesCounter);
            System.out.println(message);
            System.out.println(map.toString());
    }}
}
