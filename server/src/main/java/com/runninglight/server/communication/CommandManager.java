package com.runninglight.server.communication;

import com.runninglight.shared.Command;

import java.util.HashMap;

public class CommandManager
{
    private HashMap<String, Command> commandMap;

    public CommandManager()
    {
        commandMap = new HashMap<>();
    }

    public void setCommand(String userId, Command command)
    {
        commandMap.put(userId, command);
    }

    public Command getCommand(String userId)
    {
        if (!commandMap.containsKey(userId)) return null;
        Command command = commandMap.get(userId);
        commandMap.put(userId, null);
        return command;
    }
}
