package com.runninglight.tickettoride.activity.game;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Route;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IClaimRouteActivityView;

import static com.runninglight.tickettoride.R.layout.recyclerview_row_gamelist;
import static com.runninglight.tickettoride.R.layout.row_claim_route;

public class ClaimRouteActivity extends AppCompatActivity implements IClaimRouteActivityView {

    private ClaimRouteAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_route);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        String sTitle = getIntent().getStringExtra("title");
        TextView title = findViewById(R.id.claim_route_title);
        title.setText(sTitle);

        adapter = new ClaimRouteAdapter(getApplicationContext(), ClientModel.getInstance().getMap().findRoutes(sTitle));

        //TODO:Set onclick listener for list
    }

    private class ClaimRouteAdapter extends ArrayAdapter<Route>{

        private Route[] items;

        public ClaimRouteAdapter(Context context, Route[] items) {
            super(context, R.layout.row_claim_route, items);
            this.items = items;
        }

        public View getView(int position, View view, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(row_claim_route, null, true);

            TextView routeTitle = rowView.findViewById(R.id.claim_route_row_title);

            routeTitle.setText(items[position].toString());


            return rowView;
        }

    }

}



