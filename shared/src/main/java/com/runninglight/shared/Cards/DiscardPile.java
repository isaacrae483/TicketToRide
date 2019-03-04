package com.runninglight.shared.Cards;

import java.util.ArrayList;

public class DiscardPile {
    DiscardPile(){}

    private ArrayList<TrainCard> discardPile;

    public void addCard(TrainCard discard) {
        discardPile.add(discard);
    }

    public ArrayList<TrainCard> getDiscardPile() {
        return discardPile;
    }
}
