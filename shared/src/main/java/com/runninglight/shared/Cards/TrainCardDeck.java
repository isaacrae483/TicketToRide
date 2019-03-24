package com.runninglight.shared.Cards;

import java.util.ArrayList;
import java.util.Collections;
import static com.runninglight.shared.Cards.CardColor.WILD;

/**
 * The TrainCardDeck class is a singleton that holds an array of TrainCard objects
 * for the Ticket to Ride Game.
 */
public class TrainCardDeck {

    /**
     * The singleton instance of the TrainCardDeck
     */
    private static TrainCardDeck instance;

    /**
     * This function gets the singleton instance of the class.
     * @return the singleton instance
     */
    public static TrainCardDeck getInstance() {
        if (instance == null) {
            instance = new TrainCardDeck();
        }
        return instance;
    }

    /**
     * The constructor which initializes a deck
     */
    private TrainCardDeck(){
        initializeDeck();
    }

    /**
     * initializes the deck
     * @pre none
     * @post initializes a deck of 110 train cards, with 12 of each train card color and
     * 14 wild train cards
     */
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

    /**
     * total number of cards in the deck
     */
    private int numCards;

    /**
     * an ArrayList that stores the TrainCard objects in the deck
     */
    private ArrayList<TrainCard> trainCards;

    /**
     * the deck's discard pile which holds cards discarded by players
     */
    private DiscardPile discardPile;

    /**
     * get the number of cards currently in the deck
     * @return numCards
     */
    public int getNumCardsInDeck() {
        return numCards;
    }

    /**
     * draws a single card from the deck. The pre-condition is checked, and if
     * numCards = 0, then the discardPile is shuffled and added to the deck.
     * @pre numCards > 0
     * @post removes a card from the deck and returns it
     * @post numCards = numCards -1 (unless the discard pile is shuffled and added
     * back to the deck)
     * @return the card that is drawn from the deck
     */
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

    /**
     * Takes in TrainCard from a player and adds it to the deck's discard pile
     * @pre a valid TrainCard object is passed in
     * @post the card is added to the discard pile
     * @param trainCard
     */
    public void discard(TrainCard trainCard){
        discardPile.addCard(trainCard);
    }

    /**
     * @pre none
     * @post the order of the train cards in trainCards is shuffled
     * shuffles the train card deck
     */
    public void shuffleDeck() {
        Collections.shuffle(trainCards);
    }

    /**
     * adds the discard pile to the deck, clears the discard pile, and shuffles the deck
     * @pre none
     * @post the discardPile is added to the deck
     */
    public void addDiscardToDeck(){
        trainCards = discardPile.getDiscardPile();
        discardPile.clearDiscardPile();
        shuffleDeck();
    }
}
