package com.runninglight.shared.Cards;

import com.runninglight.shared.CardColor;

public class TrainCard {
    public TrainCard(CardColor color){
        cardColor = color;
    }

    private CardColor cardColor;

    public CardColor getCardColor(){
        return cardColor;
    }
}
