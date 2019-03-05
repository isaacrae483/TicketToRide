package com.runninglight.tickettoride.activity.game;

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

import com.runninglight.tickettoride.R;

public class MapFragment extends Fragment {

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

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        map = v.findViewById(R.id.map_image_IV);

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()== MotionEvent.ACTION_UP){
                    Toast.makeText(getActivity(),(int) event.getX(),Toast.LENGTH_LONG);
                }
                return false;
            }
        });

        return v;
    }


    }
