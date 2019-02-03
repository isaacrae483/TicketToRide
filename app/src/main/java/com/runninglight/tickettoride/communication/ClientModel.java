package com.runninglight.tickettoride.communication;

import com.runninglight.shared.Game;
import com.runninglight.shared.User;

import java.util.ArrayList;

public class ClientModel {
    private ArrayList<Game> gameList;

    private ArrayList<User> userList;

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
