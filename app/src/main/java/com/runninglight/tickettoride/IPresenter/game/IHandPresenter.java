package com.runninglight.tickettoride.IPresenter.game;

import java.util.Observer;

public interface IHandPresenter extends Observer {
    void initObserver();
    void removeObserver();
    void initInfo();
}
