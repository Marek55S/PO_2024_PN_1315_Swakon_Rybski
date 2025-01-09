package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0, 1)),
    NORTH_EAST(new Vector2d(1, 1)),
    EAST(new Vector2d(1, 0)),
    SOUTH_EAST(new Vector2d(1, -1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTH_WEST(new Vector2d(-1, -1)),
    WEST(new Vector2d(-1, 0)),
    NORTH_WEST(new Vector2d(-1, 1))
    ;

    final Vector2d vector2d;

    private MapDirection(Vector2d vector2d) {
        this.vector2d = vector2d;
    }

    public String toString() {
        return switch (this) {
            case NORTH -> "Północ";
            case NORTH_EAST -> "Północny-Wschód";
            case EAST -> "Wschód";
            case SOUTH_EAST -> "Południowy-Wschód";
            case SOUTH -> "Południe";
            case SOUTH_WEST -> "Południowy-Zachód";
            case WEST -> "Zachód";
            case NORTH_WEST -> "Północny-Zachód";
        };
    }

    public MapDirection next() {
        return MapDirection.values()[(ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection previous() {
        return MapDirection.values()[(ordinal() - 1 + MapDirection.values().length) % MapDirection.values().length];
    }

    public Vector2d toUnitVector() {
        return this.vector2d;
    }
}
