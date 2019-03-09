package com.runninglight.tickettoride.iview.game;

import com.runninglight.shared.Cards.TrainCard;

public interface IDeckView
{
    void addCardToFaceUpDeck(TrainCard trainCard, int position);
    void refreshDestDeck(int deckSize);
    void refreshTrainCardDeckSize(int deckSize);
}
