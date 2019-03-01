package com.runninglight.shared.Cards;

import java.util.ArrayList;

public class FaceUpCards {

    FaceUpCards() {
        faceUpCards = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            faceUpCards.add(trainCardDeck.drawCard());
        }
    }

    private TrainCardDeck trainCardDeck = TrainCardDeck.getInstance();

    private ArrayList<TrainCard> faceUpCards;

    public ArrayList<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public TrainCard getCard(int index) {
        TrainCard cardPicked = faceUpCards.get(index);
        faceUpCards.remove(index);
        addCard(index);
        return cardPicked;
    }

    public void addCard(int index){
        faceUpCards.add(index, trainCardDeck.drawCard());
    }
}
