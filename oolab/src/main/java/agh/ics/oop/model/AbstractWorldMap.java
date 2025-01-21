package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final MapVisualizer visualizer;
    private final int mapId;
    protected HashMap<Vector2d, List<Animal>> animals;
    protected List<MapChangeListener> observers = new ArrayList<>();
    public final Boundary mapBounds;
    public static final Random GENERATOR = new Random();


    AbstractWorldMap(int mapId){
        visualizer = new MapVisualizer(this);
        animals = new HashMap<>();
        mapBounds = new Boundary(new Vector2d(0, 0), new Vector2d(0, 0));
        this.mapId = mapId;
    }

    public AbstractWorldMap(int width,int height,int mapId) {

        visualizer = new MapVisualizer(this);
        animals = new HashMap<>();
        mapBounds = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));

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
    public void moveAllAnimals() {
        for (var animal : getOrderedByEnergyAnimals()) {
            var oldPosition = animal.getPosition();
            var oldOrientation = animal.getFacingDirection();
            animal.moveByGenome(this);
            var newPosition = animal.getPosition();
            var newOrientation = animal.getFacingDirection();

            if (oldPosition != newPosition) {
                animals.get(oldPosition).remove(animal);
                if (!animals.containsKey(newPosition)) {
                    animals.put(newPosition, new LinkedList<>());
                }
                animals.get(newPosition).add(animal);

                notifyObservers(String.format("Animal moved from %s to %s", oldPosition, newPosition));
            } else if (oldOrientation != newOrientation) {
                notifyObservers(String.format("Animal changed direction from %s to %s", oldOrientation, newOrientation));
            }
        }
    }


    @Override
    public void place(Vector2d animalProposedLocalisation) throws IncorrectPositionException {
        var animal = new Animal(animalProposedLocalisation, generateGenome());
        if (canMoveTo(animalProposedLocalisation)) {
            if (!animals.containsKey(animalProposedLocalisation)) {
                animals.put(animalProposedLocalisation, new LinkedList<>());
            }
            animals.get(animalProposedLocalisation).add(animal);
            notifyObservers(String.format("Animal was placed at %s", animalProposedLocalisation));
        } else {
            throw new IncorrectPositionException((animal.getPosition()));
        }
    }

    private List<Integer> generateGenome(){
        List<Integer> genome = new ArrayList<>();
        for(int i = 0; i < Animal.GENOME_LENGTH; i++){
            genome.add(GENERATOR.nextInt(8));
        }
        return genome;
    }


    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if (animals.containsKey(position)) {
            return animals.get(position).stream()
                    .max(Comparator.comparing(Animal::getEnergy))
                    .map(animal -> animal);
        }
        return Optional.empty();
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent() && (objectAt(position) instanceof Optional<List<Animal>> );
    }

    @Override
    //move to be abstract
    public boolean canMoveTo(Vector2d position) {
        return position.follows(mapBounds.lowerLeft()) && position.precedes(mapBounds.upperRight());
    }

    @Override
    public List<WorldElement> getElements() {
        return animals.values().stream().flatMap(List::stream).map(animal -> (WorldElement) animal).toList();
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

    // probably useless, maybe better will be method that doesnt sort
    @Override
    public List<Animal> getOrderedAnimals() {
        return animals.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing((Animal animal) -> animal.getPosition().getX())
                .thenComparing(animal -> animal.getPosition().getY())).toList();
    }

    public List<Animal> getOrderedByEnergyAnimals() {
        return animals.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Animal::getEnergy).reversed()).toList();
    }

}
