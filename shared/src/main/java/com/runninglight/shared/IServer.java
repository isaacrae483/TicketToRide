package com.runninglight.shared;

public interface IServer {
    boolean login(LoginInfo loginInfo);

    boolean register(LoginInfo loginInfo);

    boolean createGame(GameInfo gameInfo);

    boolean joinGame(User user);
}
