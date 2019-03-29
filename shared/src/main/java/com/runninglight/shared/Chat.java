package com.runninglight.shared;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Chat
{
    //Message[] messages;
    int index;

    public Chat()
    {
        //messages = new Message[10000];
        index = 0;
    }

    public void addMessage(Message message)
    {
        message.timestampMessage();
        //messages[index++] = message;
    }

    public Message[] getSortedMessages()
    {
        //sortMessages();
        //return messages;
        return null;
    }

    private void sortMessages()
    {
        //Arrays.sort(messages, new MessageCompare());
    }
}
