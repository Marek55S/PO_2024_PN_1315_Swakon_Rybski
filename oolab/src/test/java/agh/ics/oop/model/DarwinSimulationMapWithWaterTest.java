package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DarwinSimulationMapWithWaterTest {

    @Test
    void initialWaterTest() {
        var map = new DarwinSimulationMapWithWater(10, 10, 1);
        Optional<WorldElement> water = map.objectAt(new Vector2d(1, 1));
        assertTrue(water.isPresent());
        assertInstanceOf(Water.class, water.get());
    }


    @Test
    void testInflowAddsNewWaterPositions() {
        var map = new DarwinSimulationMapWithWater(10, 10, 1);
        var waterPosition = new Vector2d(1, 1);
        assertEquals(1, map.getAllWaters().size());
        assertTrue(map.objectAt(new Vector2d(1, 1)).isPresent());

        map.inflow(1);

        for(var direction: MapDirection.values()){
            var newPosition = waterPosition.add(direction.toUnitVector());
            assertTrue(map.objectAt(newPosition).isPresent());
        }
    }

    @Test
    void testInflowDrownAnimal(){
        var map = new DarwinSimulationMapWithWater(10, 10, 1);
        try{
            map.place(new Vector2d(1, 2));
        } catch (IncorrectPositionException e){
            fail("Should not throw exception");
        }
        assertEquals(1, map.getOrderedAnimals().size());

        map.inflow(1);
        map.removeDeadAnimals();
        assertEquals(0, map.getOrderedAnimals().size());
    }

    @Test
    void testOutflowRemovesWater() {
        var map = new DarwinSimulationMapWithWater(10, 10, 1);

        map.inflow(1);
        assertTrue(map.objectAt(new Vector2d(2, 1)).isPresent());
        assertEquals(9, map.getAllWaters().size());

        map.outflow(1);
        assertFalse(map.objectAt(new Vector2d(2, 1)).isPresent());
        assertEquals(1, map.getAllWaters().size());
    }

    @Test
    void testNextDayInflow() {
        var map = new DarwinSimulationMapWithWater(10, 10, 1);
        try{
            map.place(new Vector2d(1, 2));
        } catch (IncorrectPositionException e){
            fail("Should not throw exception");
        }

        for (int i = 0; i < DarwinSimulationMapWithWater.WATER_STEP_DURATION; i++) {
            map.nextDay();
        }

        Optional<WorldElement> water = map.objectAt(new Vector2d(2, 1));
        assertTrue(water.isPresent());
        assertInstanceOf(Water.class, water.get());
    }

    @Test
    void testNextDayOutflow() {
        var map = new DarwinSimulationMapWithWater(10, 10, 1);
        try{
            map.place(new Vector2d(1, 2));
        } catch (IncorrectPositionException e){
            fail("Should not throw exception");
        }
        for (int i = 0; i < DarwinSimulationMapWithWater.WATER_CYCLE_DURATION / 2; i++) {
            map.nextDay();
        }

        assertTrue(map.objectAt(new Vector2d(2, 1)).isPresent());
        assertTrue(map.objectAt(new Vector2d(3, 3)).isPresent());
        assertEquals(16, map.getAllWaters().size());

        for (int i = 0; i < DarwinSimulationMapWithWater.WATER_STEP_DURATION; i++) {
            map.nextDay();
        }
        assertTrue(map.objectAt(new Vector2d(2, 1)).isPresent());
        assertFalse(map.objectAt(new Vector2d(3, 3)).isPresent());
        assertEquals(9, map.getAllWaters().size());
    }



}