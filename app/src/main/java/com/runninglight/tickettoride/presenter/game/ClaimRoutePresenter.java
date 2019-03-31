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

    public ClaimRoutePresenter(IClaimRouteActivityView view) {
        addView(view);

    }

    public void addView(IClaimRouteActivityView activityView) {
        this.claimRouteActivityView = activityView;
    }

    @Override
    public void claimRoute(int routeNumber) {
        System.out.println("claiming route: " + routeNumber);
        Route temp = ClientModel.getInstance().getCurrentMap().getAllRoutes().get(routeNumber - 1);
        //route is not claimed
        if (temp.getClaimed() == null) {

                //has enough train cars
                if (ClientModel.getInstance().getCurrentPlayer().getTrainCars() >= temp.getLength()) {

                    //route is grey?
                    if(temp.getColor().toString() =="GREY"){
                        claimRouteActivityView.showToast("Route is Grey");
                        claimRouteActivityView.launchGrey(temp.toStringBasic(),temp.getRouteNum());
                        claimRouteActivityView.endActivity();
                    } else{
                        //has enough cards... color+wilds
                        if (ClientModel.getInstance().getCurrentPlayer().getHand().canClaimRoute(temp.getColor().toString(), temp.getLength())) {


                            proxy.claimRoute(model.getCurrentGameID(), model.getCurrentPlayer(), routeNumber, null);
                            claimRouteActivityView.showToast("Claiming route");
                            claimRouteActivityView.endActivity();


                        } else {
                            claimRouteActivityView.showToast("Not enough cards");
                        }
                    }

                } else {
                    claimRouteActivityView.showToast("Not enough Train cars.");
                }


        }else {
            claimRouteActivityView.showToast("Route already claimed");
        }



        }

    @Override
    public void claimGreyRoute(int routeNumber, String color) {
        //is claimed, enough cars and is grey already checked
        Route temp = ClientModel.getInstance().getCurrentMap().getAllRoutes().get(routeNumber - 1);

        if (ClientModel.getInstance().getCurrentPlayer().getHand().canClaimRoute(color, temp.getLength())) {


            proxy.claimRoute(model.getCurrentGameID(), model.getCurrentPlayer(), routeNumber, color);
            claimRouteActivityView.showToast("Claiming route");
            claimRouteActivityView.endActivity();


        } else {
            claimRouteActivityView.showToast("Not enough cards");
        }
    }


}
