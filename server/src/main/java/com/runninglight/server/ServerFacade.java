package com.runninglight.server;

import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.User;


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
        return false;
    }

    @Override
    public boolean joinGame(User user) {
        return false;
    }
}
