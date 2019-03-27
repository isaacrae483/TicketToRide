package com.runninglight.tickettoride.IPresenter.game;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.tickettoride.iview.game.IDeckView;

import java.util.Observer;

public interface IDeckPresenter extends Observer
{
    void checkIfMyTurn();
    void cardDrawnFromFaceUp(TrainCard trainCard, int postion);
    void drawCardFromDeck();
    void addCardToFaceUp(TrainCard trainCard, int position);
    void initDestCardDeck();
    void initObserver();
    void removeObserver();
    void addView(IDeckView view);
    boolean hasDrawnCards();
}
