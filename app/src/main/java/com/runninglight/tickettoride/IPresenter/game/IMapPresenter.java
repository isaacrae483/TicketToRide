package com.runninglight.tickettoride.IPresenter.game;

public interface IMapPresenter {
    void touchMap(int x, int y);
    void claimRoute(int route,String color);
    void viewCity(String cityName);
}
