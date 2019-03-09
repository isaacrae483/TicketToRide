package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IMapPresenter;
import com.runninglight.tickettoride.iview.game.IMap_View;

public class MapPresenter implements IMapPresenter {

    public MapPresenter(IMap_View view){
        map_view = view;
    }

    private IMap_View map_view;

    @Override
    public void touchMap(int x, int y) {
        System.out.println("x: "+x +" y: "+ +y);
        //map_view.claimMiami();
    }

    @Override
    public void claimRoute(int route, String color) {

    }

    @Override
    public void viewCity(String cityName) {

    }

    public void demo(){}
}
