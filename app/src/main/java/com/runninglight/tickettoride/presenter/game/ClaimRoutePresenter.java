package com.runninglight.tickettoride.presenter.game;

import com.runninglight.tickettoride.IPresenter.game.IClaimRoutePresenter;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IClaimRouteActivityView;
import com.runninglight.tickettoride.iview.game.IDeckView;

import java.util.Observable;

public class ClaimRoutePresenter implements IClaimRoutePresenter {

    private static ClaimRoutePresenter instance;

    private ClientModel model = ClientModel.getInstance();

    private ServerProxy proxy = ServerProxy.getInstance();

    private IClaimRouteActivityView claimRouteActivityView;

    private   ClaimRoutePresenter(){
        ClientModel.getInstance().addObserver(this);

    }

    public void addView(IClaimRouteActivityView activityView)
    {
        this.claimRouteActivityView = activityView;
        ClientModel.getInstance().addObserver(this);
    }

    public static ClaimRoutePresenter getInstance()
    {
        if (instance == null) instance = new ClaimRoutePresenter();
        return instance;
    }

    @Override
    public void claimRoute(int routeNumber) {
        //TODO:Claim route via proxy?
    }

    @Override
    public void initObserver(){
        model.addObserver(this);
    }

    @Override
    public void removeObserver(){
        model.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        //on second thought this doesnt need to be observable
    }
}
