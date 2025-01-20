package agh.ics.oop.model;

import java.util.*;

public class Animal implements WorldElement {
    private MapDirection facingDirection;
    private Vector2d localizationOnMap;
    private int energy = NEWBORNS_ENERGY;
    private final List<Integer> genome = new ArrayList<>();
    private int currentGenomeIndex = 0;
    public static final Random RANDOM = new Random();

    // all this static values should be moved to the configuration file
    public static final int NEWBORNS_ENERGY = 100;
    public static final int ENERGY_TO_REPRODUCE = 100;
    public static final int GENOME_LENGTH = 8;

    // redundant constructor
    public Animal() {
        this(new Vector2d(2, 2));
    }
    // redundant constructor
    public Animal(Vector2d localizationOnMap) {
        this.localizationOnMap = localizationOnMap;
        facingDirection = MapDirection.NORTH;
    }


    public Animal(Vector2d localizationOnMap, List<Integer> genome) {
        this.localizationOnMap = localizationOnMap;
        this.genome.addAll(genome);
        facingDirection = MapDirection.NORTH;
    }



    public MapDirection getFacingDirection() {
        return facingDirection;
    }

    // method move and moveByGenome are probably redundant
    // moveOnBorders should be changed to be used only for moving forward or merged with moveForward

    public void move(MoveDirection direction, AbstractWorldMap validator) {
        switch (direction) {
            case LEFT -> facingDirection = facingDirection.previous();
            case RIGHT -> facingDirection = facingDirection.next();
            case FORWARD -> {
                var newLoc = moveOnBorders(facingDirection.toUnitVector(), validator);
                if ( validator.canMoveTo(newLoc)) {
                    localizationOnMap = newLoc;
                }
            }
            case BACKWARD -> {
                var newLoc = moveOnBorders(facingDirection.toUnitVector().opposite(), validator);
                if (validator.canMoveTo(newLoc)) {
                    localizationOnMap = newLoc;
                }
            }
        }
    }

    private Vector2d moveOnBorders(Vector2d unitMove,AbstractWorldMap map){
        var newLoc = localizationOnMap.add(unitMove);
        if(newLoc.getX()<0){
            newLoc = new Vector2d(map.getCurrentBounds().upperRight().getX(), newLoc.getY());
        }
        else if(newLoc.getX()>map.getCurrentBounds().upperRight().getX()){
            newLoc = new Vector2d(0, newLoc.getY());
        }
        if (!newLoc.precedes(map.getCurrentBounds().upperRight()) || !newLoc.follows(map.getCurrentBounds().lowerLeft())) {
        this.rotateAnimal(4);
        newLoc = new Vector2d(newLoc.getX(), localizationOnMap.getY());
        }
        return newLoc;
    }

//    // method should be changed to move all 8 directions
//    public void moveByGenome(MoveValidator validator) {
//        facingDirection = MapDirection.values()[genome.get(currentGenomeIndex)];
//        var newLoc = localizationOnMap.add(this.facingDirection.toUnitVector());
//        if (validator.canMoveTo(newLoc))
//            localizationOnMap = newLoc;
//        currentGenomeIndex++;
//    }

    private void rotateAnimal(int rotation){
        for(int i = 0; i < rotation; i++){
            facingDirection = facingDirection.next();
        }
    }

    private void moveForward(AbstractWorldMap validator){
        var newLoc = moveOnBorders(facingDirection.toUnitVector(), validator);
        if (validator.canMoveTo(newLoc)) {
            localizationOnMap = newLoc;
        }
    }

    public void moveByGenome(AbstractWorldMap validator){
        var rotation = genome.get(currentGenomeIndex);
        rotateAnimal(rotation);
        moveForward(validator);
        currentGenomeIndex = (currentGenomeIndex+1)%GENOME_LENGTH;
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

    // equals should compare only by reference, no need to override it
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Animal animal = (Animal) o;
//        return localizationOnMap == animal.localizationOnMap && Objects.equals(localizationOnMap, animal.localizationOnMap);
//    }

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
        for (int i = 0; i < RANDOM.nextInt(GENOME_LENGTH); i++){
            if(RANDOM.nextBoolean()) genomeToMutate.set(RANDOM.nextInt(GENOME_LENGTH), RANDOM.nextInt(8));
        }
    }

    // method for mutation of the genome, each gene can be slightly mutated only once
    void slightMutation(List<Integer> genomeToMutate){
        for (int i = 0; i < GENOME_LENGTH; i++){
            if(RANDOM.nextBoolean()){;
                genomeToMutate.set(i, (genomeToMutate.get(i) + (RANDOM.nextBoolean() ? 1 : -1)+8) % 8);
            }
        }
    }

    // should be another method for different types of mutations
    public Animal reproduce(Animal partner){
        List<Integer> newGenome = new ArrayList<>();
        double energyFactor = (double) this.energy /(this.energy + partner.energy);
        int splitIndex = (int) Math.round(GENOME_LENGTH * energyFactor);
        int reproduceEnergy = (int) Math.round(energyFactor * ENERGY_TO_REPRODUCE);
        if(RANDOM.nextInt(2) == 0){
            newGenome.addAll(this.genome.subList(0, splitIndex));
            newGenome.addAll(partner.genome.subList(splitIndex, GENOME_LENGTH));
        }
        else{
            newGenome.addAll(partner.genome.subList(0, splitIndex));
            newGenome.addAll(this.genome.subList(splitIndex, GENOME_LENGTH));
        }
        this.subtractEnergy(reproduceEnergy);
        partner.subtractEnergy(ENERGY_TO_REPRODUCE - reproduceEnergy);
        //randomMutation(newGenome);
        slightMutation(newGenome);

        return new Animal(this.localizationOnMap, newGenome);
    }

}
