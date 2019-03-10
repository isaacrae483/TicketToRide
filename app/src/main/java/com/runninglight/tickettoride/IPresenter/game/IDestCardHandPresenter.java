package com.runninglight.tickettoride.IPresenter.game;


import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.tickettoride.iview.game.IDestCardHandView;

public interface IDestCardHandPresenter {
    void addView(IDestCardHandView v);
    DestinationCard[] getCards();
}
