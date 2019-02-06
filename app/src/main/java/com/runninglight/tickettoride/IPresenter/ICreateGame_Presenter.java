package com.runninglight.tickettoride.IPresenter;

import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.iview.ICreateGame_View;

import java.util.Observable;
import java.util.Observer;

public interface ICreateGame_Presenter extends Observer {


     void createGame(GameInfo gameInfo);
     void joinGame(GameInfo gameInfo);
     void cancel();

     void gameCreateSuccessful();
}
