package com.runninglight.shared;

public class Route {

    public Route(City c1, City c2, String col, int len, String t) {
        city1 = c1;
        city2 = c2;
        calculateColor(col);
        length = len;
        calculatePoints();
        type = t;
    }

    private RouteColor color;

    private int length = 0;

    private Player claimed = null;

    private int points = 0;

    private String type; //single or double

    private City city1;

    private City city2;

    private int routeNum;

    public void setClaimed(Player p) {
        claimed = p;
    }

    public Player getClaimed() {
        return claimed;
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    private void calculateColor(String col) {
        //PINK, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, GREY
        if (col.equals("pink")) {
            color = RouteColor.PINK;
        }
        else if (col.equals("white")) {
            color = RouteColor.WHITE;
        }
        else if (col.equals("blue")) {
            color = RouteColor.BLUE;
        }
        else if (col.equals("yellow")) {
            color = RouteColor.YELLOW;
        }
        else if (col.equals("orange")) {
            color = RouteColor.ORANGE;
        }
        else if (col.equals("black")) {
            color = RouteColor.BLACK;
        }
        else if (col.equals("red")) {
            color = RouteColor.RED;
        }
        else if (col.equals("green")) {
            color = RouteColor.GREEN;
        }
        else if (col.equals("grey")) {
            color = RouteColor.GREY;
        }
        else {
            color = null;
        }
    }

    public void setRouteNum(int num){
        routeNum = num;
    }

    private void calculatePoints() {
        if (length == 1) {
            points = 1;
        }
        else if (length == 2) {
            points = 2;
        }
        else if (length == 3) {
            points = 4;
        }
        else if (length == 4) {
            points = 7;
        }
        else if (length == 5) {
            points = 10;
        }
        else if (length == 6) {
            points = 15;
        }
        else {
            points = 0;
        }
    }

    public String toString(){
        return city1.getName()+ " to " + city2.getName();
    }

    public String getCity1Name(){return city1.getName();}

    public String getCity2Name(){return city2.getName();}

    public int getLength(){return length;}
}
