package com.runninglight.shared;

public class City implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != City.class) {
            return false;
        }
        City c = (City) o;
        return c.getName().equals(this.getName());
    }
}
