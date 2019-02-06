package com.runninglight.shared;

import java.util.ArrayList;

public interface IServer {
    boolean login(LoginInfo loginInfo);

    boolean register(LoginInfo loginInfo);

    boolean createGame(GameInfo gameInfo);

    boolean joinGame(User user);

    ArrayList<Game> getGameList();
}
