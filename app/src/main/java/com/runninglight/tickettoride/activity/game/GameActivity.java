package com.runninglight.tickettoride.activity.game;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.runninglight.shared.PlayerColor;
import com.runninglight.tickettoride.IPresenter.game.IGameActivity_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IGameActivity_View;
import com.runninglight.tickettoride.presenter.game.GameActivity_Presenter;

import java.io.IOException;

public class GameActivity extends AppCompatActivity implements IGameActivity_View {

    private IGameActivity_Presenter presenter;

    private Fragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game);

        presenter = new GameActivity_Presenter(this);

        String[] files = getApplicationContext().fileList();
        for (String file : files) {
            System.out.print(", " + file);
        }
        try {
            ClientModel.getInstance().getCurrentGame().initMapClient(getAssets().open("routes.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



    private int findColor(PlayerColor color){
        switch (color.toString())
        {
            case "GREEN":
                return R.color.player_green;
            case "RED":
                return R.color.player_red;
            case "BLUE":
                return R.color.player_blue;
            case "BLACK":
                return R.color.player_black;
            case "YELLOW":
                return R.color.player_yellow;
            default:
                System.out.println(color.toString());
                return R.color.player_black;
        }
    }
}
