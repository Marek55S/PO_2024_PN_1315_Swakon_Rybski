package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void toStringForAllCases(){
        //when
        MapDirection directionNorth = MapDirection.NORTH;
        MapDirection directionEast = MapDirection.EAST;
        MapDirection directionSouth = MapDirection.SOUTH;
        MapDirection directionWest = MapDirection.WEST;

        //then
        assertEquals("N", directionNorth.toString());
        assertEquals("E", directionEast.toString());
        assertEquals("S", directionSouth.toString());
        assertEquals("W", directionWest.toString());
    }

    @Test
    void nextForAllCases(){
        //when
        MapDirection directionNorth = MapDirection.NORTH;
        MapDirection directionEast = MapDirection.EAST;
        MapDirection directionSouth = MapDirection.SOUTH;
        MapDirection directionWest = MapDirection.WEST;

        //then
        assertEquals(MapDirection.EAST, directionNorth.next());
        assertEquals(MapDirection.SOUTH, directionEast.next());
        assertEquals(MapDirection.WEST, directionSouth.next());
        assertEquals(MapDirection.NORTH, directionWest.next());

    }

    @Test
    void previousForAllCases(){
        //when
        MapDirection directionNorth = MapDirection.NORTH;
        MapDirection directionEast = MapDirection.EAST;
        MapDirection directionSouth = MapDirection.SOUTH;
        MapDirection directionWest = MapDirection.WEST;

        //then
        assertEquals(MapDirection.WEST, directionNorth.previous());
        assertEquals(MapDirection.NORTH, directionEast.previous());
        assertEquals(MapDirection.EAST, directionSouth.previous());
        assertEquals(MapDirection.SOUTH, directionWest.previous());
    }

    @Test
    void toUnitVectorAllCases(){
        //when
        MapDirection directionNorth = MapDirection.NORTH;
        MapDirection directionEast = MapDirection.EAST;
        MapDirection directionSouth = MapDirection.SOUTH;
        MapDirection directionWest = MapDirection.WEST;

        //then
        assertEquals(new Vector2d(0,1), directionNorth.toUnitVector());
        assertEquals(new Vector2d(1,0), directionEast.toUnitVector());
        assertEquals(new Vector2d(0,-1), directionSouth.toUnitVector());
        assertEquals(new Vector2d(-1,0), directionWest.toUnitVector());

    }


}