package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.StatisticsTracker;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.min;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap worldMap;
    @FXML
    private Label infolabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label animalsCountLabel;
    @FXML
    private Label plantsCountLabel;
    @FXML
    private Label freeFieldsCount;
    @FXML
    private Label mostPopularGenotypes;
    @FXML
    private Label avgEnergyLevelLabel;
    @FXML
    private Label averageLifespan;
    @FXML
    private Label averageKidsAmount;
    @FXML
    private Label animalGenom;
    @FXML
    private Label animalGenomActivated;
    @FXML
    private Label animalEnergy;
    @FXML
    private Label animalChildrenCount;
    @FXML
    private Label animalDescendants;
    @FXML
    private Label animalDaysAlive;
    @FXML
    private VBox singleAnimal;

    private int minY;
    private int maxY;
    private int minX;
    private int maxX;
    private int width;
    private int height;
    private int cellHeight;
    private int cellWidth;
    private Simulation simulation;
    private StatisticsTracker statistics = new StatisticsTracker();
    private Animal selectedAnimal = null;

    private final Stage stage;

    SimulationPresenter(Stage stage) {
        this.stage = stage;
    }

    public void setSimulation(Simulation simulation) {

        this.simulation = simulation;
    }
    public void setWorldMap(AbstractWorldMap map) {
        if (worldMap != null) {
            worldMap.removeObserver(this);
        }

        map.addObserver(this);
//        map.addObserver((worldMap, message) -> Platform.runLater(() -> {
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            System.out.println(dtf.format(now) + " " + message);
//        }));

        worldMap = map;
        statistics = map.getStatistics();
    }

    private void updateStoredConstraints() {
        var boundaries = worldMap.getCurrentBounds();
        minY = boundaries.lowerLeft().getY();
        minX = boundaries.lowerLeft().getX();
        maxY = boundaries.upperRight().getY();
        maxX = boundaries.upperRight().getX();

        width = maxX - minX;
        height = maxY - minY;

        cellWidth = min(200,Math.round((float) stage.getWidth()/ (2*(width + 2))));
        cellHeight = min(200, Math.round((float) stage.getHeight() / (2*(height + 2))));

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void setXYLabel() {
        var label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
    }

    private void setLabelsOx() {
        for (int i = 0; i < width + 1; ++i) {
            var label = new Label(String.format("%d", (minX + i)));
            mapGrid.add(label, i + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
    }

    private void setLabelsOy() {
        for (int i = 0; i < height + 1; ++i) {
            var label = new Label(String.format("%d", (maxY - i)));
            mapGrid.add(label, 0, i + 1);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }
    }

    private void addElementsToMap() {
        int emptyFields = 0;
        for (int i = 0; i <= width; ++i) {
            for (int j = 0; j <= height; ++j) {
                Vector2d positionToCheck = new Vector2d(i + minX, j + minY);
//                    var label = new Label(element.toString());

                if(worldMap instanceof DarwinSimulationMapWithWater && ((DarwinSimulationMapWithWater) worldMap).isWaterAt(positionToCheck)){
                    mapGrid.add(new MapCell(cellWidth, cellHeight).turnToWater(), i + 1, height - j + 1);
                    continue;
                }
                    var mc = new MapCell(cellWidth, cellHeight);
                    if(worldMap.isAnimalAt(positionToCheck)) {
                        Animal animal = (Animal) worldMap.objectAt(positionToCheck).get();
                        mc.addAnimal(false, animal, this);

                        if(animal == selectedAnimal){
                            mc.selectAnimal();
                        }
                    }
                    if(worldMap.isGrassAt(positionToCheck)){
                        mc.addGrass();
                    }

                mapGrid.add(mc, i + 1, height - j + 1);



                    //GridPane.setHalignment(label, HPos.CENTER);

            }
        }

        statistics.setEmptyFieldsCount(emptyFields);



    }

    public void toggleRunning(ActionEvent actionEvent){
        simulation.toggle();
    }

    public void drawMap(String input) {
        clearGrid();
        updateStoredConstraints();
        setXYLabel();
        setLabelsOx();
        setLabelsOy();
        addElementsToMap();
        mapGrid.setPrefSize(stage.getX()/2, stage.getY()/2);
        infolabel.setText(input);
    }

    public void updateStatistics(){
        animalsCountLabel.setText(String.format("Animals count: %d", statistics.getAnimalsCount()));
        plantsCountLabel.setText(String.format("Plants count: %d", statistics.getGrassCount()));
        freeFieldsCount.setText(String.format("Free Fields: %d", statistics.getEmptyFieldsCount()));
        mostPopularGenotypes.setText(String.format("Most popular genotypes: "));
        avgEnergyLevelLabel.setText(String.format("Avg energy level: %d", statistics.getAverageEnergyLevel()));
        averageLifespan.setText(" ");
        averageKidsAmount.setText(" ");
    }

    public void updateSingleAnimalStatistics(){
        if(selectedAnimal != null){
            singleAnimal.setVisible(true);
            animalEnergy.setText(String.format("Animals energy: %d", selectedAnimal.getEnergy()));
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap(message);
            updateStatistics();
            updateSingleAnimalStatistics();
        });

    }

    public void setSelectedAnimal(Animal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
    }
}
