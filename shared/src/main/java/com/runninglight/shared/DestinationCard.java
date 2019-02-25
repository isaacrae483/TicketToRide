package com.runninglight.shared;

import java.util.ArrayList;
import java.util.Arrays;

public class DestinationCard {

    private boolean isComplete;

    private int points;

    private ArrayList<City> cities;

    public DestinationCard(City start, City end){
        cities = new ArrayList<>(Arrays.asList(start, end));
    }

    public DestinationCard(City start, City end, int points){
        cities = new ArrayList<>(Arrays.asList(start, end));
        this.points = points;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<City> getCities() {
        return cities;
    }
}
