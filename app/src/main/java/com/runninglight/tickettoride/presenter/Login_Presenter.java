package com.runninglight.tickettoride.presenter;

import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.User;
import com.runninglight.tickettoride.IPresenter.ILogin_Presenter;
import com.runninglight.tickettoride.communication.ClientCommunicator;
import com.runninglight.tickettoride.communication.ServerInfo;
import com.runninglight.tickettoride.communication.ServerProxy;

import java.util.Observable;

public class Login_Presenter implements ILogin_Presenter {
    @Override
    public void login(LoginInfo loginInfo, ServerInfo serverInfo) {
        ClientCommunicator.getInstance().init(serverInfo.getDomain(), serverInfo.getPort());
        ServerProxy.getInstance().login(loginInfo);
    }

    @Override
    public void register(LoginInfo loginInfo, ServerInfo serverInfo) {
        ClientCommunicator.getInstance().init(serverInfo.getDomain(), serverInfo.getPort());
        ServerProxy.getInstance().register(loginInfo);
    }

    @Override
    public void loginSuccess(User user) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
