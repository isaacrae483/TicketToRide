package com.runninglight.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.IGameList_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.IGameList_View;
import com.runninglight.tickettoride.presenter.GameList_Presenter;

public class GameListFragment extends Fragment implements IGameList_View {

    private IGameList_Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_gamelist, container, false);

        presenter = new GameList_Presenter(this);

        RecyclerView gameList_RV = v.findViewById(R.id.gameList_RecyclerView);

        Button create_BTN = v.findViewById(R.id.create_button_gameList);

        //TODO: pass game list in from the model
        //how to pass the game list down from the model without storing it?

        GameListAdapter adapter = new GameListAdapter(null);

        gameList_RV.setAdapter(adapter);
        gameList_RV.setLayoutManager(new LinearLayoutManager(getContext()));



        create_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createGame();
            }
        });


    return v;
    }



    @Override
    public void createGame() {

        Intent intent = new Intent(getContext(),CreateGameActivity.class);
        startActivity(intent);

    }

    @Override
    public void joinGameSuccessful(GameInfo gameInfo) {

        Intent intent = new Intent(getContext(),GameLobbyActivity.class);

        //TODO: add game info to the intent bundle
        startActivity(intent);

    }
}
