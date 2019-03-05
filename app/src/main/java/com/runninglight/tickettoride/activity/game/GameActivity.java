package com.runninglight.tickettoride.activity.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.runninglight.tickettoride.IPresenter.game.IGameActivity_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IGameActivity_View;
import com.runninglight.tickettoride.presenter.game.GameActivity_Presenter;

public class GameActivity extends AppCompatActivity implements IGameActivity_View {

    private IGameActivity_Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        presenter = new GameActivity_Presenter(this);

        //TODO: see MainActivity for example of how to put fragments into frames

        //TODO: the other option is to change the frames to literal fragments once all the
        //TODO: fragments are done


    }
}
