package agh.ics.oop.model;

import java.util.*;

public class GrassField extends AbstractWorldMap implements WorldMap{
    private final Map<Vector2d,Grass> grassMap = new HashMap<Vector2d,Grass>();
    private Vector2d grassBoundUpperRight;
    private Vector2d grassBoundLowerLeft;
    private Vector2d mapUpperRight;
    private Vector2d mapLowerLeft;
    private final Random generator = new Random();

    public GrassField(int grassNumber) {
        int generatorLimit = 1+(int) Math.sqrt(grassNumber*10);
        grassBoundUpperRight = new Vector2d(0,0);
        grassBoundLowerLeft = new Vector2d(generatorLimit,generatorLimit);
        placeGrass(grassNumber,generatorLimit);
    }

    private void placeGrass(int grassNumber, int generatorLimit) {
        while (grassMap.size()<grassNumber){
            int x =generator.nextInt(generatorLimit);
            int y =generator.nextInt(generatorLimit);
            Vector2d newGrassPosition = new Vector2d(x,y);
            if (!grassMap.containsKey(newGrassPosition)){
                grassMap.put(newGrassPosition,new Grass(newGrassPosition));
                grassBoundUpperRight = grassBoundUpperRight.upperRight(newGrassPosition);
                grassBoundLowerLeft = grassBoundLowerLeft.lowerLeft(newGrassPosition);
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grassMap.containsKey(position);
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.objectAt(position) == null) {
            return grassMap.get(position);
        }
        else{ return super.objectAt(position); }
    }

    private void findMapBound(){
        mapUpperRight = grassBoundUpperRight;
        mapLowerLeft = grassBoundLowerLeft;
        for (Vector2d position : animals.keySet()){
            mapUpperRight = mapUpperRight.upperRight(position);
            mapLowerLeft = mapLowerLeft.lowerLeft(position);
        }
    }

    @Override
    public Boundary getCurrentBounds() {
        findMapBound();
        return new Boundary(mapLowerLeft,mapUpperRight);
    }


    @Override
    public Collection<WorldElement> getElements(){
        Collection<WorldElement> elements = super.getElements();
        elements.addAll(grassMap.values());
        return elements;
    }


}
