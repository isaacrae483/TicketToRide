package com.runninglight.tickettoride.activity.game;

import android.content.Context;
import android.content.res.Resources;
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

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Route;
import com.runninglight.tickettoride.IPresenter.game.IClaimRoutePresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IClaimRouteActivityView;
import com.runninglight.tickettoride.presenter.game.ClaimRoutePresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.runninglight.tickettoride.R.layout.row_claim_route;

public class ClaimRouteGreyActivity extends AppCompatActivity  implements IClaimRouteActivityView {

    private String[] items = {"PINK", "WHITE", "BLUE", "YELLOW", "ORANGE", "BLACK", "RED", "GREEN","WILD"};
    private ArrayList<String> strings = new ArrayList<String>(Arrays.asList(items));

    private ArrayAdapter adapter;
    private IClaimRoutePresenter presenter;
    ListView routes_lv;
    TextView title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_route);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        presenter = new ClaimRoutePresenter(this);

        String sTitle = getIntent().getStringExtra("title");
        int routeNum = getIntent().getIntExtra("routeNum",0);

        title = findViewById(R.id.claim_route_title);
        routes_lv = findViewById(R.id.routes_lv);

        title.setText(sTitle);
        title.setBackgroundResource(R.color.route_grey);
        title.setTag(routeNum);

        adapter =new ClaimGreyRouteAdapter(this);
        routes_lv.setAdapter(adapter);

        routes_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.claimGreyRoute((Integer) title.getTag(), view.getTag().toString());
            }
        });
    }




    private class ClaimGreyRouteAdapter extends ArrayAdapter<String>{
        ClaimGreyRouteAdapter(Context context) {
            super(context, R.layout.row_claim_route,strings);




        }

        @NonNull
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(row_claim_route, null, true);

            TextView routeTitle = rowView.findViewById(R.id.claim_route_row_title);

            String rowText = strings.get(position).toLowerCase() +": " + getCardNumber(strings.get(position),ClientModel.getInstance().getCurrentPlayer().getHand().getTrainCards());
            System.out.println(rowText);

            routeTitle.setText(rowText);

            rowView.setTag(strings.get(position));

            return rowView;
        }

        //"PINK", "WHITE", "BLUE", "YELLOW", "ORANGE", "BLACK", "RED", "GREEN"
        int getCardNumber(String color, ArrayList<TrainCard>hand){

            int red = 0;
            int blue = 0;
            int green = 0;
            int yellow = 0;
            int orange = 0;
            int pink = 0;
            int black = 0;
            int white = 0;
            int wild = 0;
            for (TrainCard trainCard : hand)
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
            switch (color){
                case "PINK":
                    return pink;

                case "WHITE":
                    return white;

                case "BLUE":
                    return blue;

                case "YELLOW":
                    return yellow;

                case "ORANGE":
                   return orange;

                case "BLACK":
                    return black;

                case "RED":
                   return red;

                case "GREEN":
                    return green;

                case "WILD":
                    return wild;

                default:
                    return 0;

            }

        }

    }

    public void launchGrey(String route, int routeNum){
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

    public void endActivity(){
        finish();
    }

}
