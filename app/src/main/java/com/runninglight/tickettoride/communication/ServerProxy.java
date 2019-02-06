package com.runninglight.tickettoride.communication;

import com.runninglight.shared.Command;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Results;
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
        Results results = communicator.send(new Command(
                SERVER_FACADE,
                "register",
                new String[]{LOGIN_INFO},
                new Object[]{loginInfo}));
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
        Results results = communicator.send(new Command(
                SERVER_FACADE,
                "login",
                new String[]{LOGIN_INFO},
                new Object[]{loginInfo}));
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
        Results results = communicator.send(new Command(
                SERVER_FACADE,
                "createGame",
                new String[]{GAME_INFO},
                new Object[]{gameInfo}));
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
    public boolean joinGame(User user) {
        Results results = communicator.send(new Command(
                SERVER_FACADE,
                "createGame",
                new String[]{USER},
                new Object[]{user}));
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
    public ArrayList<Game> getGameList(){
        Results results = communicator.send(new Command(
                SERVER_FACADE,
                "getGameList",
                new String[]{},
                new Object[]{}));
        if(results.isSuccess()){
            System.out.println("Game list retrieved Successfully");
            model.initGamesList( (ArrayList<Game>) results.getData());
            return (ArrayList<Game>) results.getData();
        }
        else{
            System.out.println(results.getErrorInfo());
            return null;
        }
    }
}