package com.runninglight.tickettoride.communication;

import com.runninglight.shared.Game;
import com.runninglight.shared.User;

import java.util.ArrayList;
import java.util.Observable;

public class ClientModel extends Observable {
    private ArrayList<Game> gameList;

    private ArrayList<User> userList;

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
