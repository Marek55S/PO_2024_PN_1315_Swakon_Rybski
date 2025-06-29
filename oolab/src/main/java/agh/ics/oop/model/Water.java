package agh.ics.oop.model;

public class Water implements WorldElement{
    private final Vector2d position;

    public Water(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String getResourceString() {
        return "water.png";
    }

    @Override
    public String toString() {
        return "~";
    }
}
