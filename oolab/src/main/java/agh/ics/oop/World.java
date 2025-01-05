package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        try {
            List<MoveDirection> directions = OptionsParser.parseDirection(args);
            List<Simulation> simulations = new ArrayList<>();
            for(int i=0;i<30;i++) {
                List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 1));
                AbstractWorldMap map1 = new GrassField(10);
                AbstractWorldMap map2 = new RectangularMap(7, 7);
                map1.addObserver(new ConsoleMapDisplay());
                map2.addObserver(new ConsoleMapDisplay());
                simulations.add(new Simulation(positions, directions, map1));
                simulations.add(new Simulation(positions, directions, map2));
            }
            /*SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsyncInThreadPool();
            try{
                engine.awaitSimulationsEndForThreadPool();
            }catch(InterruptedException e){
                System.out.println("wątki zostały przerwane przed wykonaniem");
            }*/
            simulations.getFirst().run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("system zakończył działanie");
        //Application.launch(SimulationApp.class,args);

    }

}
