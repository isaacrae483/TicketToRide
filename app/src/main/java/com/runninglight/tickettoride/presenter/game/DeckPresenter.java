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

    private ServerProxy proxy = ServerProxy.getInstance();

    private IDeckView deckView;

    private int trainCardsDrawn;

    private static final int MAX_TRAIN_CARDS_PER_TURN = 2;

    public static DeckPresenter getInstance()
    {
        if (instance == null) instance = new DeckPresenter();
        return instance;
    }

    public DeckPresenter(){
        ClientModel.getInstance().addObserver(this);
        trainCardsDrawn = 0;
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
        trainCardsDrawn++;
        if(trainCard.isWild()){
            trainCardsDrawn = 0;
            try {
                model.nextTurn();
                proxy.setTurn(model.getCurrentGameID(), model.getPlayerState());
            }
            catch(RuntimeException e){
                //deckView.endGame();
                proxy.endGame(model.getCurrentGameID());
            }
        }
        checkIfTurnOver();
    }

    @Override
    public void drawCardFromDeck()
    {
        User user = ClientModel.getInstance().getCurrentUser();
        Game game = ClientModel.getInstance().getCurrentGame();
        ServerProxy.getInstance().drawCardFromDeckToHand(game, user);
        trainCardsDrawn++;
        checkIfTurnOver();
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
    public boolean hasDrawnCards(){
        if(trainCardsDrawn > 0){
            return true;
        }
        return false;
    }

    private void checkIfTurnOver(){
        if(trainCardsDrawn == MAX_TRAIN_CARDS_PER_TURN){
            trainCardsDrawn = 0;
            try {
                model.nextTurn();
                proxy.setTurn(model.getCurrentGameID(), model.getPlayerState());
            }
            catch(RuntimeException e){
               // model.signalEndGame();
               // deckView.endGame();
                proxy.endGame(model.getCurrentGameID());
            }
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

        if(hasDrawnCards()){
            deckView.disableFaceUpWildListeners();
        }
    }


}
