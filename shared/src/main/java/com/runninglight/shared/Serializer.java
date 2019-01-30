package com.runninglight.shared;

import com.google.gson.Gson;

public class Serializer
{
    private Gson gson;

    public Serializer()
    {
        gson = new Gson();
    }

    public String serialize(Object object)
    {
        return gson.toJson(object);
    }
}
