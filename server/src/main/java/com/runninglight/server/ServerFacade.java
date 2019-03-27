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
import com.runninglight.shared.state.PlayerState;

import java.util.ArrayList;
import java.util.Arrays;
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

        if (!g.initDestinationCardsPicked() && playerName.equals(g.getLastPlayer().getName()))
        {
            dealNewFaceUpCards(g);

            g.setInitDestCardsPicked();

            for (User user : g.getUserList())
            {
                for (int i = 0; i < 4; i++) ClientProxy.getInstance().addCardToHand(g, user, g.drawTrainCard());
            }
        }
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
        Game g = model.getGameByID(game.getGameID());
        if (trainCard != null) ClientProxy.getInstance().addCardToHand(g, user, trainCard);
        TrainCard newCard = g.drawTrainCard();
        ClientProxy.getInstance().addCardToFaceUp(g, newCard, position);
        if (g.getFaceUpCards().tooManyWildCards()) {
            g.getTrainCardDeck().discard(new ArrayList<TrainCard>(Arrays.asList(g.getFaceUpCards().getFaceUpCards())));
            dealNewFaceUpCards(g);
        }
        return true;
    }

    @Override
    public boolean drawCardFromDeckToHand(Game game, User user)
    {
        Game g = model.getGameByID(game.getGameID());
        ClientProxy.getInstance().addCardToHand(g, user, g.drawTrainCard());
        return true;
    }

    @Override
    public void setTurn(String gameID, PlayerState playerState){
        model.setTurn(gameID, playerState);
        proxy.setTurn(model.getGameByID(gameID), playerState);
    }

    private void dealNewFaceUpCards(Game g) {
        ClientProxy.getInstance().addCardToFaceUp(g, g.drawTrainCard(), 1);
        ClientProxy.getInstance().addCardToFaceUp(g, g.drawTrainCard(), 2);
        ClientProxy.getInstance().addCardToFaceUp(g, g.drawTrainCard(), 3);
        ClientProxy.getInstance().addCardToFaceUp(g, g.drawTrainCard(), 4);
        ClientProxy.getInstance().addCardToFaceUp(g, g.drawTrainCard(), 5);
        if (g.getFaceUpCards().tooManyWildCards()) {
            g.getTrainCardDeck().discard(new ArrayList<TrainCard>(Arrays.asList(g.getFaceUpCards().getFaceUpCards())));
            dealNewFaceUpCards(g);
        }
    }
}
