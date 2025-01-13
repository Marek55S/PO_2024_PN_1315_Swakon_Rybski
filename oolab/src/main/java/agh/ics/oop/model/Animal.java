package agh.ics.oop.model;

import java.util.*;

public class Animal implements WorldElement {
    private MapDirection facingDirection;
    private Vector2d localizationOnMap;
    private int energy = NEWBORNS_ENERGY;
    private final List<Integer> genome = new ArrayList<>();
    private int currentGenomeIndex = 0;
    public static final Random RANDOM = new Random();
    public static final int NEWBORNS_ENERGY = 100;
    public static final int ENERGY_TO_REPRODUCE = 100;
    public static final int GENOM_LENGTH = 8;

    public Animal() {
        this(new Vector2d(2, 2));
    }

    public Animal(Vector2d localizationOnMap) {
        this.localizationOnMap = localizationOnMap;
        facingDirection = MapDirection.NORTH;
    }


    //temporary constructor
    public Animal(Vector2d localizationOnMap, List<Integer> genome) {
        this.localizationOnMap = localizationOnMap;
        this.genome.addAll(genome);
        facingDirection = MapDirection.NORTH;
    }


    public Vector2d getLocalizationOnMap() {
        return localizationOnMap;
    }

    public MapDirection getFacingDirection() {
        return facingDirection;
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case LEFT -> facingDirection = facingDirection.previous();
            case RIGHT -> facingDirection = facingDirection.next();
            case FORWARD -> {
                var newLoc = localizationOnMap.add(this.facingDirection.toUnitVector());
                if (validator.canMoveTo(newLoc))
                    localizationOnMap = newLoc;
            }
            case BACKWARD -> {
                var newLoc = localizationOnMap.subtract(this.facingDirection.toUnitVector());
                if (validator.canMoveTo(newLoc))
                    localizationOnMap = newLoc;
            }
        }
    }

    // method should be changed to move all 8 directions
    public void moveByGenome(MoveValidator validator) {
        facingDirection = MapDirection.values()[genome.get(currentGenomeIndex)];
        var newLoc = localizationOnMap.add(this.facingDirection.toUnitVector());
        if (validator.canMoveTo(newLoc))
            localizationOnMap = newLoc;
        currentGenomeIndex++;
    }

    @Override
    public String toString() {
        return String.format(switch (facingDirection) {
            case NORTH -> "^";
            case NORTH_EAST -> "↗";
            case EAST -> ">";
            case NORTH_WEST -> "↖";
            case WEST -> "<";
            case SOUTH_EAST -> "↘";
            case SOUTH -> "v";
            case SOUTH_WEST -> "↙";
        });
    }

    public boolean isAt(Vector2d position) {
        return Objects.equals(localizationOnMap, position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return localizationOnMap == animal.localizationOnMap && Objects.equals(localizationOnMap, animal.localizationOnMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facingDirection, localizationOnMap);
    }

    @Override
    public Vector2d getPosition() {
        return localizationOnMap;
    }

    @Override
    public String getResourceString() {
        return switch (facingDirection) {
            case NORTH -> "up.png";
            case EAST -> "right.png";
            case SOUTH -> "down.png";
            case WEST -> "left.png";
            //below are temporary values that need to be changed
            case NORTH_EAST -> "up.png";
            case SOUTH_WEST -> "up.png";
            case NORTH_WEST -> "up.png";
            case SOUTH_EAST ->  "up.png";
        };
    }

    // method mostly for eating grass
    public void addEnergy(int energy){
        this.energy += energy;
    }
    public void subtractEnergy(int energy){
        this.energy -= energy;
    }
    public int getEnergy(){
        return energy;
    }

    public boolean canReproduce(){
        return energy >= ENERGY_TO_REPRODUCE;
    }

    public List<Integer> getGenome(){
        return Collections.unmodifiableList(genome);
    }

    // method for mutation of the genome, each gene can be mutated more than once
    private void randomMutation(List<Integer> genomeToMutate){
        for (int i = 0; i < RANDOM.nextInt(GENOM_LENGTH); i++){
            if(RANDOM.nextBoolean()) genomeToMutate.set(RANDOM.nextInt(GENOM_LENGTH), RANDOM.nextInt(8));
        }
    }

    // method for mutation of the genome, each gene can be slightly mutated only once
    void slightMutation(List<Integer> genomeToMutate){
        for (int i = 0; i < GENOM_LENGTH; i++){
            if(RANDOM.nextBoolean()){
                int index = RANDOM.nextInt(GENOM_LENGTH);
                genomeToMutate.set(index, (genomeToMutate.get(index) + (RANDOM.nextBoolean()? 1 : -1))%8);
            }
        }
    }

    // should be another method for different types of mutations
    public Animal reproduce(Animal partner){
        List<Integer> newGenome = new ArrayList<>();
        double energyFactor = (double) this.energy /(this.energy + partner.energy);
        int splitIndex = (int) Math.round(GENOM_LENGTH * energyFactor);
        int reproduceEnergy = (int) Math.round(energyFactor * ENERGY_TO_REPRODUCE);
        if(RANDOM.nextInt(2) == 0){
            newGenome.addAll(this.genome.subList(0, splitIndex));
            newGenome.addAll(partner.genome.subList(splitIndex, GENOM_LENGTH));
        }
        else{
            newGenome.addAll(partner.genome.subList(0, splitIndex));
            newGenome.addAll(this.genome.subList(splitIndex, GENOM_LENGTH));
        }
        this.subtractEnergy(reproduceEnergy);
        partner.subtractEnergy(ENERGY_TO_REPRODUCE - reproduceEnergy);
        //randomMutation(newGenome);
        slightMutation(newGenome);

        return new Animal(this.localizationOnMap, newGenome);
    }

}
