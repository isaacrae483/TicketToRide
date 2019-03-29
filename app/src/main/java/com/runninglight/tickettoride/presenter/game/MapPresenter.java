package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Player;
import com.runninglight.shared.PlayerColor;
import com.runninglight.tickettoride.IPresenter.game.IMapPresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IMap_View;

import java.util.Observable;

public class MapPresenter implements IMapPresenter {

    private ClientModel model = ClientModel.getInstance();

    public MapPresenter(IMap_View view){
        map_view = view;
        model.addObserver(this);
    }

    private IMap_View map_view;

    @Override
    public void touchMap(int x, int y) {
        PlayerColor p1 = PlayerColor.RED;
        PlayerColor p2 = PlayerColor.GREEN;
        claimRoute(21,p1);
        claimRoute(50,p2);
        claimRoute(37,p1);
        claimRoute(33,p2);
        claimRoute(20,p1);
        claimRoute(99,p2);
    }

    @Override
    public void claimRoute(int route, PlayerColor color) {
        map_view.claimRoute(route,color);

    }

    @Override
    public void viewCity(String cityName) {

    }

    public void demo(){}

    @Override
    public void update(Observable o, Object arg) {
    map_view.refresh( ClientModel.getInstance().getCurrentMap().getAllRoutes());
    }
}
