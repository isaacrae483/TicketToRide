package com.runninglight.shared.Cards;

import com.runninglight.shared.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DestinationCardDeck {

    private ArrayList<DestinationCard> deck;

    private static final int STARTING_CARD_COUNT = 30;

    public DestinationCardDeck(){
        initializeDeck();
    }

    private void initializeDeck(){
        try {
            deck = new ArrayList<>();
            String filePath = System.getProperty("user.dir") + File.separator +
                    "shared\\src\\main\\java\\com\\runninglight\\shared\\Cards\\destinationCards.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Loop should only run 30 times to make 30 cards
            for(int i = 0; i < STARTING_CARD_COUNT; i++){
                City startCity = new City(scanner.next());
                City endCity = new City(scanner.next());
                int points = Integer.parseInt(scanner.next());
                DestinationCard card = new DestinationCard(startCity, endCity, points);
                String imageResource = startCity.getName().toLowerCase().replace(" ", "") +
                        "_" + endCity.getName().toLowerCase().replace(" ", "");
                card.setImageResourceString(imageResource);
                deck.add(card);
            }
            System.out.println(deck.toString());
            Collections.shuffle(deck);
        }
        catch(FileNotFoundException e){
            // Throws an exception that the Command will catch to return a false success
            throw new RuntimeException("File doesn't exist");
        }
        catch(Exception e){
            // Throws an exception that the Command will catch to return a false success
            throw new RuntimeException("Failed to build a full deck");
        }

    }

    public DestinationCard drawCard(){
        if(deck.size() > 0) {
            DestinationCard result = deck.get(0);
            deck.remove(0);
            return result;
        }
        else{
            throw new RuntimeException("Cannot draw another card");
        }
    }

    public void returnCard(DestinationCard card){
        if(card == null){
            throw new RuntimeException("Invalid card");
        }
        deck.add(card);
    }

    public int size(){
        return deck.size();
    }

    public ArrayList<DestinationCard> getDeck() {
        return deck;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(DestinationCard card : deck){
            sb.append(card.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
