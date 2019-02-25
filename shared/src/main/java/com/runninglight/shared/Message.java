package com.runninglight.shared;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message
{
    long timestamp;
    String message;
    String userName;

    public Message(String userName, String message)
    {
        this.userName = userName;
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public void timestampMessage()
    {
        this.timestamp = new Date().getTime();
    }

    public String getDateString()
    {
        Date date = new Date(timestamp);
        return new SimpleDateFormat().format(date);
    }
}
