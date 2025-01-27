package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.DarwinSimulationMap;
import agh.ics.oop.model.DarwinSimulationMapWithWater;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.MapTypes;
import agh.ics.oop.utils.MutationVariants;
import agh.ics.oop.utils.SimulationOptions;
import agh.ics.oop.utils.SimulationOptionsToFile;
import com.opencsv.exceptions.CsvValidationException;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private Button logsLoc;
    @FXML
    private CheckBox shouldSaveLogs;
    private int ids = 0;
    private SimulationOptions simulationOptions;
    private File logsSavingLoc = null;

    private final Stage stage;

    public MainWindowPresenter(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        mapVariantCB.getItems().addAll(MapTypes.values());
        mutationVariant.getItems().addAll(MutationVariants.values());
        SimulationOptionsToFile simulationOptionsToFile = new SimulationOptionsToFile();
        try{
        SimulationOptions options = simulationOptionsToFile.readOptionsFromFile("default.csv");
        setInterfaceValues(options);
        }
        catch (CsvValidationException | IOException e) {
            displayError(ErrorMessages.DEFAULTS_LOADING);
        }
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) throws IOException{

        simulationOptions = generateSimulationOptions();
        //here some validation could be done
        if (true) {
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
                loader.setControllerFactory(param -> {
                    if (param == SimulationPresenter.class) {
                        if(shouldSaveLogs.isSelected()) {
                            return new SimulationPresenter(newStage, true, logsSavingLoc); // Pass Stage to the constructor
                        } else {
                            return new SimulationPresenter(newStage);
                        }
                    } else {
                        try {
                            return param.getDeclaredConstructor().newInstance();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                BorderPane viewRoot = loader.load();
                SimulationPresenter presenter = loader.getController();



                configureStage(newStage, viewRoot);
                newStage.show();
                DarwinSimulationMap simulationMap;
                if(simulationOptions.mapType() == MapTypes.NORMAL_MAP){
                     simulationMap = new DarwinSimulationMap(simulationOptions.simulationWidth(),simulationOptions.simulationHeigth(), ids);
                } else{
                    simulationMap = new DarwinSimulationMapWithWater(simulationOptions.simulationWidth(),simulationOptions.simulationHeigth(), ids);
                }


                ids += 1;


                //add randomness
                var positions = List.of(new Vector2d(4,4), new Vector2d(5,5), new Vector2d(9, 9));
//                var simulation = new Simulation(positions, simulationMap);
            var simulation = new Simulation(simulationOptions, ids);
                presenter.setWorldMap(simulation.getMap());
                presenter.setSimulation(simulation);
                simulations.add(simulation);
                simulationEngine.addToThreadPool(simulation);
            newStage.setOnCloseRequest(event -> {
                simulation.stop();
                });

        } else {
            displayError(ErrorMessages.WRONG_VALUES);
        }

    }

    private void setInterfaceValues(SimulationOptions options){
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
            displayError(ErrorMessages.OPTION_PARSE_ERROR);
            return null;
        }
    }

    public void onSaveConfigClicked(){
        File file = chooseFileForSaveCSV("settings.csv");
        String path = "default.csv";
        if (file != null) {
            path = file.getAbsolutePath();
        }

        SimulationOptionsToFile simulationOptionsToFile = new SimulationOptionsToFile();
        try{
        simulationOptionsToFile.writeOptionsToFile(generateSimulationOptions(), path);
        } catch (IOException e) {
            displayError(ErrorMessages.FILE_WRITE_ERROR);
        }

    }

    private File chooseFileForSaveCSV(String initialName){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(initialName);
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(genomeLength.getScene().getWindow());
        return file;
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
                setInterfaceValues(options);
            } catch (IOException | CsvValidationException e) {
                displayError(ErrorMessages.FILE_READ_ERROR);
            }
        } else{
            displayError(ErrorMessages.FILE_READ_ERROR);
        }



    }

    public void toggleSavingLogs(){
        logsLoc.setVisible(!logsLoc.isVisible());
    }

    public void onSelectLogsSaveClicked(){
        File file = chooseFileForSaveCSV("logs.csv");
        if(file != null){
            logsSavingLoc = file;
        }
    }

    public void displayError(ErrorMessages errorType){
        infolabel.setText(errorType.toString());
    }


    public void close() throws InterruptedException {
        simulationEngine.awaitSimulationEnd();
    }
    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        scene.getStylesheets().add("simulation.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
