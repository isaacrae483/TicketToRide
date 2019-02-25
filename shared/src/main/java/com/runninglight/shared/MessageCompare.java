package com.runninglight.shared;

import java.util.Comparator;

public class MessageCompare implements Comparator<Message>
{
    @Override
    public int compare(Message message1, Message message2)
    {
        System.out.println("comparing: " + (int) (message1.getTimestamp() - message2.getTimestamp()));
        System.out.println(message1.getTimestamp());
        System.out.println(message2.getTimestamp());
        return (int) (message1.getTimestamp() - message2.getTimestamp());
    }
}
