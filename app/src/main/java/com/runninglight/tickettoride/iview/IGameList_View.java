package com.runninglight.tickettoride.iview;

import com.runninglight.shared.GameInfo;

public interface IGameList_View {

    void createGame();

    void joinGameSuccessful(GameInfo gameInfo);
    void joinGameFailed();
    void refresh();
}
