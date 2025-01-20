package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.DarwinSimulationMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.MapTypes;
import agh.ics.oop.utils.MutationVariants;
import agh.ics.oop.utils.SimulationOptions;
import agh.ics.oop.utils.SimulationOptionsToFile;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class MainWindowPresenter {
    //private final List<SimulationPresenter> presenters = new ArrayList<>();
    private final List<Simulation> simulations = new ArrayList<>();
    private final SimulationEngine simulationEngine = new SimulationEngine(simulations);
    @FXML
    private Label infolabel;
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private ChoiceBox<MapTypes> mapVariantCB;
    @FXML
    private Spinner<Integer> initialGrassCount;
    @FXML
    private Spinner<Integer> energyFromPlant;
    @FXML
    private Spinner<Integer> everydayPlantGrowth;
    @FXML
    private Spinner<Integer> initialAnimalEnergy;
    @FXML
    private Spinner<Integer> initialAnimalsCount;
    @FXML
    private Spinner<Integer> animalFullEnergy;
    @FXML
    private Spinner<Integer> reproductionEnergy;
    @FXML
    private Spinner<Integer> mutationsCount;
    @FXML
    private ChoiceBox<MutationVariants> mutationVariant;
    @FXML
    private Spinner<Integer> genomeLength;
    private int ids = 0;
    private SimulationOptions simulationOptions;

    public void initialize() {
        mapVariantCB.getItems().addAll(MapTypes.values());
        mutationVariant.getItems().addAll(MutationVariants.values());
        //load defaults
        SimulationOptionsToFile simulationOptionsToFile = new SimulationOptionsToFile();
        //handle exceptions
        try{
        SimulationOptions options = simulationOptionsToFile.readOptionsFromFile("default.csv");
        widthSpinner.getValueFactory().setValue(options.simulationWidth());
        heightSpinner.getValueFactory().setValue(options.simulationHeigth());
        mapVariantCB.setValue(options.mapType());
        initialGrassCount.getValueFactory().setValue(options.initialGrassCount());
        energyFromPlant.getValueFactory().setValue(options.plantEnergy());
        everydayPlantGrowth.getValueFactory().setValue(options.everydayPlantGrowth());
        initialAnimalEnergy.getValueFactory().setValue(options.initialAnimalEnergy());
        initialAnimalsCount.getValueFactory().setValue(options.initialAnimalsCount());
        animalFullEnergy.getValueFactory().setValue(options.animalFullEnergy());
        reproductionEnergy.getValueFactory().setValue(options.reproductionEnergy());
        mutationsCount.getValueFactory().setValue(options.mutationsCount());
        genomeLength.getValueFactory().setValue(options.genomeLength());
        mutationVariant.setValue(options.mutationVariant());
        }
        catch (CsvValidationException e) {
            infolabel.setText("Default configuration could't be loaded");
        } catch (IOException e) {
            infolabel.setText("Default configuration could't be loaded");
        }
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) throws IOException{

        simulationOptions = generateSimulationOptions();
        //here some validation could be done
        if (true) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
                BorderPane viewRoot = loader.load();
                SimulationPresenter presenter = loader.getController();
                //presenters.add(presenter);

                Stage stage = new Stage();

                configureStage(stage, viewRoot);
                stage.show();

                var simulationMap = new DarwinSimulationMap(30,30, ids);
                ids += 1;
                presenter.setWorldMap(simulationMap);
                var positions = List.of(new Vector2d(1,1));
                var simulation = new Simulation(positions, simulationMap);
                presenter.setSimulation(simulation);
                simulations.add(simulation);
                simulationEngine.addToThreadPool(simulation);
                stage.setOnCloseRequest(event -> {
                simulation.stop();
                // Save file
                });

        } else {
            infolabel.setText("Moves list shouldn't be empty");
        }

    }



    private SimulationOptions generateSimulationOptions(){
        try {
            return new SimulationOptions(widthSpinner.getValue(),
                    heightSpinner.getValue(), mapVariantCB.getValue(),
                    initialGrassCount.getValue(), energyFromPlant.getValue(),
                    everydayPlantGrowth.getValue(), initialAnimalEnergy.getValue(),
                    initialAnimalsCount.getValue(), animalFullEnergy.getValue(),
                    reproductionEnergy.getValue(), mutationsCount.getValue(),
                    genomeLength.getValue(), mutationVariant.getValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //throw new RuntimeException(e);
            return null;
        }
    }

    public void onSaveConfigClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("settings.csv");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(genomeLength.getScene().getWindow());
        String path = "default.csv";
        if (file != null) {
            path = file.getAbsolutePath();
        }

        SimulationOptionsToFile simulationOptionsToFile = new SimulationOptionsToFile();
        try{
        simulationOptionsToFile.writeOptionsToFile(generateSimulationOptions(), path);
        } catch (IOException e) {
            infolabel.setText("Error writing config file");
        }

    }

    public void onLoadConfigClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("settings.csv");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(genomeLength.getScene().getWindow());
        if (file != null) {
            String path = file.getAbsolutePath();
            SimulationOptionsToFile simulationOptionsToFile = new SimulationOptionsToFile();
            try{
                SimulationOptions options = simulationOptionsToFile.readOptionsFromFile(path);
                widthSpinner.getValueFactory().setValue(options.simulationWidth());
                heightSpinner.getValueFactory().setValue(options.simulationHeigth());
                mapVariantCB.setValue(options.mapType());
                initialGrassCount.getValueFactory().setValue(options.initialGrassCount());
                energyFromPlant.getValueFactory().setValue(options.plantEnergy());
                everydayPlantGrowth.getValueFactory().setValue(options.everydayPlantGrowth());
                initialAnimalEnergy.getValueFactory().setValue(options.initialAnimalEnergy());
                initialAnimalsCount.getValueFactory().setValue(options.initialAnimalsCount());
                animalFullEnergy.getValueFactory().setValue(options.animalFullEnergy());
                reproductionEnergy.getValueFactory().setValue(options.reproductionEnergy());
                mutationsCount.getValueFactory().setValue(options.mutationsCount());
                genomeLength.getValueFactory().setValue(options.genomeLength());
                mutationVariant.setValue(options.mutationVariant());
            } catch (IOException e) {
                infolabel.setText("Error reading from config file");
            } catch (CsvValidationException e) {
                infolabel.setText("Error reading from config file");
            }
        } else{
            infolabel.setText("Error reading from config file");
        }



    }
    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
