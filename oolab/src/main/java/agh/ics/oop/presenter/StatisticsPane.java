package agh.ics.oop.presenter;

public class StatisticsPane {
    private int animalsCount = 0;
    private int grassCount = 0;
    private int emptyFieldsCount = 0;

    public int getAnimalsCount() {
        return animalsCount;
    }

    public void setAnimalsCount(int animalsCount) {
        this.animalsCount = animalsCount;
    }

    public int getGrassCount() {
        return grassCount;
    }

    public void setGrassCount(int grassCount) {
        this.grassCount = grassCount;
    }

    public int getEmptyFieldsCount() {
        return emptyFieldsCount;
    }

    public void setEmptyFieldsCount(int emptyFieldsCount) {
        this.emptyFieldsCount = emptyFieldsCount;
    }
}
