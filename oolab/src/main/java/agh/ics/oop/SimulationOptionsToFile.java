package agh.ics.oop;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class SimulationOptionsToFile {

    public void writeOptionsToFile(SimulationOptions simulationOptions, String filePath) {
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))){
            String[] header = {"width", "height", "mapType", "initialGrass", "plantEnergy",
            "everydayPlants", "initialAnimalEnergy", "initialAnimalsCount", "animalFullEnergy",
            "reproductionEnergy", "mutationsCount", "genomeLength", "mutationVariant"};
            String[] record = simulationOptions.toString().split(" ");

            csvWriter.writeNext(header);
            csvWriter.writeNext(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimulationOptions readOptionsFromFile(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            reader.readNext();

            nextLine = reader.readNext();

            MapTypes mapType = switch (nextLine[2]){
                case "std" -> MapTypes.NORMAL_MAP;
                case "wtr" -> MapTypes.WATER_MAP;
                default -> throw new IllegalStateException("Unexpected value: " + nextLine[2]);
            };

            MutationVariants mutationVariants = switch (nextLine[12]){
                case "std" -> MutationVariants.STANDARD_MUTATION;
                case "sml" -> MutationVariants.SMALL_CHANGE_MUTATION;
                default -> throw new IllegalStateException("Unexpected value: " + nextLine[12]);
            };

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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
