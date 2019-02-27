package com.runninglight.shared;

public class City {
    private String name;

    public City(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replaceAll("_", " ");
    }
}
