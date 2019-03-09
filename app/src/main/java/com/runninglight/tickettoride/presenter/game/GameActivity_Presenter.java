package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IGameActivity_Presenter;
import com.runninglight.tickettoride.activity.game.GameActivity;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IGameActivity_View;

import java.util.Observable;

public class GameActivity_Presenter implements IGameActivity_Presenter {

    private ClientModel model = ClientModel.getInstance();

    public GameActivity_Presenter(IGameActivity_View v){
        view = v;
    }

    private IGameActivity_View view;

    /**
     * The person who goes first is the person who joined the room first
     */
    public void initTurn(){
        model.setCurrentTurn(model.getCurrentGame().getPlayerList().get(0).getName());
    }

    @Override
    public void update(Observable o, Object arg) {
        //if(arg instanceof String){
       //     model.setCurrentTurn((String)arg);
      //  }
    }
}
