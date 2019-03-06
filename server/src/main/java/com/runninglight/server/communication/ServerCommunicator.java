package com.runninglight.server.communication;

import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.Message;
import com.runninglight.shared.MessageCompare;
import com.runninglight.shared.Serializer;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;

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
        Message message = new Message("userName", "1");
        message.timestampMessage();
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Message message1 = new Message("userName1", "2");
        message1.timestampMessage();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message);
        Collections.sort(messages, new MessageCompare());
        for (Message m : messages) System.out.println(m.getMessage());
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

    public void setCommandForGame(Game game, Command command){
        commandManager.setCommandForGame(game, command);
    }
}
