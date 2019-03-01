package com.runninglight.shared.Cards;

public class TrainCardDeck {

    private static TrainCardDeck instance;

    public static TrainCardDeck getInstance() {
        if (instance == null) {
            instance = new TrainCardDeck();
        }
        return instance;
    }

    private TrainCardDeck(){}

    private int numCards = 105;

    public int getNumCardsInDeck() {
        return numCards;
    }

    //public TrainCard drawCard() {}

    public void discard(TrainCard trainCard){}

    public void shuffleDeck() {}

    public void addDiscardToDeck(){}
}
