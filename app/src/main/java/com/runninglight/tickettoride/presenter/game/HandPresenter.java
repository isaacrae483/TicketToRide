package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IHandPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IHandView;

import java.util.Observable;

public class HandPresenter implements IHandPresenter {

    private static HandPresenter instance;

    private ClientModel model = ClientModel.getInstance();

    private IHandView handView;

    public static HandPresenter getInstance()
    {
        if (instance == null){
            instance = new HandPresenter();
        }
        return instance;
    }

    public HandPresenter(){
        model.addObserver(this);
    }

    public void addView(IHandView handView)
    {
        this.handView = handView;
    }

    @Override
    public void update(Observable o, Object arg) {
        int destCardCount = model.getCurrentPlayer().getDestinationCards().size();
        handView.refreshDestCardCount(destCardCount);
        handView.updateHandNumbers(ClientModel.getInstance().getCurrentPlayer().getHand().getTrainCards());
    }
}
