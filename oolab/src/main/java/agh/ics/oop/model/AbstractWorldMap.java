package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map <Vector2d,Animal> animals =  new HashMap<Vector2d,Animal>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected final List<MapChangeListener>observersList = new ArrayList<>();
    private final UUID mapId = UUID.randomUUID();


    public boolean canMoveTo(Vector2d position){
        return !animals.containsKey(position);
    }

    public void place(Animal animal) throws IncorrectPositionException {
        if (this.canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            this.notifyAllObservers("animal was placed in position %s".formatted(animal.getPosition()));
        }
        else{throw new IncorrectPositionException(animal.getPosition());
        }
    }

    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position);
    }

    public void move(Animal animal, MoveDirection direction) {
        if (this.isOccupied(animal.getPosition())){
            animals.remove(animal.getPosition());
            Vector2d oldPosition = animal.getPosition();
            animal.move(direction,this);
            animals.put(animal.getPosition(), animal);
            this.notifyAllObservers("animal was moved from position %s to position %s".formatted(oldPosition, animal.getPosition()));
        }
    }

    public WorldElement objectAt(Vector2d position){
        return animals.get(position);
    }

    public  String toString(){
        return mapVisualizer.draw(this.getCurrentBounds().LowerLeft(), this.getCurrentBounds().UpperRight());
    }

    @Override
    public Collection<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    public abstract Boundary getCurrentBounds();

    public void addObserver(MapChangeListener observer) {
        observersList.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observersList.remove(observer);
    }

    @Override
    public UUID getID() {
        return mapId;
    }

    protected void notifyAllObservers(String message) {
        for(MapChangeListener observer : observersList){
             observer.mapChanged(this,message);
        }
    }
}
