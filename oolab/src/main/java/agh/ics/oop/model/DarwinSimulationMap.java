package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DarwinSimulationMap extends AbstractWorldMap {
    private final HashMap<Vector2d, Grass> grasses;
    private final Set<Vector2d> equatorFreePositions = new HashSet<>();
    private final Set<Vector2d> otherFreePositions = new HashSet<>();
    public static final Random GENERATOR = new Random();
    

    public DarwinSimulationMap(int width,int height, int mapId) {
        super(width, height, mapId);
        grasses = new HashMap<>();

        animals = new HashMap<>();

        // not exacly 20% of the map
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (height / 2 - (int)(0.1*height) <= j && j <= height / 2 + (int)(0.1*height)) {
                    equatorFreePositions.add(new Vector2d(i, j));
                } else {
                    otherFreePositions.add(new Vector2d(i, j));
                }
            }
        }
        this.growGrass();
    }

    public int getGrassCount(){
        return grasses.size();
    }

    private void growGrass(){
        Set<Vector2d> toRemoveEquator = equatorFreePositions.stream()
                .filter(position -> GENERATOR.nextDouble() < 0.8)
                .collect(Collectors.toSet());

        toRemoveEquator.forEach(position -> {
            grasses.put(position, new Grass(position));
            equatorFreePositions.remove(position);
        });

        Set<Vector2d> toRemoveOther = otherFreePositions.stream()
                .filter(position -> GENERATOR.nextDouble() < 0.2)
                .collect(Collectors.toSet());

        toRemoveOther.forEach(position -> {
            grasses.put(position, new Grass(position));
            otherFreePositions.remove(position);
        });

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
        return mapBounds;
    }


}
