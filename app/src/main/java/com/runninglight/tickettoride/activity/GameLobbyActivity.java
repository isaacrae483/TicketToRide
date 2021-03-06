package com.runninglight.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.runninglight.shared.Game;
import com.runninglight.tickettoride.IPresenter.IGameLobby_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.activity.game.GameActivity;
import com.runninglight.tickettoride.activity.game.GameOverActivity;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.IGameLobby_View;
import com.runninglight.tickettoride.presenter.GameLobby_Presenter;

public class GameLobbyActivity extends AppCompatActivity implements IGameLobby_View {

    private TextView lobbyTitle_TV;
    private TextView playerCount_TV;
    private TextView lobbyMessage_TV;
    private Handler handler;
    private IGameLobby_Presenter presenter;
    private ClientModel model = ClientModel.getInstance();
    private String gameName;
    private String gameID;
    private int currentNumPlayers;
    private int maxNumPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        setContentView(R.layout.activity_gamelobby);

        presenter = new GameLobby_Presenter(this);

        lobbyTitle_TV = findViewById(R.id.lobbyTitle_textView);
        lobbyMessage_TV = findViewById(R.id.lobbyMessage_textView);
        playerCount_TV = findViewById(R.id.playerCurrent_textView);

        gameName = extra.getString("gameName");
        gameID = extra.getString("gameID");

        lobbyTitle_TV.setText(gameName);
        currentNumPlayers = model.getGame(gameID).getNumPlayers();
        maxNumPlayers = model.getGame(gameID).getMaxPlayerNumber();
        playerCount_TV.setText(currentNumPlayers + "/" + maxNumPlayers);



    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.initObserver();
    }

    @Override
    public void onBackPressed(){
        presenter.removeObserver();
        presenter.leaveGame();
        super.onBackPressed();
    }

    @Override
    public void startGame() {
        //showToast("Starting game...");
        lobbyMessage_TV.setText(R.string.lobby_message_starting);

        if (gameName.equals("GAMEOVER"))
        {
            Intent intent = new Intent(this, GameOverActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }

    private void checkForStart(){
        if(currentNumPlayers == maxNumPlayers){
            presenter.startGame();
        }
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void refresh(){
        currentNumPlayers = model.getGame(gameID).getNumPlayers();
        playerCount_TV.setText(currentNumPlayers + "/" + maxNumPlayers);
        checkForStart();
    }
}
