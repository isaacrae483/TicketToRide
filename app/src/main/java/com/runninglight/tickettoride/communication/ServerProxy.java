package com.runninglight.tickettoride.communication;

import android.util.Log;

import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Results;
import com.runninglight.shared.Serializer;
import com.runninglight.shared.User;

import java.util.ArrayList;

public class ServerProxy implements IServer {
    private static ServerProxy instance = null;
    private ClientCommunicator communicator = ClientCommunicator.getInstance();
    private ClientModel model = ClientModel.getInstance();

    private static final String SERVER_FACADE = "com.runninglight.server.ServerFacade";
    private static final String LOGIN_INFO = "com.runninglight.shared.LoginInfo";
    private static final String GAME_INFO = "com.runninglight.shared.GameInfo";
    private static final String USER = "com.runninglight.shared.USER";

    public static ServerProxy getInstance(){
        if(instance == null){
            instance = new ServerProxy();
        }
        return instance;
    }

    @Override
    public boolean register(LoginInfo loginInfo) {
        Results results = communicator.send(getRegisterCommand(loginInfo));
        if(results.isSuccess()){
            System.out.println("Register succeeded");
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean login(LoginInfo loginInfo) {
        Results results = communicator.send(getLoginCommand(loginInfo));
        if(results.isSuccess()){
            System.out.println("Login succeeded");
            model.setCurrentUser(new User(loginInfo.getUserName(), loginInfo.getPassword()));
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean createGame(GameInfo gameInfo) {
        System.out.println("creating game: " + gameInfo.getGameName());
        Results results = communicator.send(getCreateGameCommand(gameInfo));
        if(results.isSuccess()){
            System.out.println("Game Created Successfully");
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean joinGame(User user, Game game) {
        Results results = communicator.send(getJoinGameCommand(user, game));
        if(results.isSuccess()){
            System.out.println("Game Joined Successfully");
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public Game[] getGameList() {
        Results results = communicator.send(getGameListCommand());
        if (results.isSuccess()) {
            System.out.println("Game list retrieved Successfully");

            // Convert Game[] to ArrayList<Game> in a really messy and probably unnecessary way
            Game[] games = new Game[] {};
            games = (Game[]) new Serializer().deserializeObject(results.getData().toString(), games.getClass().getName());
            ArrayList<Game> gameList = new ArrayList<>();
            for (Object game : games) gameList.add((Game) game);
            model.initGamesList(gameList);

            return games;
        }
        else {
            System.out.println(results.getErrorInfo());
            return null;
        }
    }

    private Command getRegisterCommand(LoginInfo loginInfo)
    {
         return new Command(
                 SERVER_FACADE,
                 "register",
                 new String[] {LOGIN_INFO},
                new Object[] {loginInfo}) ;
    }

    private Command getLoginCommand(LoginInfo loginInfo)
    {
        return new Command(
                SERVER_FACADE,
                "login",
                new String[] {LOGIN_INFO},
                new Object[] {loginInfo} );
    }

    private Command getCreateGameCommand(GameInfo gameInfo)
    {
        return new Command(
                SERVER_FACADE,
                "createGame",
                new String[] {GAME_INFO},
                new Object[] {gameInfo} );
    }

    private Command getJoinGameCommand(User user, Game game)
    {
        return new Command(
                SERVER_FACADE,
                "createGame",
                new String[] {USER},
                new Object[] {user} );
    }

    private Command getGameListCommand()
    {
        return new Command(
                SERVER_FACADE,
                "getGameList",
                new String[] {},
                new Object[] {} );
    }
}