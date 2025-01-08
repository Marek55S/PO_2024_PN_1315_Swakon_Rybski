package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private final HashMap<Vector2d, Grass> grasses;
    private Vector2d upperVisualisationBoundary;
    private Vector2d lowerVisualisationBoundary;

    public GrassField(int grassCount, int mapId) {
        super(mapId);
        grasses = new HashMap<>();
        var upperBound = (int) Math.sqrt(grassCount * 10);
        lowerVisualisationBoundary = new Vector2d(upperBound, upperBound);
        upperVisualisationBoundary = new Vector2d(0, 0);
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(upperBound, upperBound, grassCount);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
            upperVisualisationBoundary = upperVisualisationBoundary.upperRight(grassPosition);
            lowerVisualisationBoundary = lowerVisualisationBoundary.lowerLeft(grassPosition);
        }

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
        var drawingLowerBoundary = lowerVisualisationBoundary;
        var drawingUpperBoundary = upperVisualisationBoundary;
        for (var animal : animals.values()) {
            drawingLowerBoundary = drawingLowerBoundary.lowerLeft(animal.getPosition());
            drawingUpperBoundary = drawingUpperBoundary.upperRight(animal.getPosition());
        }
        return new Boundary(drawingLowerBoundary, drawingUpperBoundary);
    }


}
