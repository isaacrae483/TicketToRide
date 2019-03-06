package com.runninglight.server;

import com.runninglight.server.communication.ServerCommunicator;
import com.runninglight.shared.Command;
import com.runninglight.shared.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.User;

public class ClientProxy implements IClient {

    private static ClientProxy instance;

    private ServerCommunicator communicator = ServerCommunicator.getInstance();

    private static final String CLIENT_FACADE = "com.runninglight.tickettoride.communication.ClientFacade";
    private static final String USER = "com.runninglight.shared.User";
    private static final String GAME = "com.runninglight.shared.Game";
    private static final String MESSAGE = "com.runninglight.shared.Message";
    private static final String DEST_CARD_ARRAY = "[Lcom.runninglight.shared.DestinationCard;";
    private static final String PLAYER = "com.runninglight.shared.Player";


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

    @Override
    public void broadcastMessage(Message message, Game game)
    {
        for (User user : ServerModel.getInstance().getUserList())
        {
            communicator.setCommandForUser(user.getUserName(), getBroadcastMessageCommand(message, game));
        }
    }

    @Override
    public void setDestinationCards(Game g, Player p){
        communicator.setCommandForGame(g, getSetDestinationCardsCommand(g, p));
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

    private Command getBroadcastMessageCommand(Message message, Game game)
    {
        return new Command(
                CLIENT_FACADE,
                "broadcastMessage",
                new String[] {MESSAGE, GAME},
                new Object[] {message, game} );
    }

    private Command getSetDestinationCardsCommand(Game game, Player p)
    {
        return new Command(
                CLIENT_FACADE,
                "setDestinationCards",
                new String[] {GAME, PLAYER},
                new Object[] {game, p} );
    }
}
