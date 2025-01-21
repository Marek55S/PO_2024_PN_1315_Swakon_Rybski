package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
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
    private final boolean hasGrass;
    private Rectangle bg;
    private Circle animal;
    private Rectangle grass;
    public MapCell(int width, int heigth, boolean hasGrass) {
        this.width = width;
        this.height = heigth;
        this.hasAnimal = false;
        this.hasGrass = hasGrass;

        bg = new Rectangle(width, height);
        bg.setFill(Color.LIGHTGREEN);
        this.getChildren().add(bg);

        if(hasGrass) {
            grass = new Rectangle(width / 2.0, height / 2.0, Color.DARKGREEN);
            this.getChildren().add(grass);
        }



    }
    public MapCell(int width, int heigth, boolean hasGrass, boolean higlightAnimal, int animalEnergy) {
        this.width = width;
        this.height = heigth;
        this.hasAnimal = true;
        this.hasGrass = hasGrass;

        bg = new Rectangle(width, height);
        bg.setFill(Color.LIGHTGREEN);
        this.getChildren().add(bg);

        if(hasGrass) {
            grass = new Rectangle(width / 2.0, height / 2.0, Color.DARKGREEN);
            this.getChildren().add(grass);
        }

        Color animalColor = higlightAnimal ? Color.YELLOW : Color.BROWN;
        this.animal = new Circle(width / 5.0, animalColor);
        this.getChildren().add(this.animal);


    }

}
