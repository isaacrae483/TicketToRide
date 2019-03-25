package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.User;
import com.runninglight.tickettoride.IPresenter.game.IDeckPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IDeckView;

import java.util.Observable;
import java.util.Observer;

public class DeckPresenter implements IDeckPresenter, Observer
{
    private static DeckPresenter instance;

    private ClientModel model = ClientModel.getInstance();

    private IDeckView deckView;

    public static DeckPresenter getInstance()
    {
        if (instance == null) instance = new DeckPresenter();
        return instance;
    }

    public DeckPresenter(){
        ClientModel.getInstance().addObserver(this);
    }

    public void addView(IDeckView deckView)
    {
        this.deckView = deckView;
        ClientModel.getInstance().addObserver(this);
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
    public void checkIfMyTurn(){
        if(model.isMyTurn()){
            if(model.initDestCardsPicked()) {
                deckView.enableListeners();
            }
            else{
                deckView.enableDestDeckListener();
            }
        }
        else{
            deckView.disableListeners();
        }
    }

    @Override
    public void initObserver(){
        model.addObserver(this);
    }

    @Override
    public void removeObserver(){
        model.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        deckView.refreshTrainCardDeckSize(ClientModel.getInstance().getCurrentGame().getTrainCardDeckSize());
        if (arg instanceof Integer) {
            deckView.refreshDestDeck(ClientModel.getInstance().getCurrentDestDeckSize());
            //deckView.refreshTrainCardDeckSize(ClientModel.getInstance().getCurrentGame().getTrainCardDeckSize());
        }
        if (arg instanceof ClientModel.FaceUpCardUpdate)
        {
            ClientModel.FaceUpCardUpdate faceUpCardUpdate = (ClientModel.FaceUpCardUpdate) arg;
            deckView.addCardToFaceUpDeck(faceUpCardUpdate.trainCard, faceUpCardUpdate.position);
        }
        if(arg instanceof String){
            checkIfMyTurn();
        }
    }


}
