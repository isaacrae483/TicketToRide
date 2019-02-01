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
            System.out.println("No post argument specified");
        }
    }
}
