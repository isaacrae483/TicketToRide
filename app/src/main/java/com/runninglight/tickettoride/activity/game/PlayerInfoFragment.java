package com.runninglight.tickettoride.activity.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runninglight.shared.Player;
import com.runninglight.shared.PlayerColor;
import com.runninglight.tickettoride.IPresenter.game.IPlayerInfoPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IPlayerInfoView;
import com.runninglight.tickettoride.presenter.game.PlayerInfoPresenter;

import java.util.ArrayList;


public class PlayerInfoFragment extends Fragment implements IPlayerInfoView {

    private TextView player1_name;
    private TextView player1_traincards;
    private TextView player1_destcards;
    private TextView player1_carsleft;
    private TextView player1_score;
    private ImageView player1_image;

    private TextView player2_name;
    private TextView player2_traincards;
    private TextView player2_destcards;
    private TextView player2_carsleft;
    private TextView player2_score;
    private ImageView player2_image;

    private TextView player3_name;
    private TextView player3_traincards;
    private TextView player3_destcards;
    private TextView player3_carsleft;
    private TextView player3_score;
    private ImageView player3_image;

    private TextView player4_name;
    private TextView player4_traincards;
    private TextView player4_destcards;
    private TextView player4_carsleft;
    private TextView player4_score;
    private ImageView player4_image;

    private String TCARDS = "# Train Cards: ";
    private String DCARDS = "# Dest Cards: ";
    private String LEFT = "# Cars Left: ";
    private String SCORE = "Score: ";


    IPlayerInfoPresenter presenter;

    public PlayerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PlayerInfoPresenter.getInstance();
        ((PlayerInfoPresenter) presenter).addView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_playerinfo, container, false);

        presenter.initObserver();
        player1_name = v.findViewById(R.id.player1_name);
        player1_traincards = v.findViewById(R.id.player1_traincards);
        player1_destcards = v.findViewById(R.id.player1_destcards);
        player1_carsleft = v.findViewById(R.id.player1_carsleft);
        player1_score = v.findViewById(R.id.player1_score);
        player1_image = v.findViewById(R.id.player1_image);

        player2_name = v.findViewById(R.id.player2_name);
        player2_traincards = v.findViewById(R.id.player2_traincards);
        player2_destcards = v.findViewById(R.id.player2_destcards);
        player2_carsleft = v.findViewById(R.id.player2_carsleft);
        player2_score = v.findViewById(R.id.player2_score);
        player2_image = v.findViewById(R.id.player2_image);

        player3_name = v.findViewById(R.id.player3_name);
        player3_traincards = v.findViewById(R.id.player3_traincards);
        player3_destcards = v.findViewById(R.id.player3_destcards);
        player3_carsleft = v.findViewById(R.id.player3_carsleft);
        player3_score = v.findViewById(R.id.player3_score);
        player3_image = v.findViewById(R.id.player3_image);

        player4_name = v.findViewById(R.id.player4_name);
        player4_traincards = v.findViewById(R.id.player4_traincards);
        player4_destcards = v.findViewById(R.id.player4_destcards);
        player4_carsleft = v.findViewById(R.id.player4_carsleft);
        player4_score = v.findViewById(R.id.player4_score);
        player4_image = v.findViewById(R.id.player4_image);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.initObserver();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.removeObserver();
    }

    @Override
    public void updatePlayerInfo(ArrayList<Player> players, Player currentPlayer, String currentTurn) {
        int i = 1;
        for (Player p : players) {
            if (!p.getName().equals(currentPlayer.getName())) {
                if (i == 1) {
                    setPlayer1(p, currentTurn);
                    i++;
                } else if (i == 2) {
                    setPlayer2(p, currentTurn);
                    i++;
                } else if (i == 3) {
                    setPlayer3(p, currentTurn);
                    i++;
                } else {
                    setPlayer4(p, currentTurn);
                    i++;
                }
            }
        }
    }

    private void setPlayer1(Player p, String currentTurn) {
        player1_name.setText(p.getName());
        player1_traincards.setText(TCARDS + Integer.toString(p.getHandSize()));
        player1_destcards.setText(DCARDS + Integer.toString(p.getDestinationCards().size()));
        player1_carsleft.setText(LEFT + Integer.toString(p.getTrainCars()));
        player1_score.setText(SCORE + Integer.toString(p.getPoints()));
        PlayerColor color = p.getColor();

        if (p.getName().equals(currentTurn)) {
            player1_image.setImageResource(getImageId(color, true));
        } else {
            player1_image.setImageResource(getImageId(color, false));
        }
    }

    private void setPlayer2(Player p, String currentTurn) {
        player2_name.setText(p.getName());
        player2_traincards.setText(TCARDS + Integer.toString(p.getHandSize()));
        player2_destcards.setText(DCARDS + Integer.toString(p.getDestinationCards().size()));
        player2_carsleft.setText(LEFT + Integer.toString(p.getTrainCars()));
        player2_score.setText(SCORE + Integer.toString(p.getPoints()));
        PlayerColor color = p.getColor();

        if (p.getName().equals(currentTurn)) {
            player2_image.setImageResource(getImageId(color, true));
        } else {
            player2_image.setImageResource(getImageId(color, false));
        }
    }

    private void setPlayer3(Player p, String currentTurn) {
        player3_name.setText(p.getName());
        player3_traincards.setText(TCARDS + Integer.toString(p.getHandSize()));
        player3_destcards.setText(DCARDS + Integer.toString(p.getDestinationCards().size()));
        player3_carsleft.setText(LEFT + Integer.toString(p.getTrainCars()));
        player3_score.setText(SCORE + Integer.toString(p.getPoints()));
        PlayerColor color = p.getColor();

        if (p.getName().equals(currentTurn)) {
            player3_image.setImageResource(getImageId(color, true));
        } else {
            player3_image.setImageResource(getImageId(color, false));
        }
    }

    private void setPlayer4(Player p, String currentTurn) {
        player4_name.setText(p.getName());
        player4_traincards.setText(TCARDS + Integer.toString(p.getHandSize()));
        player4_destcards.setText(DCARDS + Integer.toString(p.getDestinationCards().size()));
        player4_carsleft.setText(LEFT + Integer.toString(p.getTrainCars()));
        player4_score.setText(SCORE + Integer.toString(p.getPoints()));
        PlayerColor color = p.getColor();

        if (p.getName().equals(currentTurn)) {
            player4_image.setImageResource(getImageId(color, true));
        } else {
            player4_image.setImageResource(getImageId(color, false));
        }
    }

    private int getImageId(PlayerColor color, boolean isTurn) {
        switch (color) {
            case BLACK:
                if (isTurn) {
                    return R.drawable.black;
                } else {
                    return R.drawable.black_turn;
                }

            case RED:
                if (isTurn) {
                    return R.drawable.red_turn;
                } else {
                    return R.drawable.red;
                }

            case GREEN:
                if (isTurn) {
                    return R.drawable.green_turn;
                } else {
                    return R.drawable.green;
                }

            case BLUE:
                if (isTurn) {
                    return R.drawable.blue_turn;
                } else {
                    return R.drawable.blue;
                }

            case YELLOW:
                if (isTurn) {
                    return R.drawable.yellow_turn;
                } else {
                    return R.drawable.yellow;
                }

            default:
                return R.drawable.black;
        }
    }
}
