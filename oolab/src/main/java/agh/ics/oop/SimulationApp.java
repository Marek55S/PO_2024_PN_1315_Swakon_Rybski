package agh.ics.oop;

import agh.ics.oop.presenter.MainWindowPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("mainWindow.fxml"));
        loader.setControllerFactory(param -> {
            if (param == MainWindowPresenter.class) {
                return new MainWindowPresenter(primaryStage); // Pass Stage to the constructor
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) { // catch co?
                    throw new RuntimeException(e);
                }
            }
        });
        BorderPane viewRoot = loader.load();
        MainWindowPresenter presenter = loader.getController();
        configureStage(primaryStage, viewRoot);
//        presenter.initialize();
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            try {
                presenter.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // czy to dobry wybór?
            }
            // Save file
        });


    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
