package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DarwinSimulationMapWithWater extends DarwinSimulationMap {

    private final HashMap<Vector2d, Water> waters;
    private final HashMap<Vector2d, WaterInflowed> inflowedWaters;
    private List<Animal> drownedAnimals;
    private int currentCycleStep = 0;
    // temporary
    public static final int WATER_CYCLE_DURATION = 20;
    public static final int WATER_STEP_DURATION = 5;

    public DarwinSimulationMapWithWater(int width, int height, int mapId) {
        super(width, height, mapId);
        var tmpWater = new Water(new Vector2d(1,1));
        waters = new HashMap<>();
        waters.put(tmpWater.getPosition(), tmpWater);
        inflowedWaters = new HashMap<>();
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if (waters.containsKey(position)) {
            return Optional.of(waters.get(position));
        }
        else if(inflowedWaters.containsKey(position)){
            return Optional.of(inflowedWaters.get(position));
        }
        return super.objectAt(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return Stream.concat(super.getElements().stream(), this.getAllWaters().stream()).toList();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && !waters.containsKey(position)&& !inflowedWaters.containsKey(position);
    }

    @Override
    public void place(Vector2d animalProposedLocalisation) throws IncorrectPositionException {
        super.place(animalProposedLocalisation);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position);
    }

    public void inflow(int inflowStep){
        drownedAnimals = new ArrayList<Animal>();
        var allWater = getAllWaters();
        for(var water : allWater){
            for(var direction : MapDirection.values()){
                var newPosition = water.getPosition().add(direction.toUnitVector());
                if(objectAt(newPosition).isPresent() && objectAt(newPosition).get() instanceof Grass){
                    super.removeGrass(newPosition);
                } else if(objectAt(newPosition).isPresent() && objectAt(newPosition).get() instanceof Animal){
                    drownedAnimals.addAll(animals.get(newPosition));
                }
                if(canMoveTo(newPosition)){
                    inflowedWaters.put(newPosition,new WaterInflowed(newPosition, inflowStep));}
            }

        }
    }



    public void outflow(int outflowStep){
        var waterToRemove = inflowedWaters.values().stream()
                .filter(water -> water.getInflowStep() == outflowStep)
                .toList();
        waterToRemove.forEach(water -> inflowedWaters.remove(water.getPosition()));

    }


    @Override
    public void removeDeadAnimals(){
        super.removeDeadAnimals();
        drownedAnimals.forEach(animal -> {
            List<Animal> animalsAtPosition = animals.get(animal.getPosition());
            if (animalsAtPosition != null) {
                animalsAtPosition.remove(animal);
                if (animalsAtPosition.isEmpty()) {
                    animals.remove(animal.getPosition());
                }
            }
            super.notifyObservers("Animal drowned at position " + animal.getPosition());
        });
    }

    @Override
    public void nextDay(){
        if (dayCounter % WATER_STEP_DURATION == 0) {
            if(dayCounter%WATER_CYCLE_DURATION < WATER_CYCLE_DURATION/2){
                currentCycleStep++;
                inflow(currentCycleStep);
            }
            else{
                outflow(currentCycleStep);
                currentCycleStep--;
            }
        }
        super.nextDay();
    }

    public List<Water> getAllWaters() {
        return Stream.concat(waters.values().stream(), inflowedWaters.values()
                .stream()).toList();
    }

    public boolean isWaterAt(Vector2d position){
        return waters.containsKey(position) || inflowedWaters.containsKey(position);
    }
}
