package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        File file = new File(String.format("map_%d.log", worldMap.getId()));
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println("Move information:");
            writer.println(message);

            writer.println("\nCurrent map state:\n");
            writer.println(worldMap.toString());

            writer.println("-----------------");
        } catch (Exception e) {
            System.out.println(String.format("Failed to save state to file due to error: %s", e.getMessage()));
        }
    }
}
