package com.runninglight.tickettoride.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.runninglight.shared.Game;
import com.runninglight.tickettoride.IPresenter.IGameLobby_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.IGameLobby_View;
import com.runninglight.tickettoride.presenter.GameLobby_Presenter;

public class GameLobbyActivity extends AppCompatActivity implements IGameLobby_View {

    private TextView lobbyTitle_TV;
    private TextView playerCount_TV;
    private TextView lobbyMessage_TV;

    private IGameLobby_Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        setContentView(R.layout.activity_gamelobby);

        presenter = new GameLobby_Presenter(this);

        lobbyTitle_TV = findViewById(R.id.lobbyTitle_textView);
        lobbyMessage_TV = findViewById(R.id.lobbyMessage_textView);
        playerCount_TV = findViewById(R.id.playerCurrent_textView);

        String gameName =extra.getString("gameName");


        lobbyTitle_TV.setText(gameName);
        playerCount_TV.setText(ClientModel.getInstance().getGame(gameName).getNumPlayers()+"/"+ClientModel.getInstance().getGame(gameName).getMaxPlayerNumber());



    }


    @Override
    public void startGame() {

        lobbyMessage_TV.setText(R.string.lobby_message_starting);
    }

    @Override
    public void addPlayer(String gameName) {
        playerCount_TV = findViewById(R.id.playerCurrent_textView);
        Game game = ClientModel.getInstance().getGame(gameName);

        playerCount_TV.append(String.valueOf(game.getNumPlayers()),0,1);

    }
}
