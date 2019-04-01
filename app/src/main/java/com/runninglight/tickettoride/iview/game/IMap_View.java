package com.runninglight.tickettoride.iview.game;

import android.graphics.Bitmap;

import com.runninglight.shared.PlayerColor;
import com.runninglight.shared.Route;

import java.util.ArrayList;

public interface IMap_View {
    void claimRoute(int route, PlayerColor color);
    void refresh(ArrayList<Route> allRoutes);
    void enableListeners();
    void disableListeners();
}
