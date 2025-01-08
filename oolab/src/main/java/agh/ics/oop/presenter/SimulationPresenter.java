package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap worldMap;
    @FXML
    private Label infolabel;
    @FXML
    private GridPane mapGrid;

    private int minY;
    private int maxY;
    private int minX;
    private int maxX;
    private int width;
    private int height;
    private int cellHeight;
    private int cellWidth;


    public void setWorldMap(AbstractWorldMap map) {
        if (worldMap != null) {
            worldMap.removeObserver(this);
        }

        map.addObserver(this);
        map.addObserver((worldMap, message) -> Platform.runLater(() -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now) + " " + message);
        }));

        worldMap = map;
    }

    private void updateStoredConstraints() {
        var boundaries = worldMap.getCurrentBounds();
        minY = boundaries.lowerLeft().getY();
        minX = boundaries.lowerLeft().getX();
        maxY = boundaries.upperRight().getY();
        maxX = boundaries.upperRight().getX();

        width = maxX - minX;
        height = maxY - minY;

        cellWidth = Math.round((float) 300 / (width + 2));
        cellHeight = Math.round((float) 300 / (height + 2));


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
//        for (var element : worldMap.getElements()) {
//            if (worldMap.isOccupied(element.getPosition())) {
//                var label = new Label(element.toString());
//                var pos = element.getPosition();
//                mapGrid.add(label, pos.getX() - minX + 1, maxY - pos.getY() + 1);
//                GridPane.setHalignment(label, HPos.CENTER);
//            }
//        }
        for (int i = 0; i <= width; ++i) {
            for (int j = 0; j <= height; ++j) {
                Vector2d positionToCheck = new Vector2d(i + minX, j + minY);
                if (worldMap.isOccupied(positionToCheck)) {
                    WorldElement element = worldMap.objectAt(positionToCheck).get();
                    var label = new Label(element.toString());
                    System.out.println(label);
                    //mapGrid.add(label, positionToCheck.getX() - minX + 1, maxY - positionToCheck.getY() + 1);
                    mapGrid.add(new WorldElementBox(element), i + 1, height - j + 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }

    public void drawMap(String input) {
        clearGrid();
        updateStoredConstraints();
        setXYLabel();
        setLabelsOx();
        setLabelsOy();
        addElementsToMap();
        mapGrid.setPrefSize(300, 300);
        infolabel.setText(input);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap(message);
        });

    }

}
