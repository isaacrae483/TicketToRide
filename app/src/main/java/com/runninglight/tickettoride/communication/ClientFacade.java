package com.runninglight.tickettoride.communication;

import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.User;

public class ClientFacade implements IClient {
    @Override
    public void addPlayer(User u) {
        ClientModel.getInstance().addUser(u);
    }

    @Override
    public void addGame(Game g) {
        ClientModel.getInstance().addGame(g);
    }
}
