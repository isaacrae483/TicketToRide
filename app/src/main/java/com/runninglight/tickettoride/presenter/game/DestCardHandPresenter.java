package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.tickettoride.IPresenter.game.IDestCardHandPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IDestCardHandView;

import java.util.ArrayList;

public class DestCardHandPresenter implements IDestCardHandPresenter {

    private static DestCardHandPresenter instance;

    private ClientModel model = ClientModel.getInstance();
    private IDestCardHandView destCardHandView;

    public static DestCardHandPresenter getInstance()
    {
        if (instance == null){
            instance = new DestCardHandPresenter();
        }
        return instance;
    }

    public void addView(IDestCardHandView destCardView)
    {
        this.destCardHandView = destCardView;
    }

    public DestinationCard[] getCards(){
        return model.getCurrentPlayer().getDestinationCards().toArray(new DestinationCard[0]);
    }
}
