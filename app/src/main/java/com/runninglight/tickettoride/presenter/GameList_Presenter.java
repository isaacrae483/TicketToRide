package com.runninglight.tickettoride.presenter;

import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.IGameList_Presenter;
import com.runninglight.tickettoride.iview.IGameList_View;

import java.util.Observable;

public class GameList_Presenter implements IGameList_Presenter {

    public GameList_Presenter(IGameList_View v){
        view = v;
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

    }
}
