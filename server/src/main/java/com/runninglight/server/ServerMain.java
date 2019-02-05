package com.runninglight.server;

import com.runninglight.server.communication.ServerCommunicator;

public class ServerMain
{
    public static void main(String[] args)
    {
        if (args.length >= 1)
        {
            ServerCommunicator.getInstance().startServer(Integer.parseInt(args[0]));
            System.out.println("Server started on port: " + args[0]);
        }
        else
        {
            // Default port unless specified is 8080
            System.out.println("No port argument specified");
            ServerCommunicator.getInstance().startServer(8080);
            System.out.println("Server started on port: 8080");
        }
    }
}
