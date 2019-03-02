package com.runninglight.shared;

public class City {
    private String name;

    public City(String name) {
        setName(name);
    }

    public String getName() {
        return name.replace('_', ' ');
    }

    public void setName(String name) {
        this.name = name;
    }
}
