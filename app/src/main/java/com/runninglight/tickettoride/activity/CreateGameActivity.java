package com.runninglight.tickettoride.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.ICreateGame_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.ICreateGame_View;
import com.runninglight.tickettoride.presenter.CreateGame_Presenter;

public class CreateGameActivity extends AppCompatActivity implements ICreateGame_View {

    private EditText gameName;
    private EditText playerNum;
    private Button create_BTN;
    private Button cancel_BTN;

    private ICreateGame_Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);

        presenter = new CreateGame_Presenter(this);

        gameName = findViewById(R.id.gameName_editText);
        playerNum = findViewById(R.id.playerNumber_editText);

        create_BTN = findViewById(R.id.create_Button_create);
        cancel_BTN = findViewById(R.id.cancel_Button_create);


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
                cancel();
            }
        });



    }



    @Override
    public void joinGame(GameInfo gameInfo) {
        Intent intent = new Intent(getApplicationContext(),GameLobbyActivity.class);
        //TODO: add game info to intent bundle
        startActivity(intent);
    }

    @Override
    public void cancel() {
        finish();
    }

    @Override
    public void onCreateSuccessful() {


        finish();
        //TODO: join the newly created game
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
