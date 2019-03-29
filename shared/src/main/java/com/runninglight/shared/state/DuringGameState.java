package com.runninglight.shared.state;

import com.runninglight.shared.Game;

public class DuringGameState implements IGameState {

    public DuringGameState() {
    }

    @Override
    public void continueGame(Game game) {
        if(game.isLastTurn()) {
            game.setGameState(new FinishingGameState(game.getNumPlayers()));
        }
        game.nextTurn();
    }


}
