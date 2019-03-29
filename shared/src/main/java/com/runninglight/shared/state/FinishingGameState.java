package com.runninglight.shared.state;

import com.runninglight.shared.Game;

public class FinishingGameState implements IGameState {
    private int turnsLeft;

    public FinishingGameState() {
    }

    public FinishingGameState(int turnsLeft){
        this.turnsLeft = turnsLeft;
    }

    @Override
    public void continueGame(Game game){
        if(turnsLeft == 0){
            // Go to end game
        }
        else{
            game.nextTurn();
            turnsLeft--;
        }
    }
}
