package com.runninglight.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.tickettoride.IPresenter.IGameList_Presenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.IGameList_View;
import com.runninglight.tickettoride.presenter.GameList_Presenter;

public class GameListFragment_ListView extends Fragment implements IGameList_View
{
    private ClientModel model = ClientModel.getInstance();
    private GameListAdapter_ListView adapter;
    private IGameList_Presenter presenter;
    private Handler handler;

    public GameListFragment_ListView()
    {

        presenter = new GameList_Presenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new GameListAdapter_ListView(getActivity(), model.getGameList());
    }

    @Override
    public void onResume(){
        super.onResume();
        startRefresher();
    }

    private void startRefresher(){
        handler = new Handler();
        final int delay = 2000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                adapter.refreshItems(model.getGameList());
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_gamelist_listview, container, false);

        final ListView gameList_LV = v.findViewById(R.id.gameList_listView);

        Button create_BTN = v.findViewById(R.id.create_button_gameList_listView);

        //TODO: pass game list in from the model
        //how to pass the game list down from the model without storing it?


        gameList_LV.setAdapter(adapter);
        gameList_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = gameList_LV.getItemAtPosition(position);
                Game game = (Game)o;
                presenter.joinGame(new GameInfo(game.getGameName(),game.getMaxPlayerNumber()));
            }
        });



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
        intent.putExtra("gameName",gameInfo.getGameName());
        intent.putExtra("maxPlayers",gameInfo.getMaxPlayerNumber());
        //TODO: add game info to the intent bundle
        startActivity(intent);

    }
}
