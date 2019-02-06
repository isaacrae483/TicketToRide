package com.runninglight.tickettoride.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.runninglight.tickettoride.IPresenter.IGameLobby_Presenter;
import com.runninglight.tickettoride.R;
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
        setContentView(R.layout.activity_gamelobby);

        presenter = new GameLobby_Presenter(this);

        lobbyTitle_TV = findViewById(R.id.lobbyTitle_textView);
        lobbyMessage_TV = findViewById(R.id.lobbyMessage_textView);
        playerCount_TV = findViewById(R.id.playerCount_textView);

    }


    @Override
    public void startGame() {

        lobbyMessage_TV.setText(R.string.lobby_message_starting);
    }

    @Override
    public void addPlayer() {

    }
}
