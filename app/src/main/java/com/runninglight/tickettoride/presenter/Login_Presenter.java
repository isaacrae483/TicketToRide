package com.runninglight.tickettoride.presenter;

import android.util.Log;

import com.runninglight.shared.Game;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.User;
import com.runninglight.tickettoride.IPresenter.ILogin_Presenter;
import com.runninglight.tickettoride.activity.LoginFragment;
import com.runninglight.tickettoride.communication.ClientCommunicator;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerInfo;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.ILogin_View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Login_Presenter implements ILogin_Presenter, Observer {

    private ILogin_View loginFragment;

    public  Login_Presenter(ILogin_View fragment) {
        loginFragment = fragment;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void login(LoginInfo loginInfo,ServerInfo serverInfo) {

        ClientCommunicator.getInstance().init(serverInfo.getDomain(),serverInfo.getPort());
        boolean loginSuccessful = ServerProxy.getInstance().login(loginInfo);
        if (loginSuccessful)
        {
            User user = new User(loginInfo.getUserName(), loginInfo.getPassword());
            ClientModel.getInstance().setCurrentUser(user);
            loginSuccess(ClientModel.getInstance().getCurrentUser());
        }
        else
        {
            // Maybe we could show a Toast or something here on a failure?
        }

    }

    @Override
    public void register(LoginInfo loginInfo,ServerInfo serverInfo) {
        ClientCommunicator.getInstance().init(serverInfo.getDomain(),serverInfo.getPort());
        boolean registerSuccessful = ServerProxy.getInstance().register(loginInfo);
        if (registerSuccessful)
        {
            boolean loginSuccessful = ServerProxy.getInstance().login(loginInfo);
            if (loginSuccessful)
            {
                User user = new User(loginInfo.getUserName(), loginInfo.getPassword());
                ClientModel.getInstance().setCurrentUser(user);
                loginSuccess(ClientModel.getInstance().getCurrentUser());
            }
            else
            {
                // Inform the user they're a failure here
            }
        }
    }

    @Override
    public void loginSuccess(User user) {
        ServerProxy.getInstance().getGameList();
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Game> gameList = ((ClientModel) o).getGameList();
        loginFragment.loginSuccessful(gameList);
    }
}
