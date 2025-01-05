package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parseDirectionAllValidDirections() {
        //given
        String[] validDirections = {"f","b","l","r"};
        List<MoveDirection> expectedArray = List.of(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT);

        // when
        List<MoveDirection> parsedArray = OptionsParser.parseDirection(validDirections);

        //then
        assertEquals(expectedArray, parsedArray);
    }

    @Test
    void parseDirectionEmptyDirections() {
        //given
        String[] emptyArray = {};
        List<MoveDirection> expectedArray = List.of();

        //when
        List<MoveDirection> parsedArray = OptionsParser.parseDirection(emptyArray);

        //then
        assertEquals(expectedArray, parsedArray);
    }

    @Test
    void parseDirectionALLInvalidDirections() {
        //given
        String[] invalidDirections = {"x", "y", "z"};

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parseDirection(invalidDirections);
        });
    }
    @Test
    void parseDirectionMixedDirections() {
        //given
        String[] mixedDirections = {"x","b","y","z","f"};
        List<MoveDirection> expectedArray = List.of(MoveDirection.BACKWARD,MoveDirection.FORWARD);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parseDirection(mixedDirections);
        });
    }

}