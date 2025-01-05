package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private static final Vector2d DEFAULT_POSITION = new Vector2d(2,2);
    private static final Vector2d UPPER_RIGHT_MAP_CORNER = new Vector2d(4,4);
    private static final Vector2d LOWER_LEFT_MAP_CORNER = new Vector2d(0,0);

    public Animal() {
        this(DEFAULT_POSITION);
    }

    public Animal(Vector2d position){
        orientation = MapDirection.NORTH;
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    @Override
    public String toString(){
        return orientation.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }


    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD ->{
                Vector2d newPosition = position.add(orientation.toUnitVector());
                if(validator.canMoveTo(newPosition)){
                    position = newPosition;
                }
            }
            case BACKWARD ->{
                Vector2d newPosition = position.subtract(orientation.toUnitVector());
                if(validator.canMoveTo(newPosition)){
                position = newPosition;
                }
            }
        }
    }
}
