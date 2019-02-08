package com.runninglight.tickettoride.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.runninglight.shared.Game;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.runninglight.tickettoride.R.layout.recyclerview_row_gamelist;


public class GameListAdapter_ListView extends ArrayAdapter<Game>{

    private final Activity context;
    private  Game[] games;


    public GameListAdapter_ListView (Activity context,Game[] myGames){

        super(context, recyclerview_row_gamelist , ClientModel.getInstance().getGameList().toArray(new Game[0]));

        this.context = context;
        games = ClientModel.getInstance().getGameList().toArray(new Game[0]); ;

    }


    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(recyclerview_row_gamelist, null, true);

        TextView gameName = rowView.findViewById(R.id.gameName_textView_recyclerRow);
        TextView currentPlayers = rowView.findViewById(R.id.currentPlayerNum_textView_recyclerRow);

        gameName.setText(games[position].getGameName());
        currentPlayers.setText(games[position].getNumPlayers() + "/" + games[position].getMaxPlayerNumber());


        return rowView;
    }

    }
