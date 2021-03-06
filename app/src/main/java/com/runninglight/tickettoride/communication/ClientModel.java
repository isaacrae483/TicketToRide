package com.runninglight.tickettoride.communication;

import android.renderscript.ScriptGroup;
import android.util.Log;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.TrainCardDeck;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameOverAlert;
import com.runninglight.shared.Map;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.Route;
import com.runninglight.shared.User;
import com.runninglight.shared.state.PlayerState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class ClientModel extends Observable {

    private static ClientModel instance = null;

    private User currentUser;

    private Player currentPlayer;

    private Game currentGame;

    private Map currentMap;

    private TrainCardDeck currentTrainCardDeck;

    private ArrayList<Game> gameList;

    private ArrayList<User> userList;

    private boolean hasDrawnTrainCards = false;

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
        setChanged();
        notifyObservers(currentGame);
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

    public Message[] getMessages(Game game)
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
        if(p.getName().equals(currentPlayer.getName())){
            currentPlayer = p;
        }
        setChanged();
        notifyObservers((Integer)currentGame.getDeckSize());
    }

    public int getCurrentDestDeckSize(){
        return currentGame.getDestDeckSize();
    }

    /*
    public void decrementTrainCardDeckSize()
    {
        currentGame.decrementTrainCardDeckSize();
        setChanged();
        notifyObservers(currentGame.getTrainCardDeckSize());
    }

    public void getTrainCardDeckSize() { currentGame.getTrainCardDeckSize(); }

    public void addToTrainCardDeckSize(int increase)
    {
        currentGame.increaseTrainCardDeckSize(increase);
        currentGame.decrementTrainCardDeckSize();
        setChanged();
        notifyObservers(currentGame.getTrainCardDeckSize());
    }
    */

    public void addCardToFaceUp(Game game, TrainCard trainCard, int position)
    {
        if (getCurrentGame().getGameID().equals(game.getGameID()))
        {
            getCurrentGame().addCardToFaceUp(trainCard, position);
            setChanged();
            FaceUpCardUpdate faceUpCardUpdate = new FaceUpCardUpdate();
            faceUpCardUpdate.position = position;
            faceUpCardUpdate.trainCard = trainCard;
            notifyObservers(faceUpCardUpdate);
            //DeckPresenter.getInstance().addCardToFaceUp(trainCard, position);
        }
    }

    public class FaceUpCardUpdate
    {
        public TrainCard trainCard;
        public int position;
    }

    public String getCurrentTurn(){
        return currentGame.getTurnName();
    }

    public void setCurrentTurn(PlayerState playerState){
        currentGame.setPlayerState(playerState);
        System.out.println("*** Current turn: " + playerState.getPlayerName());
        setChanged();
        notifyObservers(playerState.getPlayerName());
    }

    public boolean isMyTurn(){
       return currentGame.isMyTurn(currentPlayer.getName());
    }

    public void nextTurn(){
        currentGame.continueGame();
        System.out.println("*** Current turn: " + currentGame.getTurnName());
        setChanged();
        notifyObservers(currentGame.getTurnName());
    }

    public boolean initDestCardsPicked(){
        return currentGame.initDestinationCardsPicked();
    }

    public void intiMap(InputStream stream){

        currentMap = new Map(stream);
    }

    public void addTrainCardToPlayerHand(TrainCard trainCard, User user, Game game)
    {
        if (currentPlayer.getName().equals(user.getUserName())) getCurrentPlayer().addCardToHand(trainCard);
        //game.getPlayer(user.getUserName()).addCardToHand(trainCard);
        setChanged();
        notifyObservers();
    }

    public void refreshCurrentPlayer(String playerName){
        if(currentPlayer.getName().equals(playerName)){
            currentPlayer = currentGame.getPlayer(playerName);
        }
    }

    public void addPointsToPlayer(String playerName, int points){
        currentGame.addPointsToPlayer(playerName, points);
        refreshCurrentPlayer(playerName);
        setChanged();
        notifyObservers();
    }

    public void addTrainCardToPlayer(String playerName, TrainCard card){
        currentGame.addTrainCardToPlayer(playerName, card);
        refreshCurrentPlayer(playerName);
        setChanged();
        notifyObservers();
    }

    public void removeTrainCardFromPlayer(String playerName, TrainCard card){
        currentGame.removeTrainCardFromPlayer(playerName, card);
        refreshCurrentPlayer(playerName);
        setChanged();
        notifyObservers();
    }

    public void addDestinationCardToPlayer(String playerName, DestinationCard card){
        DestinationCard[] cards = {card};
        currentGame.addDestinationCards(playerName, cards);
        refreshCurrentPlayer(playerName);
        setChanged();
        notifyObservers();
    }

    public void removeDestinationCardFromPlayer(String playerName, DestinationCard card){
        currentGame.removeDestinationCard(playerName, card);
        refreshCurrentPlayer(playerName);
        setChanged();
        notifyObservers();
    }

    public void addTrainCarsToPlayer(String playerName, int numCars){
        currentGame.addTrainCarsToPlayer(playerName, numCars);
        refreshCurrentPlayer(playerName);
        setChanged();
        notifyObservers();
    }

    public void claimRoute(int routeNumber){
       Route temp = currentMap.getAllRoutes().get(routeNumber-1);
       currentMap.claimRoute(routeNumber,getCurrentPlayer());
       System.out.println("in clmodel, preparing to create discard list for route: c1: "+temp.getCity1Name() + " c2: "+temp.getCity2Name());
       ArrayList<TrainCard> discard = currentPlayer.playTrainCards( temp.getColor().toString(),temp.getLength() );
       System.out.println("about to discard: " + discard.size()+ "to: "+ getCurrentGame().getTrainCardDeck());
       setChanged();
       notifyObservers();
    }

    public void claimRoute(int routeNumber, Player player){
        Route temp = currentMap.getAllRoutes().get(routeNumber-1);
        currentMap.claimRoute(routeNumber, player);
        System.out.println("in clmodel, preparing to create discard list for route: c1: "+temp.getCity1Name() + " c2: "+temp.getCity2Name());



        setChanged();
        notifyObservers();
    }

    public PlayerState getPlayerState(){
        return currentGame.getPlayerState();
    }

    public void signalEndGame(){
        setChanged();
        notifyObservers(new GameOverAlert());
    }

    public Map getCurrentMap(){return currentMap;}

    public boolean hasDrawnTrainCards() {
        return hasDrawnTrainCards;
    }

    public void setHasDrawnTrainCards(boolean hasDrawnTrainCards) {
        this.hasDrawnTrainCards = hasDrawnTrainCards;
    }
}
