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
import com.runninglight.tickettoride.iview.game.IGameActivity_View;
import com.runninglight.tickettoride.presenter.game.GameActivity_Presenter;

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

        presenter.initTurn();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            presenter.startMockup();
            return true;
        }
        return super.dispatchKeyEvent(e);
    }


    @Override
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void claimRoute(int route, PlayerColor color) {
        ImageView routeView;

        switch (route){
            case 20:
                routeView = findViewById(R.id._20);
                break;
            case 21:
                routeView = findViewById(R.id._21);
                break;
            case 33:
                routeView = findViewById(R.id._33);
                break;
            case 37:
                routeView = findViewById(R.id._37);
                break;
            case 50:
                routeView = findViewById(R.id._50);
                break;
            case 99:
                routeView = findViewById(R.id._99);
                break;
            default:
                routeView = findViewById(R.id._99);
                System.out.println("unknown route defaulting to miami to atlanta");
        }
        routeView.setVisibility(View.VISIBLE);
        routeView.setBackgroundResource(findColor(color));


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
