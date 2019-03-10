package com.runninglight.tickettoride.activity.game;

import android.app.ListActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.tickettoride.IPresenter.game.IDestCardHandPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IDestCardHandView;
import com.runninglight.tickettoride.presenter.game.DestCardHandPresenter;

public class DestCardHandActivity extends ListActivity implements IDestCardHandView {

    private IDestCardHandPresenter presenter;
    private DestCardHandAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest_card_hand);

        presenter = DestCardHandPresenter.getInstance();
        presenter.addView(this);

        this.adapter = new DestCardHandAdapter(this, R.layout.dest_card_hand_list_item, presenter.getCards());
        setListAdapter(adapter);
    }


    private class DestCardHandAdapter extends ArrayAdapter<DestinationCard> {

        private DestinationCard[] items;

        public DestCardHandAdapter(Context context, int textViewResourceId, DestinationCard[] items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.dest_card_hand_list_item, null);
            }

            DestinationCard card = items[position];
            if (card != null) {
                ImageView iv = (ImageView) v.findViewById(R.id.dest_card_hand_list_image);
                if (iv != null) {
                    int resID = getResources().getIdentifier(card.getImageResourceString(), "drawable", getPackageName());

                    iv.setImageResource(resID);
                }
            }

            return v;
        }
    }

}
