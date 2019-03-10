package com.runninglight.tickettoride.iview.game;

import com.runninglight.shared.Cards.TrainCard;

import java.util.ArrayList;

public interface IHandView {
    void refreshDestCardCount(int numCards);
    void updateHandNumbers(ArrayList<TrainCard> cards);
}
