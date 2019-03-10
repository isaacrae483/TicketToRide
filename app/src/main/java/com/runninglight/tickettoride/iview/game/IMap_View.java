package com.runninglight.tickettoride.iview.game;

import android.graphics.Bitmap;

import com.runninglight.shared.PlayerColor;

public interface IMap_View {
    void claimRoute(int route, PlayerColor color);
    void refresh();

}
