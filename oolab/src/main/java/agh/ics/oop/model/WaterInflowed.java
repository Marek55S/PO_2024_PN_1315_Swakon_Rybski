package agh.ics.oop.model;

public class WaterInflowed extends Water{
    private final int inflowStep;
    public WaterInflowed(Vector2d position, int inflowStep) {
        super(position);
        this.inflowStep = inflowStep;
    }

    public int getInflowStep() {
        return inflowStep;
    }
}
