package com.runninglight.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private ArrayList<Route> allRoutes;

    private ArrayList<City> allCities;

    private static final int TOTAL_ROUTES = 100;
    private static final int TOTAL_CITIES = 36;

    public Map(){
        initializeRoutes();
        initializeCities();
    }

    private void initializeRoutes(){
        try {
            allRoutes = new ArrayList<>();
            String filePath = System.getProperty("user.dir") + File.separator +
                    "shared\\src\\main\\java\\com\\runninglight\\shared\\routes.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Loop should only run 100 times to make 100 routes
            for(int i = 0; i < TOTAL_ROUTES; i++){
                City city1 = new City(scanner.next());
                City city2 = new City(scanner.next());
                String color = scanner.next();
                int length = Integer.parseInt(scanner.next());
                String type = scanner.next();
                Route route = new Route(city1, city2, color, length, type);
                allRoutes.add(route);
            }
            System.out.println(allRoutes.toString());
        }
        catch(FileNotFoundException e){
            // Throws an exception that the Command will catch to return a false success
            throw new RuntimeException("File doesn't exist");
        }
        catch(Exception e){
            // Throws an exception that the Command will catch to return a false success
            throw new RuntimeException("Failed to create all routes");
        }

    }

    private void initializeCities(){
        try {
            allCities = new ArrayList<>();
            String filePath = System.getProperty("user.dir") + File.separator +
                    "shared\\src\\main\\java\\com\\runninglight\\shared\\cities.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Loop should only run 36 times to make 36 routes
            for(int i = 0; i < TOTAL_CITIES; i++){
                City city = new City(scanner.next());
                allCities.add(city);
            }
            System.out.println(allCities.toString());
        }
        catch(FileNotFoundException e){
            // Throws an exception that the Command will catch to return a false success
            throw new RuntimeException("File doesn't exist");
        }
        catch(Exception e){
            // Throws an exception that the Command will catch to return a false success
            throw new RuntimeException("Failed to create all cities");
        }

    }

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
