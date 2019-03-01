package com.runninglight.shared.Cards;

public class TrainCardDeck {
    TrainCardDeck(){}

    public int getNumCardsInDeck(){
        int numCards = 0;
        return numCards;
    }

    public TrainCard drawCard() {
        return new TrainCard();
    }

    public void discard(TrainCard trainCard){}

    public void shuffleDeck() {}

    public void addDiscardToDeck(){}
}
