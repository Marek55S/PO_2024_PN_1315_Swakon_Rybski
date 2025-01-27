package agh.ics.oop.utils; // czemu to nie jest w modelu?

public enum MapTypes {
    NORMAL_MAP,
    WATER_MAP;

    @Override
    public String toString() {
        return switch (this) {
            case NORMAL_MAP -> "std";
            case WATER_MAP -> "wtr";

        };
    }
}
