package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassTest {
    @Test
    void grassReturnsItsPosition() {
        var testPosition = new Vector2d(1, 1);
        var testGrass = new Grass(testPosition);

        Assertions.assertEquals(testPosition, testGrass.getPosition());
    }

    @Test
    void grassReturnsItsStringRepresentation() {
        var testPosition = new Vector2d(1, 1);
        var testGrass = new Grass(testPosition);

        Assertions.assertEquals("*", testGrass.toString());
    }

}
