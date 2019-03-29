package com.runninglight.tickettoride.activity.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.runninglight.shared.Player;
import com.runninglight.shared.PlayerCompare;
import com.runninglight.tickettoride.IPresenter.game.IGameOverPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.activity.MainActivity;
import com.runninglight.tickettoride.iview.game.IGameOverView;
import com.runninglight.tickettoride.presenter.game.GameOverPresenter;

import java.util.ArrayList;
import java.util.Collections;

public class GameOverActivity extends AppCompatActivity implements IGameOverView
{
    ArrayList<ArrayList<View>> views;
    IGameOverPresenter gameOverPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        initView();
        gameOverPresenter = new GameOverPresenter(this);
    }

    private void initView()
    {
        findViewById(R.id.button_return_to_lobby).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        views = new ArrayList<>();

        ArrayList<View> p1 = new ArrayList<>();
        p1.add(findViewById(R.id.p1_rank_view));
        p1.add(findViewById(R.id.p1_player_view));
        p1.add(findViewById(R.id.p1_total_points_view));
        p1.add(findViewById(R.id.p1_dc_gained_view));
        p1.add(findViewById(R.id.p1_dc_lost_view));
        p1.add(findViewById(R.id.p1_largest_path_view));
        views.add(p1);

        ArrayList<View> p2 = new ArrayList<>();
        p2.add(findViewById(R.id.p2_rank_view));
        p2.add(findViewById(R.id.p2_player_view));
        p2.add(findViewById(R.id.p2_total_points_view));
        p2.add(findViewById(R.id.p2_dc_gained_view));
        p2.add(findViewById(R.id.p2_dc_lost_view));
        p2.add(findViewById(R.id.p2_largest_path_view));
        views.add(p2);

        ArrayList<View> p3 = new ArrayList<>();
        p3.add(findViewById(R.id.p3_rank_view));
        p3.add(findViewById(R.id.p3_player_view));
        p3.add(findViewById(R.id.p3_total_points_view));
        p3.add(findViewById(R.id.p3_dc_gained_view));
        p3.add(findViewById(R.id.p3_dc_lost_view));
        p3.add(findViewById(R.id.p3_largest_path_view));
        views.add(p3);

        ArrayList<View> p4 = new ArrayList<>();
        p4.add(findViewById(R.id.p4_rank_view));
        p4.add(findViewById(R.id.p4_player_view));
        p4.add(findViewById(R.id.p4_total_points_view));
        p4.add(findViewById(R.id.p4_dc_gained_view));
        p4.add(findViewById(R.id.p4_dc_lost_view));
        p4.add(findViewById(R.id.p4_largest_path_view));
        views.add(p4);

        ArrayList<View> p5 = new ArrayList<>();
        p5.add(findViewById(R.id.p5_rank_view));
        p5.add(findViewById(R.id.p5_player_view));
        p5.add(findViewById(R.id.p5_total_points_view));
        p5.add(findViewById(R.id.p5_dc_gained_view));
        p5.add(findViewById(R.id.p5_dc_lost_view));
        p5.add(findViewById(R.id.p5_largest_path_view));
        views.add(p5);
    }

    @Override
    public void setPlayerPoints(ArrayList<Player> players)
    {
        ArrayList<Player> mostRoutesPlayers = new ArrayList<>();
        int maxRoutes = -1;
        for (Player p : players) {
            if (p.getClaimedRoutes().size() > maxRoutes) {
                mostRoutesPlayers.clear();
                mostRoutesPlayers.add(p);
                maxRoutes = p.getClaimedRoutes().size();
            }
            else if (p.getClaimedRoutes().size() == maxRoutes) {
                mostRoutesPlayers.add(p);
            }
        }
        for (Player p : mostRoutesPlayers) {
            p.setHasMostRoutes(true);
        }

        for (Player p : players) {
            p.calculateTotalPoints();
        }

        Collections.sort(players, new PlayerCompare());

        for (int i = 0; i < players.size(); i++)
        {
            ((TextView) views.get(i).get(0)).setText(String.valueOf(i + 1));
            ((TextView) views.get(i).get(1)).setText(players.get(i).getName());
            ((TextView) views.get(i).get(2)).setText(String.valueOf(players.get(i).getPoints()));
            ((TextView) views.get(i).get(3)).setText(String.valueOf(players.get(i).getDestCardsGained()));
            ((TextView) views.get(i).get(4)).setText(String.valueOf(players.get(i).getDestCardsLost()));
            String mostRoutesPoints = "0";
            if (players.get(i).getHasMostRoutes()) {
                mostRoutesPoints = "10";
            }
            ((TextView) views.get(i).get(5)).setText(mostRoutesPoints);
        }
    }

    @Override
    public void onBackPressed(){

    }
}
