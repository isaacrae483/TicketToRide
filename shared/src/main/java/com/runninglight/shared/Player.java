package com.runninglight.shared;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.TrainCard;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Player model class
 * contains all model elements of a Player in a Ticket to Ride game including destinationCards and
 * a hand. Also includes basic information such as name and color
 *
 * Invariants: points >= 0, trainCars >= 0, numTrainCards >= 0; hand, color, name, destinationCards,
 * trainCards all not null
 */
public class Player {

    /**
     * String containing the name of the player
     */
    private String name;

    /**
     * PLayerObject containing the color for the player
     */
    private PlayerColor color;

    /**
     * Number of points the player has currently
     */
    private int points;

    /**
     * Number of trainCars the player has currently
     */
    private int trainCars;

    /**
     * Number of trainCards the player has currently
     */
    private int numTrainCards;

    /**
     * ArrayList containing all the destination cards the player has currently
     */
    private ArrayList<DestinationCard> destinationCards;

    /**
     * ArrayList containing all the Routes the player has claimed currently
     */
    private ArrayList<Route> claimedRoutes;

    /**
     * Model class containing all the TrainCards that the player holds currently
     */
    private Hand hand;

    private int destCardsGained;

    private int destCardsLost;

    private boolean hasMostRoutes;

    /**
     * constructor of class for a Player in the Ticket To Ride game
     * @param name Player name, corresponds to the username.
     * @param trainCars number of initial train cars
     * @param color Color assigned to this player
     *
     * precondition: None
     * postcondition: points == 0, this.name == name, this.trainCars == trainCars,
     *              this.numTrainCards == 0; color, destinationCards, hand, claimedRoutes all not
     *              null
     */
    public Player(String name, int trainCars, PlayerColor color) {
        this.name = name;
        this.points = 0;
        this.color = color;
        this.trainCars = trainCars;
        this.hand = new Hand();
        this.numTrainCards = 0;
        destCardsGained = 0;
        destCardsLost = 0;
        hasMostRoutes = false;
        destinationCards = new ArrayList<>();
        claimedRoutes = new ArrayList<>();
    }

    /**
     * Adds a list of DestinationCards to the players list of destination cards
     * @param cards ArrayList of all destination cards to be added to the players destination cards
     *
     *  precondition: cards != null, cards.length > 0
     *  postcondition: this.cards.size == (original this.cards.size) + cards.size
     */
    public void addDestinationCards(ArrayList<DestinationCard> cards){
        destinationCards.addAll(cards);
    }

    /**
     * Returns name
     * @return Name of player
     *
     * precondition: None
     * postcondition: Name unchanged
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name Name to set
     *
     * precondition: name != null
     * postcondition: this.name == name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns color
     * @return Color of player
     *
     * precondition: None
     * postcondition: Color unchanged
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Returns points
     * @return Points of player
     *
     * precondition: None
     * postcondition: Points unchanged
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets points
     * @param points points to set
     *
     * precondition: points >= 0
     * postcondition: this.points == points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns number of traincars
     * @return Number of trainCars
     *
     * precondition: None
     * postcondition: trainCars unchanged
     */
    public int getTrainCars() {
        return trainCars;
    }

    /**
     * Sets trainCars
     * @param trainCars number of traincars to set
     *
     * precondition: trainCars >= 0
     * postcondition: this.trainCars == trainCars
     */
    public void setTrainCars(int trainCars) {
        this.trainCars = trainCars;
    }

    /**
     * Returns player's DestinationCards
     * @return ArrayList of destinationCards
     *
     * precondition: None
     * postcondition: this.destinationCards unchanged
     */
    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    /**
     * Sets destinationCards
     * @param destinationCards list of DestinationCards to set
     *
     * precondition: destinationCards != null, destinationCards.size > 0
     * postcondition: this.destinationCards == destinationCards
     */
    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    /**
     * Returns player's claimed routes
     * @return ArrayList of Route containing routes claimed by player
     *
     * precondition: None
     * postcondition: this.claimedRoutes unchanged
     */
    public ArrayList<Route> getClaimedRoutes() {
        return claimedRoutes;
    }

    /**
     * Sets claimedRoutes
     * @param claimedRoutes list of claimed routes to set for this player
     *
     * precondition: destinationCards != null, destinationCards.length > 0
     * postcondition: this.claimedRoutes == claimedRoutes
     */
    public void setClaimedRoutes(ArrayList<Route> claimedRoutes) {
        this.claimedRoutes = claimedRoutes;
    }

    /**
     * Return whether the player has destination cards or not
     * @return true if destinationCards.size > 0, otherwise false
     *
     * precondition: None
     * postcondition: destinationCards unchanged
     */
    public boolean hasDestinationCards() { return destinationCards.size() > 0; }

    /**
     * Returns player's hand
     * @return player's Hand object
     *
     * precondition: None
     * postcondition: this.hand unchanged
     */
    public Hand getHand() { return hand; }

