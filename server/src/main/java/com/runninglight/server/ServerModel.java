package com.runninglight.server;

import com.runninglight.shared.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.Player;
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

        gameList.add(game);
        System.out.println("Game added, current number of games: " + gameList.size());
    }

    public void addUserToGame(User u, Game g) {
        int gameIndex = getGameIndex(g.getGameID());
        if(gameIndex != -1 && isValidUser(u)){
            gameList.get(gameIndex).addPlayer(u);
            System.out.println("Player added to Game " + g.getGameName() + " successfully");
        }
    }

    public boolean removeUserFromGame(User u, Game g){
        int gameIndex = getGameIndex(g.getGameID());
        if(gameIndex != -1 && isValidUser(u)){
            gameList.get(gameIndex).removePlayer(u);
            return true;
        }
        return false;
    }

    public boolean doesGameExist(String gameName) {
        for(Game g : gameList) {
            if(g.getGameName().equals(gameName)) {
                return true;
            }
        }
        return false;
    }

    public User findByUserName(User user){
        for(User u : userList){
            if(u.getUserName().equals(user.getUserName())){
                return u;
            }
        }
        return null;
    }

    public int getGameIndex(String gameID){
        for(int i = 0; i < gameList.size(); i++){
            if(gameList.get(i).getGameID().equals(gameID)){
                return i;
            }
        }
        return -1;
    }

    public boolean isValidUser(User user){
        for(User u : userList){
            if(u.equals(user)){
                return true;
            }
        }
        return false;
    }

    public Game getGameByID(String gameID){
        int resultIndex = getGameIndex(gameID);
        if(resultIndex == -1){
            return null;
        }
        return gameList.get(resultIndex);
    }

    public DestinationCard[] drawDestCards(String gameID, int numCards){
        int gameIndex = getGameIndex(gameID);
        if(gameIndex == -1){
            return null;
        }
        return gameList.get(gameIndex).drawDestCards(numCards);
    }

    public void returnDestCards(String gameID, DestinationCard[] cards){
        int gameIndex = getGameIndex(gameID);
        if(gameIndex != -1) {
            gameList.get(gameIndex).returnDestCards(cards);
        }
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
