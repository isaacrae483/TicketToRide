package com.runninglight.tickettoride.communication;

import android.util.Log;

import com.runninglight.shared.Command;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Results;
import com.runninglight.shared.User;

public class ServerProxy implements IServer {
    private static ServerProxy instance = null;
    private ClientCommunicator communicator = ClientCommunicator.getInstance();
    private ClientModel model = ClientModel.getInstance();

    private static final String SERVER_FACADE = "com.runninglight.server.ServerFacade";
    private static final String LOGIN_INFO = "com.runninglight.shared.LoginInfo";

    public static ServerProxy getInstance(){
        if(instance == null){
            instance = new ServerProxy();
        }
        return instance;
    }

    @Override
    public boolean register(LoginInfo loginInfo) {
        Log.d("TicketToRide","Registering user: " + loginInfo.getUserName());
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
        return false;
    }

    @Override
    public boolean joinGame(User user) {
        return false;
    }
}
