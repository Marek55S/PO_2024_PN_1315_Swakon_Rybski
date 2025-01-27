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
    private  boolean hasAnimal;
    private Rectangle bg;
    private Circle animal;
    private Rectangle grass;
    private Rectangle energyBar;
    public MapCell(int width, int heigth) {
        this.width = width;
        this.height = heigth;
        this.hasAnimal = false;

        this.bg = new Rectangle(width, height);
        this.bg.setFill(Color.LIGHTGREEN);
        this.getChildren().add(bg);

        this.grass = new Rectangle(width / 2.0, height / 2.0, Color.DARKGREEN);
        this.grass.setVisible(false);
        this.getChildren().add(grass);

        this.animal = new Circle(width / 5.0, Color.BROWN);
        this.animal.setVisible(false);
        this.getChildren().add(this.animal);

        this.energyBar = new Rectangle(width, height / 10.0, Color.GREEN);
        energyBar.setVisible(false);
        this.getChildren().add(energyBar);

        this.setAlignment(energyBar, Pos.BOTTOM_CENTER);

    }

    public MapCell turnToWater(){
        this.bg.setVisible(false);
        this.bg.setFill(Color.BLUE);
        this.bg.setVisible(true);
        return this;
    }

    public MapCell addGrass(){
        this.grass.setVisible(true);
        return this;
    }

    public MapCell addAnimal(boolean higlightAnimal, Animal animal, SimulationPresenter sim){
        Color animalColor = higlightAnimal ? Color.YELLOW : Color.BROWN;

        Color energyColor = Color.GREEN;

        if(animal.getEnergy() < animal.getNewbornsEnergy()){
            energyColor = Color.YELLOW;
        }

        if(animal.getEnergy() < animal.getNewbornsEnergy()/2){
            energyColor = Color.RED;
        }
        this.animal.setOnMouseClicked(mouseEvent -> {sim.setSelectedAnimal(animal);
            this.animal.setFill(Color.YELLOW);});

        this.animal.setVisible(true);
        this.animal.setFill(animalColor);
        this.hasAnimal = true;
        this.energyBar.setVisible(true);
        this.energyBar.setFill(energyColor);

        return this;
    }

    public MapCell selectAnimal(){
        animal.setFill(Color.YELLOW);
        return this;
    }

    public MapCell higlightAnimal(){
        animal.setFill(Color.RED);
        return this;
    }

    public MapCell higlightCell(){
        this.bg.setVisible(false);
        this.bg.setFill(Color.LIGHTCORAL);
        this.bg.setVisible(true);
        return this;
    }

    public MapCell reset(){
        this.hasAnimal = false;
        this.bg.setFill(Color.LIGHTGREEN);
        this.animal.setVisible(false);
        this.energyBar.setVisible(false);
        this.grass.setVisible(false);
        return  this;
    }


}
