package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IGameActivity_Presenter;
import com.runninglight.tickettoride.activity.game.GameActivity;
import com.runninglight.tickettoride.iview.game.IGameActivity_View;

import java.util.Observable;

public class GameActivity_Presenter implements IGameActivity_Presenter {

    public GameActivity_Presenter(IGameActivity_View v){
        view = v;
    }

    private IGameActivity_View view;


    @Override
    public void update(Observable o, Object arg) {

    }
}