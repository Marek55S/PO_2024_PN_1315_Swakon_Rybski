package agh.ics.oop.utils;

public record MapOptions(int simulationWidth,
                         int simulationHeigth,
                         int initialGrassCount,
                         int plantEnergy,
                         int everydayPlantGrowth, MutationVariants mutationVariant) {
}
