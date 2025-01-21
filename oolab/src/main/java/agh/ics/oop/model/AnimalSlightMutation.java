package agh.ics.oop.model;

import java.util.List;

public class AnimalSlightMutation extends Animal {

    AnimalSlightMutation(Vector2d initialPosition, List<Integer> genome){
        super(initialPosition, genome);
    }

    // method for mutation of the genome, each gene can be slightly mutated only once
    @Override
    protected void mutateGenome(List<Integer> genomeToMutate){
        for (int i = 0; i < GENOME_LENGTH; i++){
            if(RANDOM.nextBoolean()){;
                genomeToMutate.set(i, (genomeToMutate.get(i) + (RANDOM.nextBoolean() ? 1 : -1)+8) % 8);
            }
        }
    }

}
