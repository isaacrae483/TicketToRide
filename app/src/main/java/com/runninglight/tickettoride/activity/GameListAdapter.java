package com.runninglight.tickettoride.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.runninglight.shared.Game;
import com.runninglight.tickettoride.R;

import java.util.ArrayList;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder>  {

    public GameListAdapter(ArrayList<Game> games){
        myGames = games;

        Game game  = new Game("MyGame",3);
        ArrayList<Game> test = new ArrayList<>();
        test.add(game);
        myGames = test;
    }

    private ArrayList<Game> myGames;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View gameList = inflater.inflate(R.layout.recyclerview_row_gamelist,viewGroup,false);


        return new ViewHolder(gameList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Game game = myGames.get(i);

        TextView gameName = viewHolder.gameName;
        gameName.setText(game.getGameName());
        TextView currentPlayers = viewHolder.currentPlayerNum;

        currentPlayers.setText(game.getNumPlayers()+"/"+game.getMaxPlayerNumber());


    }

    @Override
    public int getItemCount() {
        return myGames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView gameName;
        public TextView currentPlayerNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameName = itemView.findViewById(R.id.gameName_textView_recyclerRow);
            currentPlayerNum = itemView.findViewById(R.id.currentPlayerNum_textView_recyclerRow);
        }
    }


}
