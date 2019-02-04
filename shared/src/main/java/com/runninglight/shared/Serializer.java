package com.runninglight.shared;


import com.google.gson.Gson;

import java.io.InputStreamReader;

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

    public Results deserializeResults(String json)
    {
        return gson.fromJson(json, Results.class);
    }

    public String deserializeId(InputStreamReader inputStreamReader)
    {
        return gson.fromJson(inputStreamReader, String.class);
    }
}
