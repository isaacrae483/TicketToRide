package com.runninglight.tickettoride.IPresenter.game;

import java.util.Observer;

public interface IClaimRoutePresenter extends Observer {
    void claimRoute(int routeNumber);
    void initObserver();
    void removeObserver();
}
