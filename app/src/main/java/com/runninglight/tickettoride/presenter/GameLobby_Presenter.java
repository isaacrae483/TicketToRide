package com.runninglight.tickettoride.presenter;

import com.runninglight.tickettoride.IPresenter.IGameLobby_Presenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.IGameLobby_View;

import java.util.Observable;
import java.util.Observer;

public class GameLobby_Presenter implements IGameLobby_Presenter, Observer {

    private ServerProxy proxy = ServerProxy.getInstance();
    private ClientModel model = ClientModel.getInstance();

    public GameLobby_Presenter(IGameLobby_View v){
        view =v;
        model.addObserver(this);
    }

    private IGameLobby_View view;
    @Override
    public void startGame() {

        model.deleteObserver(this);
        model.setCurrentPlayer(model.getCurrentGame().getPlayer(model.getCurrentUser().getUserName()));
        view.startGame();
    }


    @Override
    public void leaveGame(){
        proxy.leaveGame(model.getCurrentUser(), model.getCurrentGame());
    }



    @Override
    public void update(Observable o, Object arg) {
        view.refresh();
    }
}
