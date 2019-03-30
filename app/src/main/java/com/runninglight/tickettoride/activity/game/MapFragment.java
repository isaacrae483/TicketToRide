package com.runninglight.tickettoride.activity.game;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.runninglight.shared.PlayerColor;
import com.runninglight.shared.Route;
import com.runninglight.tickettoride.IPresenter.game.IMapPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IMap_View;
import com.runninglight.tickettoride.presenter.game.MapPresenter;

import java.util.ArrayList;

public class MapFragment extends Fragment implements IMap_View, View.OnClickListener {

    private IMapPresenter presenter;

    private RelativeLayout map;




    public MapFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        presenter = new MapPresenter(this);

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        setListeners(v);


        return v;
    }



    @Override
    public void claimRoute(int route, PlayerColor color) {
        ImageView routeView;

        switch (route){
            case 20:
                routeView = getView().findViewById(R.id._20_1);
                break;
            case 21:
                routeView = getView().findViewById(R.id._21_1);
                break;
            case 33:
                routeView = getView().findViewById(R.id._33_1);
                break;
            case 37:
                routeView = getView().findViewById(R.id._37_1);
                break;
            case 50:
                routeView = getView().findViewById(R.id._50_1);
                break;
            case 99:
                routeView = getView().findViewById(R.id._99_1);
                break;
            default:
                String id = "_"+Integer.toString(route);
                routeView = getView().findViewById(getID(id));
                System.out.println("unknown route defaulting to miami to atlanta");
        }
        routeView.setVisibility(View.VISIBLE);
        routeView.setBackgroundResource(findColor(color));


    }

    @Override
    public void refresh(ArrayList<Route> allRoutes) {
        System.out.println("refreshing map");
        for (int i=0; i<allRoutes.size();i++){
            Route temp = allRoutes.get(i);
            if(temp.getClaimed() != null){
                for(int j=1; j<= temp.getLength(); j++){
                    String routeID ="_"+temp.getRouteNum()+"_"+j;
                    ImageView view = getActivity().findViewById(getID(routeID));  //TODO: check og claim route for stuff ;)
                    if(view != null)
                        view.setBackgroundResource(findColor(temp.getClaimed().getColor()));
                }
            }

        }
//TODO: hook up map to model
    }

    private int findColor(PlayerColor color){
        switch (color.toString())
        {
            case "GREEN":
                return R.color.player_green;
            case "RED":
                return R.color.player_red;
            case "BLUE":
                return R.color.player_blue;
            case "BLACK":
                return R.color.player_black;
            case "YELLOW":
                return R.color.player_yellow;
            default:
                System.out.println(color.toString());
                return R.color.player_black;
        }
    }

    private int getID(String identifier){
        Resources resources = getResources();
        int ID = resources.getIdentifier(identifier,"id",getContext().getPackageName());
        return ID;
    }


    private void setListeners(View v){

        ImageView [] cities = {v.findViewById(R.id.city1),v.findViewById(R.id.city2),
                v.findViewById(R.id.city3), v.findViewById(R.id.city4), v.findViewById(R.id.city5),
                v.findViewById(R.id.city6), v.findViewById(R.id.city7), v.findViewById(R.id.city8),
                v.findViewById(R.id.city9), v.findViewById(R.id.city10), v.findViewById(R.id.city11),
                v.findViewById(R.id.city12), v.findViewById(R.id.city13), v.findViewById(R.id.city14),
                v.findViewById(R.id.city15), v.findViewById(R.id.city16), v.findViewById(R.id.city17),
                v.findViewById(R.id.city18), v.findViewById(R.id.city19), v.findViewById(R.id.city20),
                v.findViewById(R.id.city21), v.findViewById(R.id.city22), v.findViewById(R.id.city23),
                v.findViewById(R.id.city24), v.findViewById(R.id.city25), v.findViewById(R.id.city26),
                v.findViewById(R.id.city27), v.findViewById(R.id.city28), v.findViewById(R.id.city29),
                v.findViewById(R.id.city30), v.findViewById(R.id.city31), v.findViewById(R.id.city32),
                v.findViewById(R.id.city33), v.findViewById(R.id.city34), v.findViewById(R.id.city35),
                v.findViewById(R.id.city36)} ;

        for (ImageView city : cities) {
            city.setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View v) {
        System.out.println("clicked city: " + v.getTag());
        Intent intent = new Intent(getActivity(), ClaimRouteActivity.class);
        intent.putExtra("title",v.getTag().toString());
        startActivity(intent);

    }
}
