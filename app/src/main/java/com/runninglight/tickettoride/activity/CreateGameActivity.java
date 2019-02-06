package com.runninglight.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.ICreateGame_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.ICreateGame_View;
import com.runninglight.tickettoride.presenter.CreateGame_Presenter;

public class CreateGameActivity extends AppCompatActivity implements ICreateGame_View {

    private EditText gameName;
    private EditText playerNum;

    private ICreateGame_Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelobby);

        presenter = new CreateGame_Presenter(this);

        gameName = findViewById(R.id.gameName_editText);
        playerNum = findViewById(R.id.playerNumber_editText);

        Button create_BTN = findViewById(R.id.create_Button_create);
        Button cancel_BTN = findViewById(R.id.cancel_Button_create);

        create_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameInfo gameInfo = new GameInfo(gameName.getText().toString(),Integer.valueOf(playerNum.getText().toString()));
                presenter.createGame(gameInfo);
            }
        });

        cancel_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: return to previous activity... essentially the back button
            }
        });

    }



    @Override
    public void joinGame(GameInfo gameInfo) {
        Intent intent = new Intent(getApplicationContext(),GameLobbyActivity.class);
        startActivity(intent);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void onCreateSuccessful() {

    }
}
