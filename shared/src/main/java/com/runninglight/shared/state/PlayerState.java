package com.runninglight.shared.state;

import com.runninglight.shared.Game;

public class PlayerState {

    private String playerName;

    private int totalPlayers;

    private int playerIndex;

    public PlayerState(){

    }

    public PlayerState(String playerName, int totalPlayers) {
        this.playerName = playerName;
        this.totalPlayers = totalPlayers;
        playerIndex = 0;
    }

    public boolean isMyTurn(String playerName){
        if(this.playerName.equals(playerName)){
            return true;
        }
        return false;
    }

    public void nextTurn(Game game){
        if(totalPlayers != 1) {
            playerName = game.getNextPlayerName(playerIndex);
            cyclePlayerIndex();
        }
    }

    private void cyclePlayerIndex(){
        if(playerIndex + 1 == totalPlayers){
            playerIndex = 0;
        }
        else{
            playerIndex++;
        }
    }

    public void setTotalPlayers(int totalPlayers){
        this.totalPlayers = totalPlayers;
    }

    public String getPlayerName(){
        return playerName;
    }
}
