package com.runninglight.shared;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.TrainCardDeck;

import java.util.ArrayList;

public class Hand {

    private ArrayList<TrainCard> trainCards;

    public Hand() {
        trainCards = new ArrayList<>();
    }

    public ArrayList<TrainCard> getTrainCards()
    {
        return trainCards;
    }

    public void addTrainCard(TrainCard trainCard)
    {
        trainCards.add(trainCard);
    }
}
