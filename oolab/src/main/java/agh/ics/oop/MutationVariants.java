package agh.ics.oop;

public enum MutationVariants {
    STANDARD_MUTATION,
    SMALL_CHANGE_MUTATION;

    @Override
    public String toString() {
        return switch (this) {
            case STANDARD_MUTATION -> "std";
            case SMALL_CHANGE_MUTATION -> "sml";

        };
    }
}
