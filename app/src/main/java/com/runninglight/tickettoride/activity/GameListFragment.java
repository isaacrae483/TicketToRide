package com.runninglight.tickettoride.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.runninglight.tickettoride.R;

public class GameListFragment extends Fragment {

    private RecyclerView gameList_RV;
    private Button create_BTN;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_gamelist, container, false);

        gameList_RV = v.findViewById(R.id.gameList_RecyclerView);

        create_BTN = v.findViewById(R.id.create_button_gameList);



        create_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add presenter and launch new createGame Activity
            }
        });


    return v;
    }

}
