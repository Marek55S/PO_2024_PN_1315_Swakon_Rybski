package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0, 1)), EAST(new Vector2d(1, 0)), SOUTH(new Vector2d(0, -1)), WEST(new Vector2d(-1, 0));

    final Vector2d vector2d;

    private MapDirection(Vector2d vector2d) {
        this.vector2d = vector2d;
    }

    public String toString() {
        return switch (this) {
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
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
