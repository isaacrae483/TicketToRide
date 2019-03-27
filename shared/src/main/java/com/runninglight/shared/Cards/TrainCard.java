package com.runninglight.shared.Cards;

public class TrainCard {
    public TrainCard(CardColor color){
        cardColor = color;
    }

    private CardColor cardColor;

    public CardColor getCardColor(){
        return cardColor;
    }

    public boolean isWild(){
        if(cardColor == CardColor.WILD){
            return true;
        }
        return false;
    }
}
