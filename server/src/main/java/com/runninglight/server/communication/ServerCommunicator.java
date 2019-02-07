package com.runninglight.server.communication;

import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.Serializer;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class ServerCommunicator
{
    private static final String PATH_COMMAND = "/command";
    private static final String PATH_POLL = "/poll";

    private static ServerCommunicator instance;

    private HttpServer httpServer;
    private CommandManager commandManager;

    public static ServerCommunicator getInstance()
    {
        if (instance == null) instance = new ServerCommunicator();
        return instance;
    }

    public static void main(String[] args)
    {
        Game game1 = new Game("game1", 2);
        Game game2 = new Game("game2", 2);
        //Game[] games = { game1, game2 };
        ArrayList<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);
        String json = new Serializer().serialize(games);
        System.out.println(json);
        ArrayList<Game> gamesDeserialized = (ArrayList<Game>) new Serializer().deserializeObject(json, games.getClass().getName());
        //Game[] gamesDeserialized = (Game[]) new Serializer().deserializeObject(json, games.getClass().getName());
        for (Game game : gamesDeserialized) System.out.println(game.getGameName());

    }

    public void startServer(int port)
    {
        try
        {
            commandManager = new CommandManager();
            InetSocketAddress address = new InetSocketAddress(port);
            httpServer = HttpServer.create(address, 10);
            httpServer.setExecutor(null);

            // Handler declaration
            httpServer.createContext(PATH_COMMAND, new CommandHandler());
            httpServer.createContext(PATH_POLL, new PollHandler());

            httpServer.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setCommandForUser(String userId, Command command)
    {
        commandManager.setCommand(userId, command);
    }

    public Command getCommandForUser(String userId)
    {
        return commandManager.getCommand(userId);
    }
}
