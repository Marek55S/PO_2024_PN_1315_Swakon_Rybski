package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.List;

public class WorldGUI {
    public static void main(String[] args) {
        /*List<MoveDirection> directions = OptionsParser.parseDirection(args);
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 1));
        AbstractWorldMap map1 = new GrassField(10);
        map1.addObserver(new ConsoleMapDisplay());*/
        Application.launch(SimulationApp.class);
    }
}
