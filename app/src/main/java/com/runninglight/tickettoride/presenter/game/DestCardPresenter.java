package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.tickettoride.IPresenter.game.IDestCardPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IDestCardView;

public class DestCardPresenter implements IDestCardPresenter {
    private static DestCardPresenter instance;
    private ServerProxy proxy = ServerProxy.getInstance();
    private ClientModel model = ClientModel.getInstance();

    private IDestCardView destCardView;

    private DestinationCard[] cards;
    private boolean firstTurn;

    private static final int DRAW_CARD_NUM = 3;
    private static final int MIN_CARDS_KEPT_FIRST = 2;
    private static final int MIN_CARDS_KEPT = 1;

    public static DestCardPresenter getInstance()
    {
        if (instance == null){
            instance = new DestCardPresenter();
        }
        return instance;
    }

    public DestCardPresenter(){
        firstTurn = true;
    }

    public void addView(IDestCardView destCardView)
    {
        this.destCardView = destCardView;
    }

    public DestinationCard[] drawDestCards(){
        cards = proxy.drawDestCards(model.getCurrentGameID(), DRAW_CARD_NUM);
        return cards;
    }

    public void returnDestCards(Integer[] keptIndices, Integer[] returnIndices){
        if(!isValidSelection(keptIndices.length)){
            displayErrorToast();
            return;
        }
        DestinationCard[] keptCards = new DestinationCard[keptIndices.length];
        DestinationCard[] cardsToReturn = new DestinationCard[returnIndices.length];
        for(int i = 0; i < keptIndices.length; i++){
            keptCards[i] = cards[keptIndices[i]];
        }
        for(int i = 0; i < returnIndices.length; i++){
            cardsToReturn[i] = cards[returnIndices[i]];
        }
        proxy.returnDestCards(model.getCurrentGameID(), model.getCurrentPlayer().getName(),
                keptCards, cardsToReturn);
        firstTurn = false;
        try {
            model.nextTurn();
            proxy.setTurn(model.getCurrentGameID(), model.getPlayerState());
        }
        catch(RuntimeException e){
            proxy.endGame(model.getCurrentGameID());
        }
        destCardView.onSelectionSuccessful();
    }

    private boolean isValidSelection(int numCardsKept){
        if(firstTurn && numCardsKept >= MIN_CARDS_KEPT_FIRST){
            return true;
        }
        else if(!firstTurn && numCardsKept >= MIN_CARDS_KEPT){
            return true;
        }
        return false;
    }

    private void displayErrorToast(){
        if(firstTurn){
            destCardView.showToast("You must select at least 2 cards");
        }
        else{
            destCardView.showToast("You must select at least 1 card");
        }
    }
}
