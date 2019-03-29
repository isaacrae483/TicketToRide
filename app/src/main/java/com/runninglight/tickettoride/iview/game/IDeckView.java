package com.runninglight.tickettoride.iview.game;

import com.runninglight.shared.Cards.TrainCard;

public interface IDeckView
{
    void enableListeners();
    void disableListeners();
    void enableDestDeckListener();
    void enableTrainDeckListeners();
    void disableDestDeckListener();
    void disableTrainDeckListeners();
    void disableFaceUpListener(int position);
    void disableFaceUpWildListeners();
    void addCardToFaceUpDeck(TrainCard trainCard, int position);
    void refreshDestDeck(int deckSize);
    void refreshTrainCardDeckSize(int deckSize);
}
