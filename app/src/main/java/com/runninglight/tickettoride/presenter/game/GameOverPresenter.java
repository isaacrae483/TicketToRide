package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Player;
import com.runninglight.tickettoride.IPresenter.game.IGameOverPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IGameOverView;

import java.util.ArrayList;

public class GameOverPresenter implements IGameOverPresenter
{
    private IGameOverView gameOverView;

    public GameOverPresenter(IGameOverView gameOverView)
    {
        this.gameOverView = gameOverView;
        ArrayList<Player> players = ClientModel.getInstance().getCurrentGame().getPlayerList();
        gameOverView.setPlayerPoints(players);
    }
}
