package com.runninglight.tickettoride.IPresenter.game;

import com.runninglight.shared.PlayerColor;

import java.util.Observer;

public interface IMapPresenter extends Observer {
    void touchMap(int x, int y);
    void claimRoute(int route,PlayerColor color);
    void viewCity(String cityName);
    void checkIfMyTurn();
}
