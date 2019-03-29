package com.runninglight.shared;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.DestinationCardDeck;
import com.runninglight.shared.Cards.FaceUpCards;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.TrainCardDeck;
import com.runninglight.shared.state.DuringGameState;
import com.runninglight.shared.state.FinishingGameState;
import com.runninglight.shared.state.IGameState;
import com.runninglight.shared.state.PlayerState;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Game {

    /** Name of the game room */
    private String gameName;

    /** Unique 8-digit hex ID for the game room */
    private String gameID;

    /** List of Users in the game room */
    private ArrayList<User> userList;

    /** List of Players in the game room */
    private ArrayList<Player> playerList;

    /** Collection of chat messages */
    private Chat chat;

    /** Destination Card Deck */
    private transient DestinationCardDeck destCardDeck;

    /** Number of cards in Destination Card Deck */
    private int destDeckSize;

    /** Maximum number of Users allowed in the game room */
    private int maxPlayerNumber;

    /** Current number of players in the game room */
    private int numPlayers;

    /** Indicates the last index of the hex ID */
    private static final int ID_END = 7;

    /** Starting number of train cars */
    private static final int MAX_TRAIN_CARS = 45;

    /** Ending number of train cars */
    private static final int END_TRAIN_CARS = 2;

    private transient TrainCardDeck trainCardDeck;

    /** The face up train cards for the game */
    private FaceUpCards faceUpCards;

    /** The number of cards in the train card deck */
    private int trainCardDeckCurrentSize;

    /** Current turn of the game */
    private PlayerState playerState;

    /** Current state of the game */
    transient private IGameState gameState;

    /** String representation of gameState -- used for deserializing */
    private String[] gameStateData;

    private boolean initDestCardsPicked;

    private Map map;

    /**
     * Game constructor
     *
     * @param gameName name of the game room
     * @param maxPlayerNumber maximum number of players allowed in the game room
     *
     * @pre gameName != null
     * @post Creates a Game object with the specified name and maxPlayerNumber and generates an ID
     */
    public Game(String gameName, int maxPlayerNumber) {
        this.gameName = gameName;
        this.maxPlayerNumber = maxPlayerNumber;
        this.numPlayers = 0;
        this.userList = new ArrayList<>();
        this.gameID = generateID();
        this.destCardDeck = new DestinationCardDeck();
        this.destDeckSize = this.destCardDeck.size();
        this.chat = new Chat();
        this.playerList = new ArrayList<>();
        this.trainCardDeck = new TrainCardDeck();
        this.faceUpCards = new FaceUpCards();
        this.trainCardDeckCurrentSize = 110;
        this.initDestCardsPicked = false;
        this.gameState = new DuringGameState();
        this.gameStateData = new String[]{"DuringGameState", null};
    }


    /**
     * Adds a User to the userList and a Player to the playerList
     * @param user User to add
     *
     * @pre none
     * @post Adds a User to the userList and a Player to the playerList
     *      numPlayers += 1
     */
    public void addPlayer(User user){
        if(user != null) {
            userList.add(user);
            playerList.add(new Player(user.getUserName(), MAX_TRAIN_CARS, PlayerColor.values()[numPlayers]));
            ++numPlayers;
            if(numPlayers == 1) {
                playerState = new PlayerState(playerList.get(0).getName(), numPlayers);
            }
            else if(numPlayers > 1){
                playerState.setTotalPlayers(numPlayers);
            }
        }
    }

    /**
     * Removes a User from the userList
     * @param user User to remove
     *
     * @pre none
     * @post Removes a User from the userList and a Player from the playerList if they exist
     *      numPlayers -= 1
     */
    public void removePlayer(User user){
        if(user != null) {
            int userIndex = find(user.getUserName());
            if (userIndex != -1) {
                userList.remove(userIndex);
                playerList.remove(userIndex);
                numPlayers--;
                playerState.setTotalPlayers(numPlayers);
            }
        }
    }

    /**
     * Pulls an array of DestinationCards from the DestinationCardDeck
     *
     * @param numCards number of cards to draw
     * @return an array of DestinationCards with size == numCards if the deck allows,
     *          size < numCards if there aren't enough cards in the deck
     *
     * @pre destCardDeck != null
     *      numCards >= 0
     * @post a DestinationCard[] of size <= numCards will be returned
     *      destCardDeck's size -= cards.length
     */
    public DestinationCard[] drawDestCards(int numCards){
        DestinationCard[] cards = new DestinationCard[numCards];
        int index = 0;      // Use this to keep track of how many cards were actually drawn

        try {
            for (index = 0; index < numCards; index++) {
                cards[index] = destCardDeck.drawCard();
            }
            System.out.println(destCardDeck.size());
            destDeckSize = destCardDeck.size();
            return cards;
        }
        // The total desired number of cards could not be drawn because the deck is empty
        catch(RuntimeException e){
            DestinationCard[] smallerCards = new DestinationCard[index + 1];
            for(int i = 0; i < index + 1; i++){
                smallerCards[i] = cards[i];
            }
            return smallerCards;
        }
    }

    /**
     * Returns an array of DestinationCards to the DestinationCardDeck
     *
     * @param cards the cards to return to the deck
     *
     * @pre cards != null
     *      destCardDeck != null
     * @post adds all DestinationCards in cards to destCardDeck
     *      destCardDeck's size += cards.length
     */
    public void returnDestCards(DestinationCard[] cards){
        for(DestinationCard card : cards){
            destCardDeck.returnCard(card);
        }
        System.out.println(destCardDeck.size());
        destDeckSize = destCardDeck.size();
    }

    /**
     * Adds DestinationCards to a Player's Hand
     *
     * @param playerName name of Player to give cards
     * @param cards array of DestinationCards
     *
     * @pre cards != null
     *      playerName is the name of an existing Player
     * @post Adds all DestinationCards in cards to Player's Hand
     *      Player's Hand's size += cards.length
     */
    public void addDestinationCards(String playerName, DestinationCard[] cards){
        Player p = getPlayer(playerName);
        ArrayList<DestinationCard> newCards = new ArrayList<>(Arrays.asList(cards));
        p.addDestinationCards(newCards);
    }

    /**
     * Sets DestinationCards of a Player's Hand
     *
     * @param playerName name of Player to set cards for
     * @param cards array of DestinationCards
     *
     * @pre cards != null
     *      playerName is the name of an existing Player
     * @post Sets DestinationCards in Player's Hand to cards
     */
    public void setDestinationCards(String playerName, DestinationCard[] cards){
        Player p = getPlayer(playerName);
        ArrayList<DestinationCard> newCards = new ArrayList<>(Arrays.asList(cards));
        p.setDestinationCards(newCards);
    }

    /**
     * Removes a DestinationCard from a Player's Hand
     *
     * @param playerName name of Player to remove a card from
     * @param card DestinationCard to remove
     *
     * @pre playerName is the name of an existing Player
     *      card != null
     * @post card is removed from Player's Hand
     *      Player's Hand's size -= 1
     */
    public void removeDestinationCard(String playerName, DestinationCard card){
        int i = find(playerName);
        playerList.get(i).removeDestinationCard(card);
    }

    /**
     * Gets a Player from playerList with the given name
     *
     * @param playerName name of Player to search for
     * @return a Player object if found, null otherwise
     *
     * @pre playerList != null
     *      playerList contains no null objects
     * @post a Player object with a name of playerName is returned
     */
    public Player getPlayer(String playerName){
        if(playerName == null){
            return null;
        }
        for(Player p : playerList){
            if(p.getName().equals(playerName)){
                return p;
            }
        }
        return null;
    }


    /**
     * Changes the current turn to the next player
     *
     * @pre none
     * @post changes the current turn to the next Player in the playerList
     */
    public void nextTurn(){
        playerState.nextTurn(this);
    }

    /**
     * Searches the userList for a User with a specific userID and returns the index of the User
     * @param userName username to search for
     * @return the index in the userList of the User with the specified userID, -1 if not found
     *
     * @pre userList != null
     * @post Returns the index in the userList of the User with the specified userID, -1 if not found
     */
    private int find(String userName){
        if(userName == null){
            return -1;
        }
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).getUserName().equals(userName)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a random 8-digit hex value
     *
     * @pre none
     * @post returns a random 8-digit hex value
     */
    private String generateID(){
        String longID = UUID.randomUUID().toString();
        return longID.substring(0, ID_END);
    }

    /**
     * Gets the size of the destination card deck
     *
     * @return size of destination card deck
     *
     * @pre none
     * @post returns the number of cards in the destination card deck
     */
    public int getDeckSize(){
        return destDeckSize;
    }

    public FaceUpCards getFaceUpCards() {
        return faceUpCards;
    }

    public TrainCardDeck getTrainCardDeck() {
        return trainCardDeck;
    }

    public boolean isMyTurn(String playerName){
        return playerState.isMyTurn(playerName);
    }

    /**
     * Gets the name of this Game
     *
     * @return the name of this Game
     *
     * @pre none
     * @post returns the name of this Game
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the name of this Game
     *
     * @param gameName new name to set
     *
     * @pre none
     * @post sets the name of the Game to gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Gets the ID of the game
     *
     * @return 8-digit hex ID of the game
     *
     * @pre none
     * @post returns the 8-digit hex ID of this game
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Sets the ID of the game
     *
     * @param gameID ID to set
     *
     * @pre none
     * @post sets the ID of this game to gameID
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /**
     * Gets the list of Users
     *
     * @return the list of Users in this game
     *
     * @pre none
     * @post returns an ArrayList of Users in this game
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * Sets the list of Users
     *
     * @param userList list of Users to set
     *
     * @pre none
     * @post sets the userList of this game to the new userList
     */
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    /**
     * Gets the max number of players allowed in the room
     *
     * @return the max number of players allowed in the room
     *
     * @pre none
     * @post returns the max number of players allowed in the room
     */
    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    /**
     * Sets the max number of players allowed in the room
     *
     * @param maxPlayerNumber max number of players to set
     *
     * @pre none
     * @post sets the max number of players allowed in the room to maxPlayerNumber
     */
    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    /**
     * Gets the number of players currently in the game
     *
     * @return number of players in the game
     *
     * @pre none
     * @post returns the number of players in the game
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Gets the size of the destination card deck
     *
     * @return size of destination card deck
     *
     * @pre none
     * @post returns the number of cards in the destination card deck
     */
    public int getDestDeckSize(){
        return destDeckSize;
    }

    /**
     * Sets the number of players in the game
     *
     * @param numPlayers number of players to set
     *
     * @pre none
     * @post sets the number of players in the game to numPlayers
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Adds a message to the chat
     *
     * @param message message to add
     *
     * @pre chat != null
     * @post adds a message to the chat
     */
    public void addMessage(Message message) { chat.addMessage(message); }

    /**
     * Gets the messages in the chat
     *
     * @return the sorted messages of the chat
     *
     * @pre chat != null
     * @post returns the messages of the chat sorted by timestamp
     */
    public ArrayList<Message> getMessages() { return chat.getSortedMessages(); }

    /**
     * Gets the list of players in the game
     *
     * @return the list of players in the game
     *
     * @pre none
     * @post returns the list of players in the game
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Adds a card to the face-up deck
     *
     * @param trainCard card to add
     * @param position position in the face-up deck to add the card
     *
     * @pre faceUpCards != null
     * @post adds the train card to the face-up deck at the specified position
     */
    public void addCardToFaceUp(TrainCard trainCard, int position)
    {
        faceUpCards.addCard(position, trainCard);
    }

    /**
     * Gets the train card at a specific position of the face-up deck
     *
     * @param position position to pull card from
     * @return the train card at that position if there is one
     *
     * @pre faceUpCards != null
     * @post returns the train card at the specified location in the face-up deck
     */
    public TrainCard getCardFromFaceUp(int position)
    {
        return faceUpCards.getCard(position);
    }

    /**
     * Removes a train card from the face-up deck at the given position
     *
     * @param position position to remove card from
     * @return the train card that was at the given position
     *
     * @pre faceUpCards != null
     *      1 <= position <= 5
     * @post removes the train card from the face-up deck at the given position and returns it
     */
    public TrainCard removeCardFromFaceUp(int position)
    {
        return faceUpCards.removeCard(position);
    }

    /**
     * Draws a train card from the train card deck
     *
     * @return a random train card from the train card deck
     *
     * @pre none
     * @post returns a random train card
     */
    public TrainCard drawTrainCard()
    {
        TrainCard drawnCard = trainCardDeck.drawCard();
        trainCardDeckCurrentSize = trainCardDeck.getNumCardsInDeck();
        return drawnCard;
    }

    /**
     * Gets the size of the train card deck
     *
     * @return the number of cards in the train card deck
     *
     * @pre none
     * @post returns the number of cards in the train card deck
     */
    public int getTrainCardDeckSize() { return trainCardDeckCurrentSize; }

    /*
    public void decrementTrainCardDeckSize() { trainCardDeckCurrentSize--; }

    /**
     * Increases trainCardDeckCurrentSize by the specified amount
     *
     * @param numToIncrease number to add to trainCardDeckCurrentSize
     *
     * @pre none
     * @post adds numToIncrease to trainCardDeckCurrentSize

    public void increaseTrainCardDeckSize(int numToIncrease) { trainCardDeckCurrentSize += numToIncrease; }
    */

    /**
     * Determines whether or not all players have picked destination cards
     *
     * @return true if all players have picked destination cards, false otherwise
     *
     * @pre playerList has no null objects
     * @post returns true if all players have picked destination cards, false otherwise
     */
    public boolean initDestinationCardsPicked() { return initDestCardsPicked; }
    public void setInitDestCardsPicked() { initDestCardsPicked = true; }

    public void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }

    public String getNextPlayerName(int index){
        int nextIndex = index + 1;
        if(nextIndex >= numPlayers){
            nextIndex = 0;
        }
        return playerList.get(nextIndex).getName();
    }

    public String getTurnName(){
        return playerState.getPlayerName();
    }

    public PlayerState getPlayerState(){
        return playerState;
    }

    public void initTurn(){
        setPlayerState(new PlayerState(playerList.get(0).getName(), numPlayers));
    }

    public Player getLastPlayer(){
        return playerList.get(playerList.size() - 1);
    }

    public Map getMap(){return map;}
    public void initMapClient(InputStream file){
        map = new Map(file);
    }
    public void initMapServer(InputStream file){
        map = new Map(file);
    }


    public boolean isLastTurn(){
        for(Player p : playerList){
            if(p.getTrainCars() <= END_TRAIN_CARS){
                return true;
            }
        }
        return false;
    }

    public void continueGame(){
        gameState.continueGame(this);
    }

    public void setGameState(IGameState gameState){
        this.gameState = gameState;
    }

    public void updateGameState(){
        if(gameStateData[0].equals("DuringGameState")){
            gameState = new DuringGameState();
            System.out.println(gameStateData[0]);
        }
        else{
            gameState = new FinishingGameState(Integer.parseInt(gameStateData[1]));
            System.out.println(gameStateData[0]);
        }
    }

    public void decrementTurnsLeft(){
        int turnsLeft = Integer.parseInt(gameStateData[1]);
        turnsLeft--;
        gameStateData[1] = Integer.toString(turnsLeft);
    }

    public void setGameStateData(String[] data){
        gameStateData[0] = data[0];
        gameStateData[1] = data[1];
    }

    /*************************** TEST ********************************/

    /**
     * Gets a random train card
     *
     * @return a random train card
     *
     * @pre none
     * @post returns a random train card
    */
    private TrainCard getRandomTraincard()
    {
        switch (new Random().nextInt(9))
        {
            case 0: return new TrainCard(CardColor.BLUE);
            case 1: return new TrainCard(CardColor.BLACK);
            case 2: return new TrainCard(CardColor.RED);
            case 3: return new TrainCard(CardColor.GREEN);
            case 4: return new TrainCard(CardColor.WHITE);
            case 5: return new TrainCard(CardColor.PINK);
            case 6: return new TrainCard(CardColor.ORANGE);
            case 7: return new TrainCard(CardColor.YELLOW);
            case 8: return new TrainCard(CardColor.WILD);
        }
        return null;
    }


    /**
     * Adds a specified number of points to a given player
     *
     * @param playerName name of player to add points to
     * @param points number of points to add
     *
     * @pre player named playerName exists in the playerList
     * @post adds points to the player named playerName
     */
    public void addPointsToPlayer(String playerName, int points){
        int i = find(playerName);
        playerList.get(i).addPoints(points);

    }

    /**
     * Adds a train card to a player
     *
     * @param playerName name of player to add a train card to
     * @param card train card to give to player
     *
     * @pre player named playerName exists in the playerList
     * @post adds a train card to the player's hand
     *      the size of the player's hand += 1
     */
    public void addTrainCardToPlayer(String playerName, TrainCard card){
        int i = find(playerName);
        playerList.get(i).addCardToHand(card);
    }

    /**
     * Removes a train card from the player
     *
     * @param playerName name of player to remove train card from
     * @param card train card to remove
     *
     * @pre player named playerName exists in the playerList
     * @post removes the given train card from the player's hand if it exists
     */
    public void removeTrainCardFromPlayer(String playerName, TrainCard card){
        int i = find(playerName);
        playerList.get(i).removeCardFromHand(card);
    }

    /**
     * Adds a specified number of train cars to a player
     *
     * @param playerName name of player to add train cars to
     * @param numCars number of cars to add
     *
     * @pre player named playerName exists in the playerList
     * @post adds numCars to the player named playerName's number of train cars
     */
    public void addTrainCarsToPlayer(String playerName, int numCars){
        int i = find(playerName);
        playerList.get(i).addTrainCars(numCars);
    }

}
