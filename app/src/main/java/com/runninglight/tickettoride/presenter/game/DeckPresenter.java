package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.User;
import com.runninglight.tickettoride.IPresenter.game.IDeckPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IDeckView;

import java.util.Observable;

public class DeckPresenter implements IDeckPresenter
{
    private static DeckPresenter instance;

    private IDeckView deckView;

    public static DeckPresenter getInstance()
    {
        if (instance == null) instance = new DeckPresenter();
        return instance;
    }

    public void addView(IDeckView deckView)
    {
        this.deckView = deckView;
    }

    @Override
    public void cardDrawnFromFaceUp(TrainCard trainCard, int position)
    {
        User user = ClientModel.getInstance().getCurrentUser();
        Game game = ClientModel.getInstance().getCurrentGame();
        ServerProxy.getInstance().drawCardFromFaceUpToHand(game, user, trainCard, position);
    }

    @Override
    public void drawCardFromDeck()
    {
        User user = ClientModel.getInstance().getCurrentUser();
        Game game = ClientModel.getInstance().getCurrentGame();
        ServerProxy.getInstance().drawCardFromDeckToHand(game, user);
    }

    @Override
    public void addCardToFaceUp(TrainCard trainCard, int position)
    {
        deckView.addCardToFaceUpDeck(trainCard, position);
    }

    @Override
    public void initDestCardDeck(){
        int deckSize = ClientModel.getInstance().getCurrentDestDeckSize();
        deckView.refreshDestDeck(deckSize);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(arg instanceof Integer){
            deckView.refreshDestDeck(ClientModel.getInstance().getCurrentDestDeckSize());
        }
    }
}
