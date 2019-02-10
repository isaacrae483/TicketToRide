package com.runninglight.tickettoride.presenter;

import com.runninglight.tickettoride.IPresenter.IGameLobby_Presenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.IGameLobby_View;

import java.util.Observable;

public class GameLobby_Presenter implements IGameLobby_Presenter {

    private ServerProxy proxy = ServerProxy.getInstance();
    private ClientModel model = ClientModel.getInstance();

    public GameLobby_Presenter(IGameLobby_View v){
        view =v;
    }


    private IGameLobby_View view;
    @Override
    public void startGame() {

        view.startGame();
    }


    @Override
    public void leaveGame(){
        proxy.leaveGame(model.getCurrentUser(), model.getCurrentGame());
    }



    @Override
    public void update(Observable o, Object arg) {

    }
}
