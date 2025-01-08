package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final MapVisualizer visualizer;
    private final int mapId;
    protected HashMap<Vector2d, Animal> animals;
    protected List<MapChangeListener> observers;

    public AbstractWorldMap(int mapId) {

        visualizer = new MapVisualizer(this);
        animals = new HashMap<>();

        observers = new ArrayList();

        this.mapId = mapId;

    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }


    @Override
    public void move(Animal animal, MoveDirection direction) {
        var oldPosition = animal.getLocalizationOnMap();
        var oldOrientation = animal.getFacingDirection();
        animal.move(direction, this);
        var newPosition = animal.getLocalizationOnMap();
        var newOrientation = animal.getFacingDirection();

        if (oldPosition != newPosition) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
            notifyObservers(String.format("Animal moved from %s to %s", oldPosition, newPosition));
        } else if (oldOrientation != newOrientation) {
            notifyObservers(String.format("Animal changed direction from %s to %s", oldOrientation, newOrientation));
        }
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        var animalProposedLocalisation = animal.getLocalizationOnMap();
        if (canMoveTo(animalProposedLocalisation)) {
            animals.put(animalProposedLocalisation, animal);
            notifyObservers(String.format("Animal was placed at %s", animalProposedLocalisation));
        } else {
            throw new IncorrectPositionException((animal.getPosition()));
        }
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        return Optional.ofNullable(animals.get(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    @Override
    //move to be abstract
    public boolean canMoveTo(Vector2d position) {
        return (animals.get(position) == null);
    }


    //this getter is safe, because we are returning new list - modification of it won't directly impact object state

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>(animals.values());
        return elements;
    }

    public abstract Boundary getCurrentBounds();

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return visualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public List<Animal> getOrderedAnimals() {
        return animals.values().stream().sorted(Comparator.comparing((Animal animal) -> animal.getPosition().getX()
        ).thenComparing((Animal animal) -> animal.getPosition().getY())).toList();
    }
}
