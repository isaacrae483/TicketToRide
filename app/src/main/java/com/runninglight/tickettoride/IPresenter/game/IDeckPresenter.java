package com.runninglight.tickettoride.IPresenter.game;

import com.runninglight.shared.Cards.TrainCard;

import java.util.Observer;

public interface IDeckPresenter extends Observer
{
    void checkIfMyTurn();
    void cardDrawnFromFaceUp(TrainCard trainCard, int postion);
    void drawCardFromDeck();
    void addCardToFaceUp(TrainCard trainCard, int position);
    void initDestCardDeck();
}
