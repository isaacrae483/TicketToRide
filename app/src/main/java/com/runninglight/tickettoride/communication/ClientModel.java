package com.runninglight.tickettoride.communication;

import android.util.Log;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.User;
import com.runninglight.tickettoride.presenter.game.DeckPresenter;

import java.util.ArrayList;
import java.util.Observable;

public class ClientModel extends Observable {

    private static ClientModel instance = null;

    private User currentUser;

    private Player currentPlayer;

    private Game currentGame;

    private ArrayList<Game> gameList;

    private ArrayList<User> userList;

    public static ClientModel getInstance(){
        if(instance == null){
            instance = new ClientModel();
        }
        return instance;
    }

    public void initGamesList(ArrayList<Game> games)
    {
        this.gameList = games;
        setChanged();
        notifyObservers(games);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getGame(String gameID) {
        for (Game game : gameList) {
            if (gameID.equals(game.getGameID())) {
                return game;
            }
        }
        return null;
    }

    public User getCurrentUser() { return currentUser; }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public String getCurrentGameID(){
        return currentGame.getGameID();
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void addUser(User u) {
        userList.add(u);
    }

    public void addUserToGame(User u, Game g) {
        for (Game game : gameList) {
            if (game.getGameID().equals(g.getGameID())) {
                game.addPlayer(u);
                game.setNumPlayers(g.getNumPlayers());
                setChanged();
                notifyObservers(g);
                System.out.println("Player added to Game " + game.getGameName() + " successfully");
            }
        }
    }

    public void removeUserFromGame(User u, Game g){
        for(Game game : gameList){
            if(game.getGameID().equals(g.getGameID())){
                game.removePlayer(u);
                setChanged();
                notifyObservers(g);
                System.out.println("Successfully removed " + u.getUserName() + " from " + g.getGameName());
            }
        }
    }

    public void addGame(Game g) {
        gameList.add(g);
        setChanged();
        notifyObservers(g);
    }

    public void addMessage(Message message, Game game)
    {
        for (Game g : gameList)
        {
            if (g.getGameID().equals(game.getGameID()))
            {
                Log.d("TTR.ClientModel", "adding message '" + message.getMessage() + "' to game: " + getCurrentGame());
                getCurrentGame().addMessage(message);
                setChanged();
                notifyObservers(message);
                break;
            }
        }
    }

    public ArrayList<Message> getMessages(Game game)
    {
        for (Game g : gameList)
        {
            if (g.getGameID().equals(game.getGameID()))
            {
                return g.getMessages();
            }
        }
        return null;
    }

    public void setDestinationCards(Game g, Player p){
        g.setDestinationCards(p.getName(), p.getDestinationCards().toArray(new DestinationCard[0]));
        currentGame = g;
        setChanged();
        notifyObservers((Integer)currentGame.getDeckSize());
    }

    public int getCurrentDestDeckSize(){
        return currentGame.getDestDeckSize();
    }

    public void addCardToFaceUp(Game game, TrainCard trainCard, int position)
    {
        if (getCurrentGame().getGameID().equals(game.getGameID()))
        {
            getCurrentGame().addCardToFaceUp(trainCard, position);
            DeckPresenter.getInstance().addCardToFaceUp(trainCard, position);
        }
    }
}
