package com.runninglight.tickettoride.presenter.game;

import com.runninglight.shared.Route;
import com.runninglight.tickettoride.IPresenter.game.IClaimRoutePresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IClaimRouteActivityView;
import com.runninglight.tickettoride.iview.game.IDeckView;

import java.util.Observable;

public class ClaimRoutePresenter implements IClaimRoutePresenter {


    private ClientModel model = ClientModel.getInstance();

    private ServerProxy proxy = ServerProxy.getInstance();

    private IClaimRouteActivityView claimRouteActivityView;

    public ClaimRoutePresenter(IClaimRouteActivityView view){
        addView(view);

    }

    public void addView(IClaimRouteActivityView activityView)
    {
        this.claimRouteActivityView = activityView;
    }

    @Override
    public void claimRoute(int routeNumber) {
        System.out.println("claiming route: "+ routeNumber);
        Route temp = ClientModel.getInstance().getCurrentMap().getAllRoutes().get(routeNumber-1);
        //short circuit
        if(ClientModel.getInstance().getCurrentPlayer().getHand().canClaimRoute(temp.getColor().toString(), temp.getLength())){
            if(temp.getClaimed() == null){
                ClientModel.getInstance().claimRoute(routeNumber);
            }
            else{
                System.out.println("Route already claimed");
            }

        }
        else
            System.out.println("not enough cards");

    }


}
