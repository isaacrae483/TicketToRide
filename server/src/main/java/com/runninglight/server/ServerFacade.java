package com.runninglight.server;

import com.runninglight.server.communication.ServerCommunicator;
import com.runninglight.shared.Command;
import com.runninglight.shared.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.User;

import java.util.ArrayList;


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
        if (gameInfo.getMaxPlayerNumber() < 2 || gameInfo.getMaxPlayerNumber() > 5) {
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
    public void returnDestCards(String gameID, DestinationCard[] cards){
        model.returnDestCards(gameID, cards);
    }

    @Override
    public boolean sendMessage(Message message, Game game)
    {
        System.out.println("Received message: '" + message.getMessage() + "' from user: " + message.getUserName() + " in game: " + game.getGameName());
        ServerModel.getInstance().addMessageToGame(message, game);
        ClientProxy.getInstance().broadcastMessage(message, game);
        return true;
    }

}
