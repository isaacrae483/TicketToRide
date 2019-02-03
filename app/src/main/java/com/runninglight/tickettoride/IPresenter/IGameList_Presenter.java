package com.runninglight.tickettoride.IPresenter;

import com.runninglight.shared.GameInfo;

import java.util.Observable;
import java.util.Observer;

public interface IGameList_Presenter extends Observer {

     void createGame(GameInfo gameInfo);
     void joinGame(GameInfo gameInfo);

     void updateGameListView();

}
