package com.runninglight.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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
                    "shared\\src\\main\\java\\com\\runninglight\\shared\\cities_test.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Loop should only run 30 times to make 30 cards
            for(int i = 0; i < STARTING_CARD_COUNT; i++){
                City startCity = new City(scanner.next());
                City endCity = new City(scanner.next());
                DestinationCard card = new DestinationCard(startCity, endCity);
                deck.add(card);
            }
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

    public DestinationCard getRandomCard(){
        int index = ThreadLocalRandom.current().nextInt(deck.size());
        DestinationCard result = deck.get(index);
        deck.remove(index);
        return result;
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
