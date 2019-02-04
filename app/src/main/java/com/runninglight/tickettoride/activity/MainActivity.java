package com.runninglight.tickettoride.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.runninglight.tickettoride.R;

public class MainActivity extends AppCompatActivity
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
            loginFragment= new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.currentFragment_View,loginFragment)
                    .commit();
        }


    }
}
