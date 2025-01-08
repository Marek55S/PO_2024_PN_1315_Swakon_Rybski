package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {

    private final List<Vector2d> leftPositions;
    private final Random rng = new Random();
    private int grassLeftToBeReturned;

    //This generation method is based on generating all possible coordinates inside field. The values are stored inside leftPositions list and when certain value is needed
    //the random element of the list is selected. Then the chosen element is stored in temporary variable and swapped with the last element in the list, and popped.
    //In the end the chosen value is returned from temporary variable.
    //It's not the best approach when it comes to memory efficiency - it requires O(10n) space
    //Aside from that drawback it guarantees that returned values are random and unique, while sticking to the requirement of not utilising pre-generated list of random values
    //Possible alternatives:
    //1. Generate values, do random shuffle on them and choose first N - memory consumption is not improved, time complexity is worse than in proposed solution, and specification rules are broken
    //2. Utilise same technique as in implemented solution but for each coordinate separately. This solution is better, theoretically working with only O(2n) memory usage
    //But is way harder to implement, because to allow possibility of two positions sharing one of coordinates, we would need to add way to generate 1 repeated and 1 unique coordinates
    //It is achievable but way more complex than implemented solution
    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        leftPositions = new ArrayList<>();
        grassLeftToBeReturned = grassCount;
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                leftPositions.add(new Vector2d(x, y));
            }
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator implements Iterator<Vector2d> {

        @Override
        public boolean hasNext() {
            return grassLeftToBeReturned > 0;
        }

        @Override
        public Vector2d next() {
            var positionsLeft = leftPositions.size();
            var nextPositionIndex = rng.nextInt(positionsLeft);
            var nextPosition = leftPositions.get(nextPositionIndex);
            leftPositions.set(nextPositionIndex, leftPositions.get(positionsLeft - 1));
            leftPositions.removeLast();
            grassLeftToBeReturned -= 1;
            return nextPosition;
        }
    }
}
