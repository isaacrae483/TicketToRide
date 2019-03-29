package com.runninglight.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Map stores the routes and cities for the Ticket To Ride Game, and allows for
 * players to claim these routes. The Map is initialized at the beginning of the game.
 */
public class Map {
    /**
     * allRoutes holds the list of all routes on the Ticket To Ride Map
     */
    private ArrayList<Route> allRoutes;

    /**
     * allCities holds the list of all cities on the Ticket To Ride Map
     */
    private ArrayList<City> allCities;

    /**
     * The number of routes is 100
     */
    private static final int TOTAL_ROUTES = 100;

    /**
     * The number of cities is 36
     */
    private static final int TOTAL_CITIES = 36;

    /**
     * Constructor for the Map class. When a Map is instantiated, the routes and
     * the cities are initialized (at the beginning of the game)
     * @pre none
     * @post allRoutes initialized correctly (allRoutes.size() == 100)
     * @post allCities initialized correctly (allCities.size() == 36)
     * @post Initialized Map ready for gameplay: no routes claimed
     */
    public Map(InputStream fileRoute){
        initializeRoutes(fileRoute);
        //initializeCities(fileCity);
    }

    /**
     * This function is used by the constructor to initialize the routes from the
     * text file routes.txt. This text file is formatted correctly with all route
     * information necessary for initialization
     * @pre none
     * @post allRoutes initialized correctly (allRoutes.size() == 100)
     * @post no routes are claimed
     * @exception RuntimeException if an error occurs while creating the routes
     */
    private void initializeRoutes(InputStream file){

            allRoutes = new ArrayList<>();
            /*
            String filePath = System.getProperty("user.dir") + File.separator +
                    "app\\src\\main\\assets\\routes.txt";
            File file = new File(filePath);
            */


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

    /**
     * This function is used by the constructor to initialize the cities from the
     * text file cities.txt. This text file is formatted correctly with all city
     * information necessary for initialization
     * @pre none
     * @post allCities initialized correctly (allCities.size() == 36)
     * @exception RuntimeException if an error occurs while creating the routes
     */
    private void initializeCities(File file){
        try {
            allCities = new ArrayList<>();
            /*
            String filePath = System.getProperty("user.dir") + File.separator +
                    "app\\src\\main\\assets\\cities.txt";
            File file = new File(filePath);
            */
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

    /**
     * The claimRoute function allows a player in the Ticket to Ride game to claim
     * a route on the map
     * @param routeIndex - the index of the route (in allRoutes) to be claimed
     * @param p - the Player who will be claiming the route
     * @pre allRoutes must have been initialized previously
     * @pre 0 <= routeIndex < 100 (must be a valid index in allRoutes)
     * @pre p must be a valid player in the game
     * @post the route at routeIndex in allRoutes will be marked as claimed by Player p
     */
    public void claimRoute(int routeIndex, Player p) {
        allRoutes.get(routeIndex).setClaimed(p);
    }

    /**
     * getCity returns the index of a specific City in the allCities list
     * @param c - the City to search for
     * @return - the index of the City in the list
     * @pre c must be a valid City in the Ticket to Ride game
     * @pre allCities must have been initialized previously
     * @post getCity will return the index of c in allCities
     */
    public int getCity(City c) {
        for (int i = 0; i < allCities.size(); ++i) {
            if (allCities.get(i).equals(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * getRouteIndex returns the index of a specific Route in the allRoutes list
     * @param r - the Route to search for
     * @return - the index of the Route in the list
     * @pre r must be a valid Route in the Ticket to Ride game
     * @pre allRoutes must have been initialized previously
     * @post getRouteIndex will return the index of r in allRoutes
     */
    public int getRouteIndex(Route r) {
        for (int i = 0; i < allRoutes.size(); ++i) {
            if (allRoutes.get(i).equals(r)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Route> routesWithCity(City c) {
        ArrayList<Route> routesToVisit = new ArrayList<>();
        for (Route r : allRoutes) {
            if (r.getCity1().equals(c) || r.getCity2().equals(c)) {
                routesToVisit.add(r);
            }
        }
        return routesToVisit;
    }

    public Route[] findRoutes(String cityName){
        ArrayList <Route> routes = new ArrayList<>();

        for(int i=0; i< allRoutes.size(); i++){
            if( (allRoutes.get(i).getCity1Name().equals(cityName) )||( allRoutes.get(i).getCity2Name().equals(cityName)) )
                allRoutes.get(i).setRouteNum(i);
                routes.add(allRoutes.get(i));
        }

        return (Route[]) routes.toArray();
    }

    public File passFile(File file){
        return file;
    }

}
