package agh.ics.oop.model;

import agh.ics.oop.StatisticsTracker;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.List;
import java.util.Optional;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends agh.ics.oop.model.MoveValidator {

    /**
     * Place a animal on the map.
     *
     * @param animalProposedLocalisation position of animal to place on the map.
     */
    void place(Vector2d animalProposedLocalisation) throws IncorrectPositionException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal, MoveDirection direction);

    void moveAllAnimals();

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    Optional<WorldElement> objectAt(Vector2d position);

    boolean canMoveTo(Vector2d position);

    List<WorldElement> getElements();

    Boundary getCurrentBounds();

    int getId();

    List<Animal> getOrderedAnimals();

    StatisticsTracker getStatistics();
}
