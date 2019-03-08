package com.runninglight.server;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.User;

import java.util.ArrayList;
import java.util.Random;


public class ServerFacade implements IServer {
   private ServerModel model = ServerModel.getInstance();
   private ClientProxy proxy = ClientProxy.getInstance();

    @Override
    public boolean login(LoginInfo loginInfo) {
        User user = new User(loginInfo.getUserName(), loginInfo.getPassword());
        if(model.isValidUser(user)){
            System.out.println("User found. Login successful.");
            return true;
        }
        else{
            throw new IllegalArgumentException("Login failed.");
        }
    }

    @Override
    public boolean register(LoginInfo loginInfo) {
        User user = new User(loginInfo.getUserName(), loginInfo.getPassword());
        if(model.findByUserName(user) == null){
            // userName must be alphanumeric with no spaces, neither userName nor password can be blank
            if(!user.getUserName().matches("^.*[^a-zA-Z0-9 ].*$") &&
                    !user.getUserName().contains(" ") &&
                    !user.getUserName().equals("") &&
                    !user.getPassword().equals("")){
                System.out.println("User doesn't exist yet. Register successful.");
                model.addUser(user);
                return true;
            }
            else{
                throw new IllegalArgumentException("Invalid input");
            }
        }
        else{
            throw new IllegalArgumentException("User already exists");
        }
    }

    @Override
    public boolean createGame(GameInfo gameInfo) {
        if ((gameInfo.getMaxPlayerNumber() < 2 || gameInfo.getMaxPlayerNumber() > 5)&& !gameInfo.getGameName().equals("TEST")) {
            System.out.println("Invalid player number");
            throw new IllegalArgumentException("Invalid player number");
        }
        else if(gameInfo.getGameName() == null || gameInfo.getGameName().equals("")){
            System.out.println("Empty game name");
            throw new IllegalArgumentException("Empty game name");
        }
        Game game = new Game(gameInfo.getGameName(), gameInfo.getMaxPlayerNumber());
        model.addGame(game);
        proxy.addGame(game);
        return true;
    }

    @Override
    public boolean joinGame(User user, Game game) {
        if (game.getNumPlayers() >= game.getMaxPlayerNumber()) {
            System.out.println("Game full. Choose another game");
            return false;
        }
        model.addUserToGame(user, game);
        game.addPlayer(user);
        proxy.addPlayer(user, game);
        return true;
    }

    @Override
    public boolean leaveGame(User u, Game g){
        if(model.removeUserFromGame(u, g)){
            proxy.removePlayer(u, g);
            return true;
        }
        return false;
    }

    @Override
    public Game[] getGameList() {
        ArrayList<Game> gamesList = model.getGameList();
        Game[] gamesArray = gamesList.toArray(new Game[gamesList.size()]);
        System.out.println(gamesArray);
        return gamesArray;
    }

    @Override
    public DestinationCard[] drawDestCards(String gameID, int numCards){
        return model.drawDestCards(gameID, numCards);
    }

    @Override
    public void returnDestCards(String gameID, String playerName,
                                DestinationCard[] cardsKept, DestinationCard[] cardsToReturn){
        model.returnDestCards(gameID, playerName, cardsKept, cardsToReturn);
        Game g = model.getGameByID(gameID);
        Player p = g.getPlayer(playerName);
        proxy.setDestinationCards(g, p);
    }

    @Override
    public boolean sendMessage(Message message, Game game)
    {
        System.out.println("Received message: '" + message.getMessage() + "' from user: " + message.getUserName() + " in game: " + game.getGameName());
        ServerModel.getInstance().addMessageToGame(message, game);
        ClientProxy.getInstance().broadcastMessage(message, game);
        return true;
    }

    @Override
    public boolean drawCardFromFaceUpToHand(Game game, User user, TrainCard trainCard, int position)
    {
        if (trainCard != null) ClientProxy.getInstance().addCardToHand(game, user, trainCard);
        ClientProxy.getInstance().addCardToFaceUp(game, getRandomTraincard(), position);
        return false;
    }

    @Override
    public boolean drawCardFromDeckToHand(Game game, User user)
    {
        return false;
    }

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
}
