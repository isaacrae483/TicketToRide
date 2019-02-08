package com.runninglight.tickettoride.presenter;

import android.util.Log;

import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.IGameList_Presenter;
import com.runninglight.tickettoride.communication.ClientCommunicator;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.IGameList_View;

import java.util.Observable;
import java.util.Observer;

public class GameList_Presenter implements IGameList_Presenter, Observer
{

    public GameList_Presenter(IGameList_View v){
        view = v;
        ClientModel.getInstance().addObserver(this);
        ClientCommunicator.getInstance().startPoller(ClientModel.getInstance().getCurrentUser().getUserName());
    }

    private IGameList_View view;

    @Override
    public void createGame() {
        view.createGame();
    }

    @Override
    public void joinGame(GameInfo gameInfo) {
        view.joinGameSuccessful(gameInfo);
    }

    @Override
    public void updateGameListView() {

    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof Game)
        {
            Game addedGame = (Game) arg;
            Log.d("TicketToRide", "Game added: " +  addedGame.getGameName());
            for (Game game : ClientModel.getInstance().getGameList()) Log.d("TicketToRide", game.getGameName());
            // The adapter needs to be updated here, since the list of games has changed
        }
    }
}
