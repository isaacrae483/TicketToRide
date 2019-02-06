package com.runninglight.shared;

import java.util.ArrayList;
import java.util.UUID;

public class Game {

    /** Name of the game room */
    private String gameName;

    /** Unique 8-digit hex ID for the game room */
    private String gameID;

    /** List of Users in the game room */
    private ArrayList<User> playerList;

    /** Maximum number of Users allowed in the game room */
    private int maxPlayerNumber;

    /** Current number of players in the game room */
    private int numPlayers;

    /** Indicates the last index of the hex ID */
    private static final int ID_END = 7;

    /**
     * Game constructor
     *
     * @param gameName name of the game room
     * @param maxPlayerNumber maximum number of players allowed in the game room
     *
     * @pre gameName != null
     * @post Creates a Game object with the specified name and maxPlayerNumber and generates an ID
     */
    public Game(String gameName, int maxPlayerNumber) {
        this.gameName = gameName;
        this.maxPlayerNumber = maxPlayerNumber;
        this.numPlayers = 0;
        this.playerList = new ArrayList<>();
        this.gameID = generateID();
    }

    /**
     * Adds a User to the playerList
     * @param user User to add
     *
     * @pre none
     * @post Adds a User to the playerList
     */
    public void addPlayer(User user){
        if(user != null) {
            playerList.add(user);
            ++numPlayers;
        }
    }

    /**
     * Removes a User from the playerList
     * @param user User to remove
     *
     * @pre none
     * @post Removes a User from the playerList if it exists
     */
    public void removePlayer(User user){
        int userIndex = find(user.getUserID());
        if(userIndex != -1){
            playerList.remove(userIndex);
        }
    }

    /**
     * Searches the playerList for a User with a specific userID and returns the index of the User
     * @param userID ID to search for
     * @return the index in the playerList of the User with the specified userID, -1 if not found
     *
     * @pre none
     * @post Returns the index in the playerList of the User with the specified userID, -1 if not found
     */
    private int find(String userID){
        if(userID == null){
            return -1;
        }
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getUserID().equals(userID)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a random 8-digit hex value
     *
     * @pre none
     * @post returns a random 8-digit hex value
     */
    private String generateID(){
        String longID = UUID.randomUUID().toString();
        return longID.substring(0, ID_END);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public ArrayList<User> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<User> playerList) {
        this.playerList = playerList;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
}
