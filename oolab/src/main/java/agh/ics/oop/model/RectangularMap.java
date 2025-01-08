package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Boundary bounds;
    Map<Vector2d, Animal> animals = new HashMap<>();

    public RectangularMap(int width, int height, int mapId) {
        super(mapId);
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);

        bounds = new Boundary(lowerLeft, upperRight);

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && (position.follows(lowerLeft) && position.precedes(upperRight));
    }

    @Override
    public Boundary getCurrentBounds() {
        return bounds;
    }
}
