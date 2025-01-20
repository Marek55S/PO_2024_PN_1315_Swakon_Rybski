package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DarwinSimulationMapWithWater extends DarwinSimulationMap {

    private final HashMap<Vector2d, Water> waters;

    public DarwinSimulationMapWithWater(int width, int height, int mapId) {
        super(width, height, mapId);

        waters = new HashMap<>();
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        Optional<WorldElement> tmp = super.objectAt(position);

        if(tmp.isPresent()) {
            return tmp;
        }

        return Optional.ofNullable(waters.get(position));
    }

    @Override
    public List<WorldElement> getElements() {
        return Stream.concat(super.getElements().stream(), waters.values().stream()).toList();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && !waters.containsKey(position);
    }

    @Override
    public void place(Vector2d animalProposedLocalisation) throws IncorrectPositionException {
        super.place(animalProposedLocalisation);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position);
    }
}
