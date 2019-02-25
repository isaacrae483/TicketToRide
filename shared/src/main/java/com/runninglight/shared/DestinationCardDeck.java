package com.runninglight.shared;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DestinationCardDeck {

    private ArrayList<DestinationCard> deck;

    private static final int STARTING_CARD_COUNT = 30;

    public DestinationCardDeck(){
        initializeDeck();
    }

    private void initializeDeck(){
        // TODO: Fill deck with proper cards--need to know city names
    }

    public DestinationCard getRandomCard(){
        int index = ThreadLocalRandom.current().nextInt(deck.size());
        DestinationCard result = deck.get(index);
        deck.remove(index);
        return result;
    }
}
