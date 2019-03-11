package com.runninglight.tickettoride.presenter.game;

import android.util.Log;

import com.runninglight.shared.Game;
import com.runninglight.shared.Message;
import com.runninglight.tickettoride.IPresenter.game.IChatPresenter;
import com.runninglight.tickettoride.activity.game.ChatAdapter;
import com.runninglight.tickettoride.communication.ClientCommunicator;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IChatView;

import java.util.Observable;
import java.util.Observer;

public class ChatPresenter implements IChatPresenter, Observer
{
    IChatView chatView;

    public ChatPresenter(IChatView chatView)
    {
        this.chatView = chatView;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void sendMessage(String messageContent)
    {
            Message message = new Message(ClientModel.getInstance().getCurrentUser().getUserName(), messageContent);
            // ClientModel.getInstance().addMessage(message);
        ServerProxy.getInstance().sendMessage(message, ClientModel.getInstance().getCurrentGame());
    }

    @Override
    public void initObserver(){
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void removeObserver(){
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Message)
        {
            chatView.updateChat();
        }
        if(arg instanceof Game){
            chatView.updateAdapter();
        }
    }
}
