package com.runninglight.tickettoride.presenter;

import com.runninglight.tickettoride.IPresenter.IGameLobby_Presenter;
import com.runninglight.tickettoride.iview.IGameLobby_View;

import java.util.Observable;

public class GameLobby_Presenter implements IGameLobby_Presenter {

    public GameLobby_Presenter(IGameLobby_View v){
        view =v;
    }


    private IGameLobby_View view;
    @Override
    public void StartGame() {

        view.startGame();
    }




    @Override
    public void update(Observable o, Object arg) {

    }
}
