package com.runninglight.server.communication;

import com.runninglight.shared.Command;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

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

    public void startServer(int port)
    {
        try
        {
            commandManager = new CommandManager();
            InetSocketAddress address = new InetSocketAddress(port);
            httpServer = HttpServer.create(address, 10);
            httpServer.setExecutor(null);

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
