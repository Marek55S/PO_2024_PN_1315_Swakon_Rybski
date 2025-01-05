package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;
    private final Vector2d NORTH_UNIT_VECTOR  = new Vector2d(0,1);
    private final Vector2d EAST_UNIT_VECTOR  = new Vector2d(1,0);
    private final Vector2d SOUTH_UNIT_VECTOR  = new Vector2d(0,-1);
    private final Vector2d WEST_UNIT_VECTOR  = new Vector2d(-1,0);

    public String toString(){
        return switch(this){
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }
    public MapDirection next(){
        return switch(this){
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
    public MapDirection previous(){
        return switch(this){
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }
    public Vector2d toUnitVector(){
        return switch(this){
            case NORTH -> NORTH_UNIT_VECTOR;
            case EAST -> EAST_UNIT_VECTOR;
            case SOUTH -> SOUTH_UNIT_VECTOR;
            case WEST -> WEST_UNIT_VECTOR;

        };
    }

}
