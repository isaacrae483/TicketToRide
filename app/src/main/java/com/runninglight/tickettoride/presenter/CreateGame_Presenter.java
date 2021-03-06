package com.runninglight.tickettoride.presenter;

import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.ICreateGame_Presenter;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.ICreateGame_View;

import java.util.Observable;

public class CreateGame_Presenter implements ICreateGame_Presenter {

    public CreateGame_Presenter(ICreateGame_View v){
        view = v;

    }

    private ICreateGame_View view;


    @Override
    public void createGame(GameInfo gameInfo) {

        boolean gameCreated = ServerProxy.getInstance().createGame(gameInfo);
        if (gameCreated)
        {
            view.onCreateSuccessful();
        }
        else
        {
            view.showToast("Failed to create game");
        }

    }

    @Override
    public void joinGame(GameInfo gameInfo) {

        view.joinGame(gameInfo);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void gameCreateSuccessful() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
