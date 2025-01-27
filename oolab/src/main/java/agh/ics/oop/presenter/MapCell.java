package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.util.concurrent.RecursiveAction;

public class MapCell extends StackPane {
    private final int width;
    private final int height;
    private final boolean hasAnimal;
    private Rectangle bg;
    private Circle animal;
    private Rectangle grass;

    public MapCell(int width, int heigth) {
        this.width = width;
        this.height = heigth;
        this.hasAnimal = false;

        bg = new Rectangle(width, height);
        bg.setFill(Color.LIGHTGREEN);
        this.getChildren().add(bg);


    }

    public MapCell turnToWater() {
        this.bg.setFill(Color.BLUE);
        return this;
    }

    public MapCell addGrass() {
        grass = new Rectangle(width / 2.0, height / 2.0, Color.DARKGREEN);
        this.getChildren().add(grass);
        return this;
    }

    public MapCell addAnimal(boolean higlightAnimal, Animal animal, SimulationPresenter sim) {
        Color animalColor = higlightAnimal ? Color.YELLOW : Color.BROWN;
        this.animal = new Circle(width / 5.0, animalColor);
        this.animal.setOnMouseClicked(mouseEvent -> {
            sim.setSelectedAnimal(animal);
            System.out.println("Someone touched meeeee");
            this.animal.setFill(Color.YELLOW);
        });
        this.getChildren().add(this.animal);

        Color energyColor = Color.GREEN;

        if (animal.getEnergy() < animal.getNewbornsEnergy()) {
            energyColor = Color.YELLOW;
        }

        if (animal.getEnergy() < animal.getNewbornsEnergy() / 2) {
            energyColor = Color.RED;
        }

        Rectangle energyBar = new Rectangle(width, height / 10.0, energyColor);

        this.getChildren().add(energyBar);

        this.setAlignment(energyBar, Pos.BOTTOM_CENTER);


        return this;
    }

    public MapCell selectAnimal() {
        animal.setFill(Color.YELLOW);
        return this;
    }

    public MapCell higlightAnimal() {
        animal.setFill(Color.RED);
        return this;
    }

    public MapCell higlightCell() {
        bg.setFill(Color.LIGHTCORAL);
        return this;
    }


}
