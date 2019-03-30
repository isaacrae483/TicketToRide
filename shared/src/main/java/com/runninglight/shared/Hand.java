package com.runninglight.shared;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.TrainCardDeck;

import java.util.ArrayList;

import static com.runninglight.shared.Cards.CardColor.WILD;

public class Hand {

    private ArrayList<TrainCard> trainCards;

    public Hand() {
        trainCards = new ArrayList<>();
    }

    public int getHandSize() {
        return trainCards.size();
    }

    public ArrayList<TrainCard> getTrainCards()
    {
        return trainCards;
    }

    public void addTrainCard(TrainCard trainCard)
    {
        trainCards.add(trainCard);
    }

    public ArrayList<TrainCard> playTrainCards(String color, int length){

        System.out.println(color + " length: "+ length);
        ArrayList<TrainCard> cardsToPlay = new ArrayList<>();
        int numCards = 0;

        for(TrainCard card : trainCards){
            if (numCards < length) {
                if (card.getCardColor().toString().equals(color)) {
                    cardsToPlay.add(new TrainCard(card.getCardColor()));
                    ++numCards;
                }
            }
        }
        for(TrainCard card : trainCards) {
            if (numCards < length) {
                if (card.getCardColor().toString().equals(WILD.toString())) {
                    cardsToPlay.add(new TrainCard(card.getCardColor()));
                    ++numCards;
                }
            }
        }
        trainCards.removeAll(cardsToPlay);
        System.out.println("in hand, remove size: "+ cardsToPlay.size()+ " numCards: "+ numCards+ " length: "+length);
        return cardsToPlay;
    }

    public boolean canClaimRoute(String color, int length) {
        int numCards = 0;

        for (TrainCard card : trainCards) {
            if (card.getCardColor().toString().equals(color) || card.getCardColor().toString().equals(WILD.toString())) {
                ++numCards;
            }
        }
        if (numCards >= length) {
            return true;
        }
        System.out.println("in hand size: "+trainCards.size()+" numCards: "+ numCards +"of color: "+ color);
        System.out.println(WILD.toString());
        return false;
    }
}
