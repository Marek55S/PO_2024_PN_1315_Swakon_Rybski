package agh.ics.oop.model;

import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection facingDirection;
    private Vector2d localizationOnMap;

    public Animal() {
        this(new Vector2d(2, 2));
    }

    public Animal(Vector2d localizationOnMap) {
        this.localizationOnMap = localizationOnMap;
        facingDirection = MapDirection.NORTH;
    }

    public Vector2d getLocalizationOnMap() {
        return localizationOnMap;
    }

    public MapDirection getFacingDirection() {
        return facingDirection;
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case LEFT -> facingDirection = facingDirection.previous();
            case RIGHT -> facingDirection = facingDirection.next();
            case FORWARD -> {
                var newLoc = localizationOnMap.add(this.facingDirection.toUnitVector());
                if (validator.canMoveTo(newLoc))
                    localizationOnMap = newLoc;
            }
            case BACKWARD -> {
                var newLoc = localizationOnMap.subtract(this.facingDirection.toUnitVector());
                if (validator.canMoveTo(newLoc))
                    localizationOnMap = newLoc;
            }
        }
    }

    @Override
    public String toString() {
        return String.format(switch (facingDirection) {
            case NORTH -> "^";
            case EAST -> ">";
            case WEST -> "<";
            case SOUTH -> "v";
        });
    }

    public boolean isAt(Vector2d position) {
        return Objects.equals(localizationOnMap, position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return localizationOnMap == animal.localizationOnMap && Objects.equals(localizationOnMap, animal.localizationOnMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facingDirection, localizationOnMap);
    }

    @Override
    public Vector2d getPosition() {
        return localizationOnMap;
    }

    @Override
    public String getResourceString() {
        return switch (facingDirection) {
            case NORTH -> "up.png";
            case EAST -> "right.png";
            case SOUTH -> "down.png";
            case WEST -> "left.png";
        };
    }
}
