package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IDestCardPresenter;
import com.runninglight.tickettoride.iview.game.IDestCardView;

public class DestCardPresenter implements IDestCardPresenter {
    private static DestCardPresenter instance;

    private IDestCardView destCardView;

    public static DestCardPresenter getInstance()
    {
        if (instance == null) instance = new DestCardPresenter();
        return instance;
    }

    public void addView(IDestCardView destCardView)
    {
        this.destCardView = destCardView;
    }
}
