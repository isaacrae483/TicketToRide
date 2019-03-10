package com.runninglight.tickettoride.activity.game;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runninglight.shared.Cards.TrainCard;
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

        numBlackText = v.findViewById(R.id.num_black);
        numBlueText = v.findViewById(R.id.num_blue);
        numRedText = v.findViewById(R.id.num_red);
        numWhiteText = v.findViewById(R.id.num_white);
        numOrangeText = v.findViewById(R.id.num_orange);
        numYellowText = v.findViewById(R.id.num_yellow);
        numPinkText = v.findViewById(R.id.num_pink);
        numGreenText = v.findViewById(R.id.num_green);
        numWildText = v.findViewById(R.id.num_wild);

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
}
