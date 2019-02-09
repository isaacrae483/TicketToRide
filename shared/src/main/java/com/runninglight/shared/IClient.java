package com.runninglight.shared;

public interface IClient {

    void addPlayer(User u, Game g);

    void removePlayer(User u, Game g);

    void addGame(Game g);
}
