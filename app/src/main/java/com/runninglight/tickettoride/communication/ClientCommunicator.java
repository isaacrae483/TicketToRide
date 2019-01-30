package com.runninglight.tickettoride.communication;

import android.net.UrlQuerySanitizer;

import com.runninglight.shared.Serializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator
{
    private static ClientCommunicator instance;

    private Serializer serializer;
    private String domain;
    private int port;

    public static ClientCommunicator getInstance()
    {
        if (instance == null) instance = new ClientCommunicator();
        return instance;
    }

    // Call this function before using any other method
    public void init(String domain, int port)
    {
        this.domain = domain;
        this.port = port;
        serializer = new Serializer();
    }

    // Will take a Command object and return a Results object when they're added
    public void send(Object command)
    {
        try
        {
            String urlString = "http://" + domain + ":" + port + "/command";
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(serializer.serialize(command));
            outputStreamWriter.close();

            String inputLine;
            InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null) stringBuilder.append(inputLine);
            reader.close();
            streamReader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return; // Once the Results object is added, it will return here
    }
}
