package com.runninglight.tickettoride.IPresenter;

import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.User;

import java.util.Observable;
import java.util.Observer;

public interface ILogin_Presenter extends Observer {


     void login(LoginInfo loginInfo);
     void register(LoginInfo loginInfo);

     void loginSuccess(User user);
}
