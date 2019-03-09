package com.runninglight.tickettoride.activity.game;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.runninglight.tickettoride.IPresenter.game.IGameActivity_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.activity.LoginFragment;
import com.runninglight.tickettoride.iview.game.IGameActivity_View;
import com.runninglight.tickettoride.presenter.game.GameActivity_Presenter;

public class GameActivity extends AppCompatActivity implements IGameActivity_View {

    private IGameActivity_Presenter presenter;

    private MapFragment mapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_game);

        presenter = new GameActivity_Presenter(this);

        FragmentManager fm = this.getSupportFragmentManager();

         mapFragment= (MapFragment) fm.findFragmentById(R.id.map_frame);
        if(mapFragment==null){
            mapFragment= new MapFragment();
            fm.beginTransaction()
                    .add(R.id.map_frame,mapFragment)
                    .commit();
        }

        //TODO: see MainActivity for example of how to put fragments into frames

        //TODO: the other option is to change the frames to literal fragments once all the
        //TODO: fragments are done

        presenter.initTurn();
    }
}
