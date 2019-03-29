package com.runninglight.tickettoride.activity.game;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.runninglight.shared.Game;
import com.runninglight.shared.Message;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;

import java.util.ArrayList;
import java.util.List;

import static com.runninglight.tickettoride.R.layout.message_row;
import static com.runninglight.tickettoride.R.layout.recyclerview_row_gamelist;

public class ChatAdapter extends ArrayAdapter<Message>
{

    private final Activity context;

    private ArrayList<Message> messageList;

    public ChatAdapter (Activity context, ArrayList<Message> messages){
        super(context, message_row, messages);
        this.context = context;
        this.messageList = messages;
    }


    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(message_row, null, true);

        TextView messageContent = rowView.findViewById(R.id.message_view);
        TextView nameView = rowView.findViewById(R.id.name_view);
        messageContent.setText(messageList.get(position).getMessage());
        nameView.setText(messageList.get(position).getUserName());
        return rowView;
    }

    public void refresh(Message message)
    {
        messageList.add(message);
        notifyDataSetChanged();
    }

}

