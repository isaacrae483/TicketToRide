package com.runninglight.tickettoride.activity.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Route;
import com.runninglight.tickettoride.IPresenter.game.IClaimRoutePresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IClaimRouteActivityView;
import com.runninglight.tickettoride.presenter.game.ClaimRoutePresenter;

import java.util.ArrayList;

import static com.runninglight.tickettoride.R.layout.recyclerview_row_gamelist;
import static com.runninglight.tickettoride.R.layout.row_claim_route;

public class ClaimRouteActivity extends AppCompatActivity implements IClaimRouteActivityView {

    private ClaimRouteAdapter adapter;
    private IClaimRoutePresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_route);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        presenter = new ClaimRoutePresenter(this);

        String sTitle = getIntent().getStringExtra("title");

        TextView title = findViewById(R.id.claim_route_title);
        ListView routes_lv = findViewById(R.id.routes_lv);

        title.setText(sTitle);

        adapter = new ClaimRouteAdapter(getApplicationContext(), ClientModel.getInstance().getCurrentMap().findRoutes(sTitle));
        routes_lv.setAdapter(adapter);

        routes_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Route curr = ClientModel.getInstance().getCurrentMap().getAllRoutes().get(position);

                presenter.claimRoute((int)view.getTag());
                finish();
            }
        });
    }

    private class ClaimRouteAdapter extends ArrayAdapter<Route>{

        private ArrayList<Route> items;

        public ClaimRouteAdapter(Context context, ArrayList<Route> items) {
            super(context, R.layout.row_claim_route, items);
            this.items = items;
        }

        public View getView(int position, View view, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(row_claim_route, null, true);

            TextView routeTitle = rowView.findViewById(R.id.claim_route_row_title);

            routeTitle.setText(items.get(position).toString());

            rowView.setTag(items.get(position).getRouteNum());

            String color = "route_"+ items.get(position).getColor().toString().toLowerCase();

            rowView.setBackgroundResource(getID(color));

            return rowView;
        }

    }

    private int getID(String identifier){
        Resources resources = getResources();
        int ID = resources.getIdentifier(identifier,"color",getPackageName());
        return ID;
    }


    @Override
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}



