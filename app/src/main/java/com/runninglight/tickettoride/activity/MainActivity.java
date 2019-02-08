package com.runninglight.tickettoride.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.runninglight.shared.Game;
import com.runninglight.tickettoride.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginContext
{

    private LoginFragment loginFragment;
    private GameListFragment gameListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getSupportFragmentManager();

        loginFragment = (LoginFragment) fm.findFragmentById(R.id.currentFragment_View);
        if(loginFragment==null){
            loginFragment= new LoginFragment(this);
            fm.beginTransaction()
                    .add(R.id.currentFragment_View,loginFragment)
                    .commit();
        }


    }


   public void loginSuccessful(ArrayList<Game> games){

       gameListFragment = new GameListFragment();
       android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
       t.replace(R.id.currentFragment_View, gameListFragment);
       t.commit();
   }

   public void loginFailed(){}
}
