package com.runninglight.shared;

public interface IClient {

    void addPlayer(User u, Game g);

    void removePlayer(User u, Game g);

    void addGame(Game g);

    void broadcastMessage(Message message, Game game);

    void setDestinationCards(Game g, Player p);
}
