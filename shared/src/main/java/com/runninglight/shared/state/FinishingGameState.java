package com.runninglight.shared.state;

import com.runninglight.shared.Game;

public class FinishingGameState implements IGameState {

    private int turnsLeft;

    public FinishingGameState() {
    }

    public FinishingGameState(int turnsLeft){
        this.turnsLeft = turnsLeft - 1;
    }

    @Override
    public void continueGame(Game game){
        System.out.println("***TURNS LEFT: " + turnsLeft);
        if(turnsLeft == 0){
            throw new RuntimeException("Game finished!");
        }
        else{
            game.nextTurn();
            turnsLeft--;
            game.decrementTurnsLeft();

        }
    }
}
