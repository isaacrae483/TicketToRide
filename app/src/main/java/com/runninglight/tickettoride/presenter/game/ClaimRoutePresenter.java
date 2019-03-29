package com.runninglight.tickettoride.presenter.game;

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
        //TODO:Claim route via proxy?
        System.out.println("attempting to claim route: "+ routeNumber +"stopping at Presenter for now");
    }


}
