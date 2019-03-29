package com.runninglight.tickettoride.activity.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.runninglight.shared.Message;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.iview.game.IChatView;
import com.runninglight.tickettoride.presenter.game.ChatPresenter;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements IChatView
{
    private View view;
    private ChatAdapter chatAdapter;
    private ListView listView;
    private ChatPresenter chatPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatPresenter = new ChatPresenter(this);

        initUiElements();

        listView = view.findViewById(R.id.chat_view);
        chatAdapter = new ChatAdapter(getActivity(), new ArrayList<Message>());
        listView.setAdapter(chatAdapter);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        chatPresenter.initObserver();
    }

    @Override
    public void onPause(){
        super.onPause();
        chatPresenter.removeObserver();
    }

    @Override
    public void updateChat(Message message)
    {
        chatAdapter.refresh(message);
    }

    @Override
    public void updateAdapter(){
        chatAdapter = new ChatAdapter(getActivity(), new ArrayList<Message>());
        listView.setAdapter(chatAdapter);
    }

    private void initUiElements()
    {
        Button sendButton = view.findViewById(R.id.button_send);
        final EditText editText = view.findViewById(R.id.message_edit);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("TTR.ChatFragment", "sending message: " + editText.getText().toString());
                chatPresenter.sendMessage(editText.getText().toString());
                editText.setText("");
            }
        });
    }
}
