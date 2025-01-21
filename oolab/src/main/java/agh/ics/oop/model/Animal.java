package agh.ics.oop.model;

import agh.ics.oop.utils.SimulationOptions;

import java.util.*;

public class Animal implements WorldElement {
    private MapDirection facingDirection;
    private Vector2d localizationOnMap;
    private final List<Integer> genome = new ArrayList<>();
    private int currentGenomeIndex = 0;
    public static final Random RANDOM = new Random();
    private int kidsCount = 0;
    private int age = 0;
    private final List<Animal> children = new ArrayList<>();
    private final Set<Animal> descendants = new HashSet<>();
    private final Animal parent1;
    private final Animal parent2;
    protected final SimulationOptions options = null;

    // all this static values should be moved to the configuration file
    protected int newbornsEnergy = 100;
    protected  int energyToReproduce = 100;
    protected int genomeLength = 8;
    protected int maxMutation = 3;
    private int energy = newbornsEnergy;

    public Animal(Vector2d localizationOnMap, List<Integer> genome) {
        this(localizationOnMap, genome, null, null);
    }


    public Animal(Vector2d localizationOnMap, List<Integer> genome, Animal parent1, Animal parent2) {
        this(localizationOnMap, genome, parent1, parent2, MapDirection.values()[RANDOM.nextInt(8)]);
    }

    public Animal(Vector2d localizationOnMap, List<Integer> genome, Animal parent1, Animal parent2, MapDirection facingDirection) {
        this.localizationOnMap = localizationOnMap;
        this.genome.addAll(genome);
        this.facingDirection = facingDirection;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public Animal(Vector2d localizationOnMap, List<Integer> genome, SimulationOptions options){
        this(localizationOnMap, genome, null,null);
        this.localizationOnMap = localizationOnMap;
        this.genome.addAll(genome);
        energyToReproduce = options.reproductionEnergy();
        newbornsEnergy = options.initialAnimalEnergy();
        genomeLength = options.genomeLength();
        maxMutation = options.mutationsCount();
    }

    public int getAge() {
        return age;
    }

    public MapDirection getFacingDirection() {
        return facingDirection;
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
        currentGenomeIndex = (currentGenomeIndex+1)% genomeLength;
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
            case NORTH -> "animal_up.png";
            case EAST -> "animal_r.png";
            case SOUTH -> "animal_d.png";
            case WEST -> "animal_l.png";
            case NORTH_EAST -> "animal_ru.png";
            case SOUTH_WEST -> "animal_ld.png";
            case NORTH_WEST -> "animal_lu.png";
            case SOUTH_EAST ->  "animal_rd.png";
        };
    }

    // method mostly for eating grass
    public void addEnergy(int energy){
        this.energy += energy;
    }

    public void subtractEnergy(int energy){
        this.energy -= energy;
        age++;
    }
    public int getEnergy(){
        return energy;
    }

    public boolean canReproduce(){
        return energy >= energyToReproduce;
    }

    public List<Integer> getGenome(){
        return Collections.unmodifiableList(genome);
    }

    // method for mutation of the genome, each gene can be mutated more than once
    protected void mutateGenome(List<Integer> genomeToMutate){
        for (int i = 0; i < RANDOM.nextInt(maxMutation); i++){
            if(RANDOM.nextBoolean()) genomeToMutate.set(RANDOM.nextInt(genomeLength), RANDOM.nextInt(8));
        }
    }


    public void addChildren(Animal child){
        kidsCount++;
        children.add(child);
        addDescendant(child);
    }

    public int getChildrenCount(){
        return kidsCount;
    }

    public int  getTotalDescendantsCount(){
        return descendants.size();
    }

    public List<Animal> getChildren(){
        return Collections.unmodifiableList(children);
    }

    private void addDescendant(Animal descendant) {
        if (descendants.add(descendant)) {
            if (parent1 != null) parent1.addDescendant(descendant);
            if (parent2 != null) parent2.addDescendant(descendant);
        }
        for (Animal subDescendant : descendant.descendants) {
            if (descendants.add(subDescendant)) {
                if (parent1 != null) parent1.addDescendant(subDescendant);
                if (parent2 != null) parent2.addDescendant(subDescendant);
            }
        }
    }


    // should be another method for different types of mutations
    public Animal reproduce(Animal partner){
        List<Integer> newGenome = new ArrayList<>();
        double energyFactor = (double) this.energy /(this.energy + partner.energy);
        int splitIndex = (int) Math.round(genomeLength * energyFactor);
        int reproduceEnergy = (int) Math.round(energyFactor * energyToReproduce);
        if(RANDOM.nextInt(2) == 0){
            newGenome.addAll(this.genome.subList(0, splitIndex));
            newGenome.addAll(partner.genome.subList(splitIndex, genomeLength));
        }
        else{
            newGenome.addAll(partner.genome.subList(0, splitIndex));
            newGenome.addAll(this.genome.subList(splitIndex, genomeLength));
        }
        this.subtractEnergy(reproduceEnergy);
        partner.subtractEnergy(energyToReproduce - reproduceEnergy);
        mutateGenome(newGenome);

        var child = new Animal(this.localizationOnMap, newGenome,this,partner);
        addChildren(child);
        partner.addChildren(child);

        return child;
    }

}
