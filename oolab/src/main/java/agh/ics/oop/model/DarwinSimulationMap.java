package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DarwinSimulationMap extends AbstractWorldMap {
    private final HashMap<Vector2d, Grass> grasses;
    private final Vector2d upperBoundary;
    private final Vector2d lowerBoundary;
    private final Boundary bounds;
    

    public DarwinSimulationMap(int width,int height, int mapId) {
        super(mapId);
        grasses = new HashMap<>();
        lowerBoundary = new Vector2d(0, 0);
        upperBoundary = new Vector2d(width - 1, height - 1);

        bounds = new Boundary(lowerBoundary, upperBoundary);
        animals = new HashMap<>();
    }


    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        var animalAt = super.objectAt(position);
        if (animalAt.isPresent()) {
            return animalAt;
        }
        return Optional.ofNullable(grasses.get(position));
    }


    @Override
    public List<WorldElement> getElements() {
        return Stream.concat(super.getElements().stream(), grasses.values().stream()).toList();
    }

    @Override
    public Boundary getCurrentBounds() {
        var drawingLowerBoundary = lowerBoundary;
        var drawingUpperBoundary = upperBoundary;
        for (var animal : animals.values()) {
            drawingLowerBoundary = drawingLowerBoundary.lowerLeft(animal.getPosition());
            drawingUpperBoundary = drawingUpperBoundary.upperRight(animal.getPosition());
        }
        return new Boundary(drawingLowerBoundary, drawingUpperBoundary);
    }
}
