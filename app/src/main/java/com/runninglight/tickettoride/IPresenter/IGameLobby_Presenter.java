package com.runninglight.tickettoride.IPresenter;

import java.util.Observable;
import java.util.Observer;

public interface IGameLobby_Presenter extends Observer {

     void startGame();
     void leaveGame();
     void initObserver();
     void removeObserver();
}
