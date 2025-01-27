package agh.ics.oop.model;

import agh.ics.oop.utils.SimulationOptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimalSlightMutation extends Animal {

    AnimalSlightMutation(Vector2d initialPosition, List<Integer> genome) {
        super(initialPosition, genome);
    }

    AnimalSlightMutation(Vector2d initialPosition, List<Integer> genome, Animal parent1, Animal parent2) {
        super(initialPosition, genome, parent1, parent2);
    }

    AnimalSlightMutation(Vector2d initialPosition, List<Integer> genome, Animal parent1, Animal parent2, MapDirection facingDirection) {
        super(initialPosition, genome, parent1, parent2, facingDirection);
    }

    AnimalSlightMutation(Vector2d initialPosition, List<Integer> genome, SimulationOptions options) {
        super(initialPosition, genome, options);
    }

    // method for mutation of the genome, each gene can be slightly mutated only once
    @Override
    protected void mutateGenome(List<Integer> genomeToMutate) {
        Set<Integer> mutatedGenes = new HashSet<>();
        for (int i = 0; i < maxMutation; i++) {
            int geneToMutate = RANDOM.nextInt(genomeLength);
            if (RANDOM.nextBoolean() && !mutatedGenes.contains(geneToMutate)) {
                ;
                mutatedGenes.add(geneToMutate);
                genomeToMutate.set(geneToMutate, (genomeToMutate.get(geneToMutate) + (RANDOM.nextBoolean() ? 1 : -1) + 8) % 8);
            }
        }
    }

}
