package agh.ics.oop.utils;

import agh.ics.oop.StatisticsTracker;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.List;

public class SimulationOptionsToFile { // nie rozumiem tej klasy; nazwa

    public void writeOptionsToFile(SimulationOptions simulationOptions, String filePath) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"width", "height", "mapType", "initialGrass", "plantEnergy",
                    "everydayPlants", "initialAnimalEnergy", "initialAnimalsCount", "animalFullEnergy",
                    "reproductionEnergy", "mutationsCount", "genomeLength", "mutationVariant"};
            String[] record = simulationOptions.toString().split(" ");

            csvWriter.writeNext(header);
            csvWriter.writeNext(record);
        }
    }

    public void writeSimulationLogHeaderToFile(String filePath) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"day", "animals_count", "animals_genomes", "plants_count", "free_fields", "average_energy", "average_livespan", "average_kids_amount"};
            csvWriter.writeNext(header);
        }
    }

    public void writeSimulationLogToFile(int day, StatisticsTracker stats, String filePath) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true))) {
            String genomes = "";
            for (var genome : stats.getMostPopularGenomes()) {
                genomes = genomes + " " + genome;
            }
            String[] record = {Integer.toString(day), Integer.toString(stats.getAnimalsCount()), genomes,
                    Integer.toString(stats.getGrassCount()),
                    Integer.toString(stats.getEmptyFieldsCount()),
                    Integer.toString(stats.getAverageEnergyLevel()),
                    Integer.toString(stats.getAverageLifespan()),
                    Integer.toString(stats.getAverageKidsAmount())};
            csvWriter.writeNext(record);
        }
    }


    public SimulationOptions readOptionsFromFile(String filePath) throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            reader.readNext();

            nextLine = reader.readNext();

            MapTypes mapType = switch (nextLine[2]) {
                case "std" -> MapTypes.NORMAL_MAP;
                case "wtr" -> MapTypes.WATER_MAP;
                default -> throw new IllegalStateException("Unexpected value: " + nextLine[2]);
            };

            MutationVariants mutationVariants = switch (nextLine[12]) {
                case "std" -> MutationVariants.STANDARD_MUTATION;
                case "sml" -> MutationVariants.SMALL_CHANGE_MUTATION;
                default -> throw new IllegalStateException("Unexpected value: " + nextLine[12]);
            };
            try {
                return new SimulationOptions(Integer.parseInt(nextLine[0]),
                        Integer.parseInt(nextLine[1]),
                        mapType,
                        Integer.parseInt(nextLine[3]),
                        Integer.parseInt(nextLine[4]),
                        Integer.parseInt(nextLine[5]),
                        Integer.parseInt(nextLine[6]),
                        Integer.parseInt(nextLine[7]),
                        Integer.parseInt(nextLine[8]),
                        Integer.parseInt(nextLine[9]),
                        Integer.parseInt(nextLine[10]),
                        Integer.parseInt(nextLine[11]),
                        mutationVariants);
            } catch (NumberFormatException e) {
                throw new CsvValidationException(e.getMessage());
            }
        }

    }
}
