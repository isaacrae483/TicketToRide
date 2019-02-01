package com.runninglight.server.communication;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator
{
    private static final String PATH_COMMAND = "/command";

    private static ServerCommunicator instance;

    private HttpServer httpServer;

    public static ServerCommunicator getInstance()
    {
        if (instance == null) instance = new ServerCommunicator();
        return instance;
    }

    // Strictly for Server testing purposes
    public static void main(String[] args)
    {
        ServerCommunicator.getInstance().startServer(8000);
    }

    public void startServer(int port)
    {
        try
        {
            InetSocketAddress address = new InetSocketAddress(port);
            httpServer = HttpServer.create(address, 10);
            httpServer.setExecutor(null);

            httpServer.createContext(PATH_COMMAND, new CommandHandler());

            httpServer.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
