package com.runninglight.server;

import com.runninglight.server.communication.ServerCommunicator;
import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.User;

public class ClientProxy implements IClient {

    private static ClientProxy instance;

    private ServerCommunicator communicator = ServerCommunicator.getInstance();

    private static final String CLIENT_FACADE = "com.runninglight.tickettoride.communication.ClientFacade";
    private static final String USER = "com.runninglight.shared.User";
    private static final String GAME = "com.runninglight.shared.Game";

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
            communicator.setCommandForUser(user.getUserName(), getGameAddedCommand(g));
        }
    }

    @Override
    public void addPlayer(User u, Game g) {
        for (User user : ServerModel.getInstance().getUserList())
        {
            communicator.setCommandForUser(user.getUserName(), getPlayerAddedCommand(u, g));
        }
    }

    @Override
    public void removePlayer(User u, Game g){
        for (User user : ServerModel.getInstance().getUserList())
        {
            communicator.setCommandForUser(user.getUserName(), getPlayerRemovedCommand(u, g));
        }
    }

    private Command getGameAddedCommand(Game game)
    {
        return new Command(
                CLIENT_FACADE,
                "addGame",
                new String[] {GAME},
                new Object[] {game} );
    }

    private Command getPlayerAddedCommand(User user, Game game) {
        return new Command(
                CLIENT_FACADE,
                "addPlayer",
                new String[] {USER, GAME},
                new Object[] {user, game} );
    }

    private Command getPlayerRemovedCommand(User user, Game game) {
        return new Command(
                CLIENT_FACADE,
                "removePlayer",
                new String[] {USER, GAME},
                new Object[] {user, game} );
    }
}
