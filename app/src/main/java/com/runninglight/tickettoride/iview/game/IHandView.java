package com.runninglight.tickettoride.iview.game;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Player;

import java.util.ArrayList;

public interface IHandView {
    void refreshDestCardCount(int numCards);
    void updateHandNumbers(ArrayList<TrainCard> cards);
    void updateCurrentPlayerInfo(Player currentPlayer, String currentTurn);
}
