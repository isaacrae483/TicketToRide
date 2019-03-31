package com.runninglight.tickettoride.iview.game;

public interface IClaimRouteActivityView {
    void showToast(String message);
    void endActivity();
    void launchGrey(String route, int routeNum);
}
