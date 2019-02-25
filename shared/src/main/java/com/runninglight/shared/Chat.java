package com.runninglight.shared;

import java.util.ArrayList;
import java.util.Collections;

public class Chat
{
    ArrayList<Message> messages;

    public Chat()
    {
        messages = new ArrayList<>();
    }

    public void addMessage(Message message)
    {
        message.timestampMessage();
        messages.add(message);
    }

    public ArrayList<Message> getSortedMessages()
    {
        sortMessages();
        return messages;
    }

    private void sortMessages()
    {
        Collections.sort(messages, new MessageCompare());
    }
}
