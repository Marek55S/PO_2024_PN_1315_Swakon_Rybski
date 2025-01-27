package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {
    @Test
    public void directionReturnsNextDirection() {
        Assertions.assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH.next());
        Assertions.assertEquals(MapDirection.EAST, MapDirection.NORTH_EAST.next());
        Assertions.assertEquals(MapDirection.SOUTH_EAST, MapDirection.EAST.next());
        Assertions.assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_EAST.next());
        Assertions.assertEquals(MapDirection.SOUTH_WEST, MapDirection.SOUTH.next());
        Assertions.assertEquals(MapDirection.WEST, MapDirection.SOUTH_WEST.next());
        Assertions.assertEquals(MapDirection.NORTH_WEST, MapDirection.WEST.next());
        Assertions.assertEquals(MapDirection.NORTH, MapDirection.NORTH_WEST.next());
    }

    @Test
    public void directionReturnsPreviousDirection() {
        Assertions.assertEquals(MapDirection.NORTH_EAST, MapDirection.EAST.previous());
        Assertions.assertEquals(MapDirection.NORTH, MapDirection.NORTH_EAST.previous());
        Assertions.assertEquals(MapDirection.NORTH_WEST, MapDirection.NORTH.previous());
        Assertions.assertEquals(MapDirection.WEST, MapDirection.NORTH_WEST.previous());
        Assertions.assertEquals(MapDirection.SOUTH_WEST, MapDirection.WEST.previous());
        Assertions.assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_WEST.previous());
        Assertions.assertEquals(MapDirection.SOUTH_EAST, MapDirection.SOUTH.previous());
        Assertions.assertEquals(MapDirection.EAST, MapDirection.SOUTH_EAST.previous());
    }

    @Test
    public void directionReturnsProperMovement() {
//        Assertions.assertEquals();
    }


}
