package com.runninglight.shared.Cards;

import java.util.ArrayList;

import static com.runninglight.shared.Cards.CardColor.WILD;

public class FaceUpCards {

    public FaceUpCards() {
        faceUpCards = new TrainCard[5];
        /*faceUpCards = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            faceUpCards.add(trainCardDeck.drawCard());
        }*/
    }

    //private TrainCardDeck trainCardDeck = TrainCardDeck

    private TrainCard[] faceUpCards;

    public TrainCard[] getFaceUpCards() {
        return faceUpCards;
    }

    public TrainCard getCard(int index) {
        /*TrainCard cardPicked = faceUpCards.get(index);
        faceUpCards.remove(index);
        addCard(index);
        return cardPicked;*/
        return faceUpCards[index - 1];
    }

    public TrainCard removeCard(int index)
    {
        TrainCard tempCard = faceUpCards[index - 1];
        faceUpCards[index - 1] = null;
        return tempCard;
    }

    public void addCard(int index, TrainCard trainCard){
        faceUpCards[index - 1] = trainCard;
        //faceUpCards.add(index, trainCardDeck.drawCard());
    }

    public boolean tooManyWildCards() {
        int numWildCards = 0;
        for (TrainCard card : faceUpCards) {
            if (card != null) {
                if (card.getCardColor().equals(WILD)) {
                    ++numWildCards;
                }
            }
        }
        if (numWildCards >= 3) {
            System.out.println("too many wild cards");
            return true;
        }
        else {
            return false;
        }
    }
}
