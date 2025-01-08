package agh.ics.oop;

import javafx.application.Application;

public class WorldGui {
    public static void main(String[] args) {
        try {
            Application.launch(SimulationApp.class, args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
