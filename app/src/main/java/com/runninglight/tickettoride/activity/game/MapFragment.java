package com.runninglight.tickettoride.activity.game;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.runninglight.tickettoride.IPresenter.game.IMapPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IMap_View;
import com.runninglight.tickettoride.presenter.game.MapPresenter;

public class MapFragment extends Fragment implements IMap_View {

    private IMapPresenter presenter;

    private ImageView map;


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

        map = v.findViewById(R.id.map_image_IV);
        BitmapDrawable drawable = (BitmapDrawable) map.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        map.setImageBitmap(bitmap);
        map.setScaleType(ImageView.ScaleType.FIT_XY);

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                presenter.touchMap((int) event.getX(),(int) event.getY());
                return false;
            }
        });



        return v;
    }


    }
