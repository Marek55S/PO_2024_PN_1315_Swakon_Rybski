package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalSlightMutationTest {

    @Test
    void childSlightlyMutatedGenome(){
        //given
        List<Integer> genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        Animal parent1 = new Animal(new Vector2d(2, 2), genome,null,null,MapDirection.NORTH);
        Animal parent2 = new Animal(new Vector2d(2, 2), genome,null,null,MapDirection.NORTH);
        //when
        Animal child = parent1.reproduce(parent2);
        //then
        List<Integer> childGenome = child.getGenome();
        for (int i = 0; i < 8; i++) {
            Integer gene = childGenome.get(i);
            Assertions.assertTrue(gene == genome.get(i) || gene == (genome.get(i) + 1) % 8 || gene == (genome.get(i) +7) % 8);
        }
    }

}