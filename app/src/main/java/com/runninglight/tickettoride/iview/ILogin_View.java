package com.runninglight.tickettoride.iview;

import com.runninglight.shared.Game;

import java.util.ArrayList;

public interface  ILogin_View {

    void loginSuccessful(ArrayList<Game> games);
    void loginFailed();

}
