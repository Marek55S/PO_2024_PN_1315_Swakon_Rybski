package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parseDirection(String[] stringDirections) {
        List<MoveDirection> directions = new ArrayList<>();
        for (String direction : stringDirections) {
            switch (direction) {
                case "f" -> directions.add(MoveDirection.FORWARD);
                case "b" -> directions.add(MoveDirection.BACKWARD);
                case "l" -> directions.add(MoveDirection.LEFT);
                case "r" -> directions.add(MoveDirection.RIGHT);
                default -> {throw new IllegalArgumentException("Illegal move specification: " + direction);}
            }
            }
        return directions;
    }
}
