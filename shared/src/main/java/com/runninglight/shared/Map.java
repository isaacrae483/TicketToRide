package com.runninglight.shared;

import java.util.ArrayList;

public class Map {
    private ArrayList<Route> allRoutes;

    private ArrayList<City> allCities;

    public void claimRoute(int routeIndex, Player p) {
        allRoutes.get(routeIndex).setClaimed(p);
    }

    public int getCity(City c) {
        for (int i = 0; i < allCities.size(); ++i) {
            if (allCities.get(i).equals(c)) {
                return i;
            }
        }
        return -1;
    }

    public int getRouteIndex(Route r) {
        for (int i = 0; i < allRoutes.size(); ++i) {
            if (allRoutes.get(i).equals(r)) {
                return i;
            }
        }
        return -1;
    }

    boolean playerConnects(City city1, City city2) {
        return false;
    }
}
