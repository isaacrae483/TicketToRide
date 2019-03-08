package com.runninglight.tickettoride.IPresenter.game;

import com.runninglight.shared.Cards.TrainCard;

public interface IDeckPresenter
{
    void cardDrawnFromFaceUp(TrainCard trainCard, int postion);
    void drawCardFromDeck();
    void addCardToFaceUp(TrainCard trainCard, int position);
}
