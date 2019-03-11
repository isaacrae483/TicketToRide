package com.runninglight.tickettoride.activity.game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Player;
import com.runninglight.shared.PlayerColor;
import com.runninglight.tickettoride.IPresenter.game.IHandPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IHandView;
import com.runninglight.tickettoride.presenter.game.HandPresenter;

import java.util.ArrayList;


public class HandFragment extends Fragment implements IHandView {

    private ImageView destHand;
    private TextView destHandSize;

    private TextView numBlueText;
    private TextView numRedText;
    private TextView numGreenText;
    private TextView numYellowText;
    private TextView numOrangeText;
    private TextView numPinkText;
    private TextView numBlackText;
    private TextView numWhiteText;
    private TextView numWildText;

    private TextView currentplayer_name;
    private TextView currentplayer_traincards;
    private TextView currentplayer_destcards;
    private TextView currentplayer_carsleft;
    private TextView currentplayer_score;
    private ImageView currentplayer_image;

    private String TCARDS = "# Train Cards: ";
    private String DCARDS = "# Dest Cards: ";
    private String LEFT = "# Cars Left: ";
    private String SCORE = "Score: ";

    private IHandPresenter presenter;

    public HandFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = HandPresenter.getInstance();
        ((HandPresenter) presenter).addView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_hand, container, false);
        destHand = v.findViewById(R.id.dest_hand);
        destHandSize = v.findViewById(R.id.dest_hand_size);
        setDestHandListener();

        numBlackText = v.findViewById(R.id.num_black);
        numBlueText = v.findViewById(R.id.num_blue);
        numRedText = v.findViewById(R.id.num_red);
        numWhiteText = v.findViewById(R.id.num_white);
        numOrangeText = v.findViewById(R.id.num_orange);
        numYellowText = v.findViewById(R.id.num_yellow);
        numPinkText = v.findViewById(R.id.num_pink);
        numGreenText = v.findViewById(R.id.num_green);
        numWildText = v.findViewById(R.id.num_wild);

        currentplayer_name = v.findViewById(R.id.currentplayer_name);
        currentplayer_traincards = v.findViewById(R.id.currentplayer_traincards);
        currentplayer_destcards = v.findViewById(R.id.currentplayer_destcards);
        currentplayer_carsleft = v.findViewById(R.id.currentplayer_carsleft);
        currentplayer_score = v.findViewById(R.id.currentplayer_score);
        currentplayer_image = v.findViewById(R.id.currentplayer_image);

        numYellowText.setText(Integer.toString(0));
        numGreenText.setText(Integer.toString(0));
        numPinkText.setText(Integer.toString(0));
        numOrangeText.setText(Integer.toString(0));
        numWhiteText.setText(Integer.toString(0));
        numRedText.setText(Integer.toString(0));
        numBlueText.setText(Integer.toString(0));
        numBlackText.setText(Integer.toString(0));
        numWildText.setText(Integer.toString(0));

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.initObserver();
    }

    @Override
    public void onPause(){
        super.onPause();
        presenter.removeObserver();
    }

    public void setDestHandListener(){
        destHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DestCardHandActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void refreshDestCardCount(int numCards){
        destHandSize.setText(Integer.toString(numCards));
    }

    @Override
    public void updateHandNumbers(ArrayList<TrainCard> cards)
    {
        int red = 0;
        int blue = 0;
        int green = 0;
        int yellow = 0;
        int orange = 0;
        int pink = 0;
        int black = 0;
        int white = 0;
        int wild = 0;
        for (TrainCard trainCard : cards)
        {
            switch (trainCard.getCardColor())
            {
                case YELLOW: yellow++; break;
                case ORANGE: orange++; break;
                case GREEN: green++; break;
                case PINK: pink++; break;
                case BLUE: blue++; break;
                case RED: red++; break;
                case BLACK: black++; break;
                case WHITE: white++; break;
                case WILD: wild++; break;
            }
        }
        numYellowText.setText(Integer.toString(yellow));
        numGreenText.setText(Integer.toString(green));
        numPinkText.setText(Integer.toString(pink));
        numOrangeText.setText(Integer.toString(orange));
        numWhiteText.setText(Integer.toString(white));
        numRedText.setText(Integer.toString(red));
        numBlueText.setText(Integer.toString(blue));
        numBlackText.setText(Integer.toString(black));
        numWildText.setText(Integer.toString(wild));
    }

    @Override
    public void updateCurrentPlayerInfo(Player currentPlayer, String currentTurn) {
        currentplayer_name.setText(currentPlayer.getName());
        currentplayer_traincards.setText(TCARDS + Integer.toString(currentPlayer.getHandSize()));
        currentplayer_destcards.setText(DCARDS + Integer.toString(currentPlayer.getDestinationCards().size()));
        currentplayer_carsleft.setText(LEFT + Integer.toString(currentPlayer.getTrainCars()));
        currentplayer_score.setText(SCORE + Integer.toString(currentPlayer.getPoints()));
        PlayerColor color = currentPlayer.getColor();

        if (currentPlayer.getName().equals(currentTurn)) {
            currentplayer_image.setImageResource(getImageId(color, true));
        } else {
            currentplayer_image.setImageResource(getImageId(color, false));
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
