package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowPresenter {
    //private final List<SimulationPresenter> presenters = new ArrayList<>();
    private final List<Simulation> simulations = new ArrayList<>();
    private final SimulationEngine simulationEngine = new SimulationEngine(simulations);
    @FXML
    private TextField moveslisttextfield;
    @FXML
    private Label infolabel;
    private int ids = 0;

    public void onSimulationStartClicked(ActionEvent actionEvent) throws IOException {

        if (!moveslisttextfield.getText().strip().isEmpty()) {
            try {
                List<MoveDirection> moves = OptionsParser.parseOptions(moveslisttextfield.getText().split(" "));
                List<Vector2d> positions = List.of(new Vector2d(1, 1));

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
                BorderPane viewRoot = loader.load();
                SimulationPresenter presenter = loader.getController();
                //presenters.add(presenter);

                Stage stage = new Stage();
                configureStage(stage, viewRoot);
                stage.show();

                var grassField = new GrassField(2, ids);
                ids += 1;
                presenter.setWorldMap(grassField);
                Simulation simulation = new Simulation(positions, moves, grassField);
                simulations.add(simulation);
                simulationEngine.addToThreadPool(simulation);
            } catch (IllegalArgumentException e) {
                infolabel.setText("This moves combination is invalid");
            }
        } else {
            infolabel.setText("Moves list shouldn't be empty");
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
