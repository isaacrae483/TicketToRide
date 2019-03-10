package com.runninglight.tickettoride.activity.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.runninglight.shared.PlayerColor;
import com.runninglight.tickettoride.IPresenter.game.IMapPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IMap_View;
import com.runninglight.tickettoride.presenter.game.MapPresenter;

public class MapFragment extends Fragment implements IMap_View {

    private IMapPresenter presenter;

    private RelativeLayout map;




    public MapFragment(){}

    //TODO: note to self, there is a onclick attribute in image view, look into it


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

        map = v.findViewById(R.id.map_RL);

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                presenter.touchMap((int) event.getX(),(int) event.getY());
                return true;
            }
        });



        return v;
    }



    @Override
    public void claimRoute(int route, PlayerColor color) {
        ImageView routeView;

        switch (route){
            case 21:
                routeView = getView().findViewById(R.id._21);
                break;

            case 50:
                routeView = getView().findViewById(R.id._50);
                break;
            case 37:
                routeView = getView().findViewById(R.id._37);
                break;
            case 33:
                routeView = getView().findViewById(R.id._33);
                break;
            case 20:
                routeView = getView().findViewById(R.id._20);
                break;
            case 99:
                routeView = getView().findViewById(R.id._99);
                break;
            default:
                routeView = getView().findViewById(R.id._99);
                System.out.println("unknown route defaulting to miami to atlanta");
        }
        routeView.setVisibility(View.VISIBLE);
        routeView.setBackgroundResource(findColor(color));


    }

    @Override
    public void refresh() {

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

}
