package com.runninglight.shared.Cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DiscardPile {
    DiscardPile(){
        discardPile = new ArrayList<>();
    }

    private ArrayList<TrainCard> discardPile;

    public void addCards(ArrayList<TrainCard> discards) {
        discardPile.addAll(discards);
    }

    public ArrayList<TrainCard> getDiscardPile() {
        return discardPile;
    }

    public void clearDiscardPile() {
        discardPile.clear();
    }
}