    /**
     * Returns size of player's hand
     * @return int with number of cards in player's hand
     *
     * precondition: None
     * postcondition: this.hand unchanged
     */
    public int getHandSize() {
        return hand.getHandSize();
    }

    /**
     * Adds the TrainCard param to the players hand
     * @param trainCard trainCard to add
     *
     * precondition: trainCard != null
     * postcondition: this.hand.size == (original hand.size + 1), hand contains trainCard,
     *                  this.numTrainCards == (original numTrainCards + 1)
     */
    public void addCardToHand(TrainCard trainCard) { hand.addTrainCard(trainCard); numTrainCards++; }

    /**
     * removes the specified trainCard from the players hand
     * @param trainCard trainCard to remove
     *
     * precondition: trainCard != null
     * postcondition: this.hand.size == (original hand.size - 1),
     *                  this.numTrainCards == (original numTrainCards - 1)
     */
    public void removeCardFromHand(TrainCard trainCard){
        //hand.removeTrainCard(trainCard);
        numTrainCards--;
    }

    /**
     * removes the specified destinationCard from the players hand
     * @param card trainCard to remove
     *
     * precondition: card != null
     * postcondition: this.destinationCards.size == (original destinationCards.size - 1),
     */
    public void removeDestinationCard(DestinationCard card){
        for(DestinationCard c : destinationCards){
            if(c.toString().equals(card.toString())){
                destinationCards.remove(c);
            }
        }
    }

    /**
     * Adds points to the player's points
     * @param points
     *
     * precondition: points > 0
     * postcondition: this.points == (original this.points) + points
     */
    public void addPoints(int points){
        this.points += points;
    }

    /**
     * Adds trainCars to the player's trainCar number
     * @param numCars
     *
     * precondition: points > 0
     * postcondition: this.trainCars == (original this.trainCars) + numCars
     */
    public void addTrainCars(int numCars){
        this.trainCars += numCars;
    }

    public void calculateTotalPoints() {
        int bonus = 0;
        if (hasMostRoutes) {
            bonus = 10;
        }
        points = points + bonus + destCardsGained - destCardsLost;
    }

    public void destCardPoints() {
        destCardsGained = 0;
        destCardsLost = 0;
        for (DestinationCard destCard : destinationCards) {
            if (destCardComplete(destCard)) {
                destCardsGained = destCardsGained + destCard.getPoints();
            }
            else {
                destCardsLost = destCardsLost + destCard.getPoints();
            }
        }
    }

    private boolean destCardComplete(DestinationCard destCard) {
        City startCity = destCard.getCities().get(0);
        City endCity = destCard.getCities().get(1);

        TreeSet<City> visitedCities = new TreeSet<>();
        ArrayList<Route> routesToVisit = new ArrayList<>();

        routesToVisit.addAll(routesWithCity(startCity));
        visitedCities.add(startCity);

        while (!routesToVisit.isEmpty()) {
            ArrayList<City> citiesToCheck = new ArrayList<>();
            for (Route r : routesToVisit) {
                ArrayList<City> otherCities = new ArrayList<>();
                if (!visitedCities.contains(r.getCity1())) {
                    visitedCities.add(r.getCity1());
                    otherCities.addAll(otherCities(r.getCity1()));
                }
                if (!visitedCities.contains(r.getCity2())) {
                    visitedCities.add(r.getCity2());
                    otherCities.addAll(otherCities(r.getCity2()));
                }
                for (City c : otherCities) {
                    if (c.equals(endCity)) {
                        return true;
                    }
                    if (!visitedCities.contains(c)) {
                        visitedCities.add(c);
                        citiesToCheck.add(c);
                    }
                }
            }
            routesToVisit.clear();
            for (City c : citiesToCheck) {
                routesToVisit.addAll(routesWithCity(c));
            }
        }
        return false;
    }

    private ArrayList<Route> routesWithCity(City c) {
        ArrayList<Route> routesToVisit = new ArrayList<>();
        for (Route r : claimedRoutes) {
            if (r.getCity1().equals(c) || r.getCity2().equals(c)) {
                routesToVisit.add(r);
            }
        }
        return routesToVisit;
    }

    private ArrayList<City> otherCities(City rootCity) {
        ArrayList<City> otherCities = new ArrayList<>();
        for (Route r : claimedRoutes) {
            if (r.getCity1().equals(rootCity)) {
                otherCities.add(r.getCity2());
            }
            else if (r.getCity2().equals(rootCity)) {
                otherCities.add(r.getCity1());
            }
        }
        return otherCities;
    }

    public int getDestCardsGained() {
        return destCardsGained;
    }

    public int getDestCardsLost() {
        return destCardsLost;
    }

    public void setHasMostRoutes(boolean hasMostRoutes) {
        this.hasMostRoutes = hasMostRoutes;
    }

    public boolean getHasMostRoutes() {
        return hasMostRoutes;
    }

    public ArrayList<TrainCard> playTrainCards(String color, int length) {
        trainCars -= length;
        return hand.playTrainCards(color, length);
    }
}
