package com.runninglight.tickettoride.IPresenter.game;

import java.util.Observer;

public interface IClaimRoutePresenter {
    void claimRoute(int routeNumber);
    void claimGreyRoute(int routeNumber, String color);
}
