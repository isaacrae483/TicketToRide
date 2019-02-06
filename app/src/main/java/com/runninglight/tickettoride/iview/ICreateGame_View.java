package com.runninglight.tickettoride.iview;

import com.runninglight.shared.GameInfo;

public interface ICreateGame_View {
    void createGame(GameInfo gameInfo);
    void joinGame(GameInfo gameInfo);

    void onCreateSuccessful();
}
