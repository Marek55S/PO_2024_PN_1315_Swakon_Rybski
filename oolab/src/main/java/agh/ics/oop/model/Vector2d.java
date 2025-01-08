package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    public boolean precedes(Vector2d other) {
        return x <= other.getX() && y <= other.getY();
    }

    public boolean follows(Vector2d other) {
        return x >= other.getX() && y >= other.getY();
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.getX(), y + other.getY());
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.getX(), y - other.getY());
    }

    public Vector2d upperRight(Vector2d other) {
        int newX = x > other.getX() ? x : other.getX();
        int newY = y > other.getY() ? y : other.getY();
        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int newX = x < other.getX() ? x : other.getX();
        int newY = y < other.getY() ? y : other.getY();
        return new Vector2d(newX, newY);
    }

    public Vector2d opposite() {
        return new Vector2d(-1 * x, -1 * y);
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vector2d otherVector)) return false;
        return x == otherVector.getX() && y == otherVector.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
