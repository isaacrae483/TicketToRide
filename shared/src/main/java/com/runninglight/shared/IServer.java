package com.runninglight.shared;

import java.util.ArrayList;

public interface IServer {
    boolean login(LoginInfo loginInfo);

    boolean register(LoginInfo loginInfo);

    boolean createGame(GameInfo gameInfo);

    boolean joinGame(User user, Game game);

    boolean leaveGame(User user, Game game);

    Game[] getGameList();

    DestinationCard[] drawDestCards(String gameID, int numCards);

    void returnDestCards(String gameID, DestinationCard[] cards);
}
