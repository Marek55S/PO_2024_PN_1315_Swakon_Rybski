package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> threadList = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        simulationsList = simulations;
    }

    public void runSync(){

        for(Simulation simulation : simulationsList){
            simulation.run();
        }
    }

    public void runAsync(){
        for (Simulation simulation : simulationsList) {
            Thread newThread = new Thread(simulation);
            threadList.add(newThread);
            newThread.start();
        }
    }

    public void awaitSimulationsEndForRunAsync() throws InterruptedException {
        for(int i=0;i<simulationsList.size();i++){
            threadList.get(i).join();
        }
    }

    public void awaitSimulationsEndForThreadPool() throws InterruptedException{
        if(!threadPool.awaitTermination(60, TimeUnit.SECONDS)){
            System.out.println("Wątki zostały zakończone przed wykonaniem wszystkich zadań");
            threadPool.shutdownNow();
        }
    }

    public void runAsyncInThreadPool(){
        for(Simulation simulation: simulationsList){
            threadPool.submit(simulation);
        }
        threadPool.shutdown();
    }

}
