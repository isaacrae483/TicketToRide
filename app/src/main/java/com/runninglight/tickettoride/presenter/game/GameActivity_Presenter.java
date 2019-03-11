package com.runninglight.tickettoride.presenter.game;

import android.os.Handler;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.City;
import com.runninglight.tickettoride.IPresenter.game.IGameActivity_Presenter;
import com.runninglight.tickettoride.activity.game.GameActivity;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IGameActivity_View;

import java.util.Observable;

public class GameActivity_Presenter implements IGameActivity_Presenter {

    private ClientModel model = ClientModel.getInstance();

    public GameActivity_Presenter(IGameActivity_View v){
        view = v;
    }

    private IGameActivity_View view;

    /**
     * The person who goes first is the person who joined the room first
     */
    public void initTurn(){
        model.setCurrentTurn(model.getCurrentGame().getPlayerList().get(0).getName());
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void startMockup(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            int step = 0;
            @Override
            public void run(){
                switch(step){
                    case 0:{
                        view.showToast("The demo will start now!");
                        break;
                    }
                    case 1:{
                        view.showToast("Player 1 will gain 5 points");
                        break;
                    }
                    case 2:{
                        model.addPointsToPlayer("player1", 5);
                        break;
                    }
                    case 3:{
                        view.showToast("Player 1 will gain 1 red train card");
                        break;
                    }
                    case 4:{
                        model.addTrainCardToPlayer("player1", new TrainCard(CardColor.RED));
                        break;
                    }
                    case 5:{
                        view.showToast("Player 1 will lose 1 red train card");
                        break;
                    }
                    case 6:{
                        model.removeTrainCardFromPlayer("player1", new TrainCard(CardColor.RED));
                        break;
                    }
                    case 7:{
                        view.showToast("Player 1 will gain 1 destination card");
                        break;
                    }
                    case 8:{
                        model.addDestinationCardToPlayer("player1",
                                new DestinationCard(new City("Boston"), new City("New_York")));
                        break;
                    }
                    case 9:{
                        view.showToast("Player 1 will lose 1 destination card");
                        break;
                    }
                    case 10:{
                        model.removeDestinationCardFromPlayer("player1",
                                new DestinationCard(new City("Boston"), new City("New_York")));
                        break;
                    }
                    case 11:{
                        view.showToast("Player 2 will gain 1 red train card");
                        break;
                    }
                    case 12:{
                        model.addTrainCardToPlayer("player2", new TrainCard(CardColor.RED));
                        break;
                    }
                    case 13:{
                        view.showToast("Player 2 will gain 10 train cars");
                        break;
                    }
                    case 14:{
                        model.addTrainCarsToPlayer("player2", 10);
                        break;
                    }
                    case 15:{
                        view.showToast("Player 2 will gain 1 destination card");
                        break;
                    }
                    case 16:{
                        model.addDestinationCardToPlayer("player2",
                                new DestinationCard(new City("Boston"), new City("New_York")));
                        break;
                    }
                }
                step++;
                handler.postDelayed(this, 5000);
            }
        },5000);
    }


}
