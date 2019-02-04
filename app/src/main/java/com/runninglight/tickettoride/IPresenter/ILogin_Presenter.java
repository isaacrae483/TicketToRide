package com.runninglight.tickettoride.IPresenter;

import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.User;
import com.runninglight.tickettoride.communication.ServerInfo;

import java.util.Observable;
import java.util.Observer;

public interface ILogin_Presenter extends Observer {


     void login(LoginInfo loginInfo, ServerInfo serverInfo);
     void register(LoginInfo loginInfo, ServerInfo serverInfo);

     void loginSuccess(User user);
}
