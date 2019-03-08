package com.runninglight.tickettoride.IPresenter.game;


import com.runninglight.shared.DestinationCard;
import com.runninglight.tickettoride.iview.game.IDestCardView;

public interface IDestCardPresenter {
    public void addView(IDestCardView v);
    public DestinationCard[] drawDestCards();
    public void returnDestCards(Integer[] keptIndices, Integer[] returnIndices);
}
