package com.runninglight.tickettoride.communication;

import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.Message;
import com.runninglight.shared.User;

public class ClientFacade implements IClient {

    private ClientModel model = ClientModel.getInstance();

    @Override
    public void addPlayer(User u, Game g) {
        model.addUserToGame(u, g);
    }

    @Override
    public void removePlayer(User u, Game g){
        model.removeUserFromGame(u, g);
    }

    @Override
    public void addGame(Game g) {
        model.addGame(g);
    }

    @Override
    public void broadcastMessage(Message message, Game game)
    {
        model.addMessage(message, game);
    }
}
