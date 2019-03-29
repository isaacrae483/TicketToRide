package com.runninglight.tickettoride.communication;

import android.util.Log;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.IClient;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.Route;
import com.runninglight.shared.User;
import com.runninglight.shared.state.PlayerState;

public class ClientFacade implements IClient {

    private ClientModel model = ClientModel.getInstance();

    private static ClientFacade instance;

    public static ClientFacade getInstance()
    {
        if (instance == null) instance = new ClientFacade();
        return instance;
    }

    @Override
    public void addPlayer(User u, Game g) {
        model.addUserToGame(u, g);
    }

    @Override
    public void removePlayer(User u, Game g){
        model.removeUserFromGame(u, g);
    }

    @Override
    public void addGame(Game g) {
        model.addGame(g);
    }

    @Override
    public void broadcastMessage(Message message, Game game)
    {
        model.addMessage(message, game);
    }

    @Override
    public void setDestinationCards(Game g, Player p){
        model.setDestinationCards(g, p);
    }

    @Override
    public void addCardToHand(Game game, User user, TrainCard trainCard)
    {
        model.setCurrentGame(game);
        Log.d("TAG", "adding card: " + trainCard.getCardColor() + " to user: " + user.getUserName());
        ClientModel.getInstance().addTrainCardToPlayerHand(trainCard, user, game);
    }

    @Override
    public void addCardToFaceUp(Game game, TrainCard trainCard, int position)
    {
        model.setCurrentGame(game);
        model.addCardToFaceUp(game, trainCard, position);
    }

    @Override
    public void setTurn(Game game, PlayerState playerState){
        model.setCurrentGame(game);
        model.setCurrentTurn(playerState);
    }

    @Override
    public void claimRoute(Game game, Player player, Route route)
    {
        // Alter model
    }
}
