package agh.ics.oop.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimalSlightMutation extends Animal {

    AnimalSlightMutation(Vector2d initialPosition, List<Integer> genome){
        super(initialPosition, genome);
    }

    // method for mutation of the genome, each gene can be slightly mutated only once
    @Override
    protected void mutateGenome(List<Integer> genomeToMutate){
        Set<Integer> mutatedGenes = new HashSet<>();
        for (int i = 0; i < MAX_MUTATION; i++){
            int geneToMutate = RANDOM.nextInt(GENOME_LENGTH);
            if(RANDOM.nextBoolean() && !mutatedGenes.contains(geneToMutate)){;
                mutatedGenes.add(geneToMutate);
                genomeToMutate.set(geneToMutate, (genomeToMutate.get(geneToMutate) + (RANDOM.nextBoolean() ? 1 : -1)+8) % 8);
            }
        }
    }

}
