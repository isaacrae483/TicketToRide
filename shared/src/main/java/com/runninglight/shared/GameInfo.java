package com.runninglight.shared;

public class GameInfo {

    /** Name of the game room */
    private String gameName;

    /** Max number of players allowed in the room */
    private int maxPlayerNumber;

    /**
     * GameInfo constructor
     *
     * @param gameName name of the room
     * @param maxPlayerNumber maximum number of players in the room
     *
     * @pre None
     * @post gameName and maxPlayerNumber are assigned values
     */
    public GameInfo(String gameName, int maxPlayerNumber){
        this.gameName = gameName;
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public String getGameName() {
        return gameName;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }
}
