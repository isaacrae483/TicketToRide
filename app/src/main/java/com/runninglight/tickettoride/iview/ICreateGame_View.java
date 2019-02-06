package com.runninglight.tickettoride.iview;

import com.runninglight.shared.GameInfo;

public interface ICreateGame_View {

    void joinGame(GameInfo gameInfo);
    void cancel();

    void onCreateSuccessful();
}
