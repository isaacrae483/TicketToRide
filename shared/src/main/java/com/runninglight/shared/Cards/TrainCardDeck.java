package com.runninglight.shared.Cards;

import com.runninglight.shared.CardColor;

import java.util.ArrayList;

import static com.runninglight.shared.CardColor.WILD;

public class TrainCardDeck {

    private static TrainCardDeck instance;

    public static TrainCardDeck getInstance() {
        if (instance == null) {
            instance = new TrainCardDeck();
        }
        return instance;
    }

    private TrainCardDeck(){
        initializeDeck();
    }

    private void initializeDeck() {
        numCards = 110;
        discardPile = new DiscardPile();
        CardColor colorArray[] = CardColor.values();

        for (int i = 0; i < 9; ++i) {
            for (CardColor color: colorArray) {
                for (int j = 0; j < 12; j++) {
                    trainCards.add(new TrainCard(color));
                }
                if (color.equals(WILD)) {
                    trainCards.add(new TrainCard(color));
                    trainCards.add(new TrainCard(color));
                }
            }
        }
        shuffleDeck();
    }

    private int numCards;

    private ArrayList<TrainCard> trainCards;

    private DiscardPile discardPile;

    public int getNumCardsInDeck() {
        return numCards;
    }

    public TrainCard drawCard() {
        if (trainCards.size() == 0) {
            addDiscardToDeck();
            numCards = trainCards.size();
        }
        TrainCard drawnCard = trainCards.get(0);
        trainCards.remove(0);
        numCards = trainCards.size();
        return drawnCard;
    }

    public void discard(TrainCard trainCard){
        discardPile.addCard(trainCard);
    }

    public void shuffleDeck() {}

    public void addDiscardToDeck(){
        trainCards = discardPile.getDiscardPile();
        shuffleDeck();
    }
}
