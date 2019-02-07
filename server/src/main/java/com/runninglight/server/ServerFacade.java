package com.runninglight.server;

import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.User;

import java.util.ArrayList;


public class ServerFacade implements IServer {
   private ServerModel model = ServerModel.getInstance();

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
            System.out.println("User doesn't exist yet. Register successful.");
            model.addUser(user);
            return true;
        }
        else{
            throw new IllegalArgumentException("Register failed.");
        }
    }

    @Override
    public boolean createGame(GameInfo gameInfo) {
        if (gameInfo.getMaxPlayerNumber() < 2 || gameInfo.getMaxPlayerNumber() > 5) {
            System.out.println("Invalid player number");
            return false;
        }
        else if (model.doesGameExist(gameInfo.getGameName())) {
            System.out.println("Game name already exists");
            return false;
        }
        Game game = new Game(gameInfo.getGameName(), gameInfo.getMaxPlayerNumber());
        model.addGame(game);
        return true;
    }

    @Override
    public boolean joinGame(User user, Game game) {
        if (game.getNumPlayers() == game.getMaxPlayerNumber()) {
            System.out.println("Game full. Choose another game");
            return false;
        }
        game.addPlayer(user);
        return true;
    }

    @Override
    public Game[] getGameList() {
        ArrayList<Game> gamesList = model.getGameList();
        Game[] gamesArray = gamesList.toArray(new Game[gamesList.size()]);
        System.out.println(gamesArray);
        return gamesArray;
    }
}
