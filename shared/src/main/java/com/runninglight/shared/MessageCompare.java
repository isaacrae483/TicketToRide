package com.runninglight.shared;

import java.util.Comparator;

public class MessageCompare implements Comparator<Message>
{
    @Override
    public int compare(Message message1, Message message2)
    {
        return (int) (message1.getTimestamp() - message2.getTimestamp());
    }
}
