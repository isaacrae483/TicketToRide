package com.runninglight.server;

import com.runninglight.server.communication.ServerCommunicator;
import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.User;

public class ClientProxy implements IClient {

    private static ClientProxy instance;

    public static ClientProxy getInstance() {
        if (instance == null) {
            instance = new ClientProxy();
        }
        return instance;
    }

    @Override
    public void addGame(Game g)
    {
        for (User user : ServerModel.getInstance().getUserList())
        {
            ServerCommunicator.getInstance().setCommandForUser(user.getUserName(), getGameAddedCommand(g));
        }
    }

    @Override
    public void addPlayer(User u) {
        ServerModel.getInstance().addUser(u);
    }

    private Command getGameAddedCommand(Game game)
    {
        return new Command(
                "com.runninglight.tickettoride.communication.ClientFacade",
                "addGame",
                new String[] {"com.runninglight.shared.Game"},
                new Object[] {game} );
    }
}
