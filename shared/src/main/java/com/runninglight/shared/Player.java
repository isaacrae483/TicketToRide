package com.runninglight.shared;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.TrainCard;

import java.util.ArrayList;

public class Player {

    private String name;

    private PlayerColor color;

    private int points;

    private int trainCars;

    private ArrayList<DestinationCard> destinationCards;

    private ArrayList<Route> claimedRoutes;

    private Hand hand;

    public Player(String name, int trainCars, PlayerColor color) {
        this.name = name;
        this.points = 0;
        this.color = color;
        this.trainCars = trainCars;
        this.hand = new Hand();
        destinationCards = new ArrayList<>();
        claimedRoutes = new ArrayList<>();
    }

    public void addDestinationCards(ArrayList<DestinationCard> cards){
        destinationCards.addAll(cards);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTrainCars() {
        return trainCars;
    }

    public void setTrainCars(int trainCars) {
        this.trainCars = trainCars;
    }

    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public ArrayList<Route> getClaimedRoutes() {
        return claimedRoutes;
    }

    public void setClaimedRoutes(ArrayList<Route> claimedRoutes) {
        this.claimedRoutes = claimedRoutes;
    }

    public boolean hasDestinationCards() { return destinationCards.size() > 0; }

    public Hand getHand() { return hand; }

    public int getHandSize() {
        return hand.getHandSize();
    }

    public void addCardToHand(TrainCard trainCard) { hand.addTrainCard(trainCard); }
}
