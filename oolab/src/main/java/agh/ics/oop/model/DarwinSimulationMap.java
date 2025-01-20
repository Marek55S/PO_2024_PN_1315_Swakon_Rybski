package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DarwinSimulationMap extends AbstractWorldMap {
    private final HashMap<Vector2d, Grass> grasses;
    private final Set<Vector2d> equatorFreePositions = new HashSet<>();
    private final Set<Vector2d> otherFreePositions = new HashSet<>();
    protected int dayCounter = 0;
    

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

    public int getDayCounter(){
        return dayCounter;
    }


    public void growGrass(){
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


    public void removeDeadAnimals(){
        List<Animal> deadAnimals = animals.values().stream()
                .flatMap(Collection::stream)
                .filter(animal -> animal.getEnergy() <= 0)
                .toList();

        deadAnimals.forEach(animal -> {
            List<Animal> animalsAtPosition = animals.get(animal.getPosition());
            if (animalsAtPosition != null) {
                animalsAtPosition.remove(animal);
                if (animalsAtPosition.isEmpty()) {
                    animals.remove(animal.getPosition());
                }
            }
            super.notifyObservers("Animal died at position " + animal.getPosition());
        });
    }

    public void eatGrass(int grassEnergy){
        for(Animal animal: super.getOrderedByEnergyAnimals()){
            if(grasses.containsKey(animal.getPosition())){
                animal.addEnergy(grassEnergy);
                grasses.remove(animal.getPosition());
                super.notifyObservers("Animal ate grass at position " + animal.getPosition());
            }
        }
    }


    public void reproduceAnimals(){
        for(List<Animal> animalsAtPosition : animals.values()) {
            if (animalsAtPosition.size() >= 2) {
                animalsAtPosition.sort(Comparator.comparing(Animal::getEnergy).reversed());
                int i = 0;
                while (i < animalsAtPosition.size() - 1) {
                    Animal parent1 = animalsAtPosition.get(0);
                    Animal parent2 = animalsAtPosition.get(1);
                    if (parent1.canReproduce() && parent2.canReproduce()) {
                        Animal child = parent1.reproduce(parent2);
                        animals.get(child.getPosition()).add(child);
                        notifyObservers("Animal was born at position " + child.getPosition());
                    }
                    i+=2;
                }
            }
        }}

    public void takeEnergyFromAnimals(int energy){
        for (Animal animal : getOrderedByEnergyAnimals()) {
            animal.subtractEnergy(energy);
        }
    }

    public void removeGrass(Vector2d position){
        grasses.remove(position);
    }

    public void nextDay(){
        removeDeadAnimals();
        moveAllAnimals();
        eatGrass(15);
        reproduceAnimals();
        growGrass();
        takeEnergyFromAnimals(5);
        dayCounter++;
    }

}
