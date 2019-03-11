package com.runninglight.shared;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.DestinationCardDeck;
import com.runninglight.shared.Cards.FaceUpCards;
import com.runninglight.shared.Cards.TrainCard;

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

    private Chat chat;

    /** Destination Card Deck */
    private transient DestinationCardDeck destCardDeck;

    /** Number of cards in Destination Card Deck */
    private int destDeckSize;

    /** Maximum number of Users allowed in the game room */
    private int maxPlayerNumber;

    /** Current number of players in the game room */
    private int numPlayers;

    /** Name of player whose turn it is */
    private String currentTurn;

    /** Index in playerList of whose turn it is */
    private int turnIndex;

    /** Indicates the last index of the hex ID */
    private static final int ID_END = 7;

    /** Starting number of train cars */
    private static final int MAX_TRAIN_CARS = 45;

    private FaceUpCards faceUpCards;

    private int trainCardDeckCurrentSize;

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
        this.faceUpCards = new FaceUpCards();
        this.trainCardDeckCurrentSize = 110;
    }


    public boolean isValidPlayer(Player player){
        String playerName = player.getName();
        int resultIndex = find(playerName);
        if(resultIndex == -1){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Adds a User to the userList
     * @param user User to add
     *
     * @pre none
     * @post Adds a User to the userList
     */
    public void addPlayer(User user){
        if(user != null) {
            userList.add(user);
            playerList.add(new Player(user.getUserName(), MAX_TRAIN_CARS, PlayerColor.values()[numPlayers]));
            ++numPlayers;
        }
    }

    /**
     * Removes a User from the userList
     * @param user User to remove
     *
     * @pre none
     * @post Removes a User from the userList if it exists
     */
    public void removePlayer(User user){
        int userIndex = find(user.getUserName());
        if(userIndex != -1){
            userList.remove(userIndex);
            playerList.remove(userIndex);
            numPlayers--;
        }
    }

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

    public void returnDestCards(DestinationCard[] cards){
        for(DestinationCard card : cards){
            destCardDeck.returnCard(card);
        }
        System.out.println(destCardDeck.size());
        destDeckSize = destCardDeck.size();
    }

    public void addDestinationCards(String playerName, DestinationCard[] cards){
        Player p = getPlayer(playerName);
        ArrayList<DestinationCard> newCards = new ArrayList<>(Arrays.asList(cards));
        p.addDestinationCards(newCards);
    }

    public void setDestinationCards(String playerName, DestinationCard[] cards){
        Player p = getPlayer(playerName);
        ArrayList<DestinationCard> newCards = new ArrayList<>(Arrays.asList(cards));
        p.setDestinationCards(newCards);
    }

    public void removeDestinationCard(String playerName, DestinationCard card){
        int i = find(playerName);
        playerList.get(i).removeDestinationCard(card);
    }

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

    public int getDeckSize(){
        return destDeckSize;
    }

    public String getCurrentTurn(){
        return currentTurn;
    }

    public void setCurrentTurn(String playerName){
        currentTurn = playerName;
        turnIndex = find(playerName);
    }

    public void nextTurn(){
        turnIndex++;
        if(turnIndex == playerList.size()){
            turnIndex = 0;
        }
        currentTurn = playerList.get(turnIndex).getName();
    }

    /**
     * Searches the userList for a User with a specific userID and returns the index of the User
     * @param userName username to search for
     * @return the index in the userList of the User with the specified userID, -1 if not found
     *
     * @pre none
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

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getDestDeckSize(){
        return destDeckSize;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void addMessage(Message message) { chat.addMessage(message); }

    public ArrayList<Message> getMessages() { return chat.getSortedMessages(); }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void addCardToFaceUp(TrainCard trainCard, int position)
    {
        faceUpCards.addCard(position, trainCard);
    }

    public TrainCard getCardFromFaceUp(int position)
    {
        return faceUpCards.getCard(position);
    }

    public TrainCard removeCardFromFaceUp(int position)
    {
        return faceUpCards.removeCard(position);
    }
    public TrainCard drawTrainCard()
    {
        trainCardDeckCurrentSize--;
        return getRandomTraincard();
    }
    public int getTrainCardDeckSize() { return trainCardDeckCurrentSize; }
    public void decrementTrainCardDeckSize() { trainCardDeckCurrentSize--; }
    public void increaseTrainCardDeckSize(int numToIncrease) { trainCardDeckCurrentSize += numToIncrease; }

    public boolean initDestinationCardsPicked()
    {
        for (Player player : playerList) if (!player.hasDestinationCards()) return false;
        return true;
    }

    // TEST

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

    public void addPointsToPlayer(String playerName, int points){
        int i = find(playerName);
        playerList.get(i).addPoints(points);

    }

    public void addTrainCardToPlayer(String playerName, TrainCard card){
        int i = find(playerName);
        playerList.get(i).addCardToHand(card);
    }

    public void removeTrainCardFromPlayer(String playerName, TrainCard card){
        int i = find(playerName);
        playerList.get(i).removeCardFromHand(card);
    }

    public void addTrainCarsToPlayer(String playerName, int numCars){
        int i = find(playerName);
        playerList.get(i).addTrainCars(numCars);
    }
}
