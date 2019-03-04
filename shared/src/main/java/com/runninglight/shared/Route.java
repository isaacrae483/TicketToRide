package com.runninglight.shared;

import java.util.ArrayList;

public class Route {

    // enum color

    private int length = 0;

    private Player claimed = null;

    private int points = 0;

    private ArrayList<City> connectsCities;

    public void setClaimed(Player p) {
        claimed = p;
    }

    public Player getClaimed() {
        return claimed;
    }
}
