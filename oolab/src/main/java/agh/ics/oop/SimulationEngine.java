package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final List<Simulation> simulations;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() {
        for (Simulation simulation : simulations) {
            simulation.run();
        }
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulations) {
            threadPool.submit(new Thread(simulation));
        }
    }

    public void addToThreadPool(Simulation simulation) {
        threadPool.submit(new Thread(simulation));
    }

    public void awaitSimulationEnd() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
        threadPool.shutdown();
        if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            threadPool.shutdownNow();
        }
    }
}
