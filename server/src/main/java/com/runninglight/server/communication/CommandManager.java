package com.runninglight.server.communication;

import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.jws.soap.SOAPBinding;

public class CommandManager
{
    private HashMap<String, LinkedList<Command>> commandMap;

    public CommandManager()
    {
        commandMap = new HashMap<>();
    }

    public void setCommand(String userId, Command command)
    {
        if (!commandMap.containsKey(userId)) commandMap.put(userId, new LinkedList<Command>());
        commandMap.get(userId).add(command);
    }

    public Command getCommand(String userId)
    {
        if (!commandMap.containsKey(userId)) return null;
        if (commandMap.get(userId).size() == 0) return null;
        Command command = commandMap.get(userId).pop();
        return command;
    }

    public void setCommandForGame(Game game, Command command)
    {
        for (User user : game.getUserList())
        {
            System.out.println("setting command: " + user.getUserName());
            setCommand(user.getUserName(), command);
        }
    }
}
