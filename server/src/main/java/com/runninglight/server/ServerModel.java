package com.runninglight.server;

import com.runninglight.shared.Game;
import com.runninglight.shared.User;

import java.util.ArrayList;

public class ServerModel {
    private static ServerModel instance = null;
    private ArrayList<Game> gameList;
    private ArrayList<User> userList;

    public static ServerModel getInstance(){
        if(instance == null){
            instance = new ServerModel();
        }
        return instance;
    }

    private ServerModel(){
        gameList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void addGame(Game game) {
        
    }

    public User findByUserName(User user){
        for(User u : userList){
            if(u.getUserName().equals(user.getUserName())){
                return u;
            }
        }
        return null;
    }

    public boolean isValidUser(User user){
        for(User u : userList){
            if(u.equals(user)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
