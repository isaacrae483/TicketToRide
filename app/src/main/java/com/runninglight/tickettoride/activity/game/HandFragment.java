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

import com.runninglight.tickettoride.IPresenter.game.IHandPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IHandView;
import com.runninglight.tickettoride.presenter.game.HandPresenter;


public class HandFragment extends Fragment implements IHandView {

    private ImageView destHand;
    private TextView destHandSize;

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
        return v;
    }

    @Override
    public void refreshDestCardCount(int numCards){
        destHandSize.setText(Integer.toString(numCards));
    }
}
