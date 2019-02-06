package com.runninglight.server;

import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.User;

public class ClientProxy implements IClient {
    @Override
    public void addGame(Game g) {
        ServerModel.getInstance().addGame(g);
    }

    @Override
    public void addPlayer(User u) {
        ServerModel.getInstance().addUser(u);
    }
}
