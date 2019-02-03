package com.runninglight.shared;

public interface IServer {
    void login(LoginInfo loginInfo);

    void register(LoginInfo loginInfo);

    void createGame(GameInfo gameInfo);

    void joinGame(User user);
}
