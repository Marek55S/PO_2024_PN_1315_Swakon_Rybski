package agh.ics.oop;

import agh.ics.oop.model.DarwinSimulationMap;

import java.util.ArrayList;
import java.util.List;

public class StatisticsTracker {
    private int animalsCount = 0;
    private int grassCount = 0;
    private int emptyFieldsCount = 0;
    private List<List<Integer>> mostPopularGenomes = new ArrayList<List<Integer>>();
    private int averageEnergyLevel = 0;
    private int averageLifespan = 0;
    private int averageKidsAmount = 0;
    private int day = 0;


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

    public List<List<Integer>> getMostPopularGenomes() {
        return mostPopularGenomes;
    }

    public void setMostPopularGenomes(List<List<Integer>> mostPopularGenomes) {
        this.mostPopularGenomes = mostPopularGenomes;
    }

    public int getAverageEnergyLevel() {
        return averageEnergyLevel;
    }

    public void setAverageEnergyLevel(int averageEnergyLevel) {
        this.averageEnergyLevel = averageEnergyLevel;
    }

    public int getAverageLifespan() {
        return averageLifespan;
    }

    public void setAverageLifespan(int averageLifespan) {
        this.averageLifespan = averageLifespan;
    }

    public int getAverageKidsAmount() {
        return averageKidsAmount;
    }

    public void setAverageKidsAmount(int averageKidsAmount) {
        this.averageKidsAmount = averageKidsAmount;
    }

    public String genomeToString(List<Integer> genome) {
        String tmp = "[";

        for(Integer i : genome) {
            tmp = tmp + i + ".";
        }

        tmp += "]";

        return tmp;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
