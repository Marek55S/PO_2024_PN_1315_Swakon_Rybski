import agh.ics.oop.SimulationApp;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        try {
            Application.launch(SimulationApp.class, args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
