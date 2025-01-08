package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionsParserTest {
    @Test
    void translateSingular() {
        String[] testArguments = {"f"};
        List<MoveDirection> expected = Arrays.asList(MoveDirection.FORWARD);
        assertEquals(expected, OptionsParser.parseOptions(testArguments));

        testArguments[0] = "b";
        expected.set(0, MoveDirection.BACKWARD);
        assertEquals(expected, OptionsParser.parseOptions(testArguments));

        testArguments[0] = "l";
        expected.set(0, MoveDirection.LEFT);
        assertEquals(expected, OptionsParser.parseOptions(testArguments));

        testArguments[0] = "r";
        expected.set(0, MoveDirection.RIGHT);
        assertEquals(expected, OptionsParser.parseOptions(testArguments));

        testArguments[0] = "x";
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parseOptions(testArguments);
        });
    }

    @Test
    void translateMultipleValid() {
        String[] testArguments = {"f", "r", "l", "b"};
        List<MoveDirection> expected = Arrays.asList(MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.BACKWARD);
        assertEquals(expected, OptionsParser.parseOptions(testArguments));
    }

    @Test
    void translateMultipleInvalid() {
        String[] testArguments = {"x", "y", "z", "m"};
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parseOptions(testArguments);
        });
    }

    @Test
    void translateMultipleInvalidCombinationsOfValid() {
        String[] testArguments = {"frl", "bl", "lx", "lf", "fl", "rl", "lr"};
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parseOptions(testArguments);
        });
    }

    @Test
    void translateMultipleValidWithInvalidInside() {
        String[] testArguments = {"f", "r", "l", "b", "xyz", "assimov", "Yugo", "l", "b"};
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parseOptions(testArguments);
        });
    }

}
