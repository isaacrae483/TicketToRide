package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IPlayerInfoPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IPlayerInfoView;

import java.util.Observable;

public class PlayerInfoPresenter implements IPlayerInfoPresenter {

    private static PlayerInfoPresenter instance;

    private ClientModel model = ClientModel.getInstance();

    private IPlayerInfoView playerInfoView;

    public static PlayerInfoPresenter getInstance()
    {
        if (instance == null){
            instance = new PlayerInfoPresenter();
        }
        return instance;
    }

    public PlayerInfoPresenter(){
        model.addObserver(this);
    }

    public void addView(IPlayerInfoView playerInfoView)
    {
        this.playerInfoView = playerInfoView;
    }

    @Override
    public void update(Observable o, Object arg) {
        playerInfoView.updatePlayerInfo(model.getCurrentGame().getPlayerList(), model.getCurrentPlayer(),
                model.getCurrentTurn());
    }

    @Override
    public void initObserver(){
        model.addObserver(this);
    }

    @Override
    public void removeObserver(){
        model.deleteObserver(this);
    }
}
