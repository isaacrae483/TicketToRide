package com.runninglight.shared;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.TrainCardDeck;

import java.util.ArrayList;

public class Hand {
    Hand(){
        trainCards = new ArrayList<>();
        initializeHand();
    }

    private TrainCardDeck trainCardDeck = TrainCardDeck.getInstance();

    private ArrayList<TrainCard> trainCards;

    public void initializeHand() {
        for (int i = 0; i < 4; ++i) {
            trainCards.add(trainCardDeck.drawCard());
        }
    }
}
