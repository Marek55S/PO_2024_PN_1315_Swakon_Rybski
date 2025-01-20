package agh.ics.oop.utils;

public record SimulationOptions(int simulationWidth,
                                int simulationHeigth,
                                MapTypes mapType,
                                int initialGrassCount,
                                int plantEnergy,
                                int everydayPlantGrowth,
                                int initialAnimalEnergy,
                                int initialAnimalsCount,
                                int animalFullEnergy,
                                int reproductionEnergy,
                                int mutationsCount,
                                int genomeLength,
                                MutationVariants mutationVariant) {
    @Override
    public String toString() {
        return String.format("%d %d %s %d %d %d %d %d %d %d %d %d %s", simulationWidth, simulationHeigth,
                mapType, initialGrassCount, plantEnergy, everydayPlantGrowth, initialAnimalEnergy,
                initialAnimalsCount, animalFullEnergy, reproductionEnergy, mutationsCount, genomeLength, mutationVariant);
    }
}
