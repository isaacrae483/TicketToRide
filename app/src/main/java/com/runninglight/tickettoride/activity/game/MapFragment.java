package com.runninglight.tickettoride.activity.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.runninglight.tickettoride.IPresenter.game.IMapPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IMap_View;
import com.runninglight.tickettoride.presenter.game.MapPresenter;

public class MapFragment extends Fragment implements IMap_View {

    private IMapPresenter presenter;

    private RelativeLayout map;




    public MapFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        presenter = new MapPresenter(this);

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        map = v.findViewById(R.id.map_RL);

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                presenter.touchMap((int) event.getX(),(int) event.getY());
                return true;
            }
        });



        return v;
    }

    public void claimMiami(){
        ImageView route = getView().findViewById(R.id.miami);
        route.bringToFront();
        route.setVisibility(View.VISIBLE);
    }

    }
