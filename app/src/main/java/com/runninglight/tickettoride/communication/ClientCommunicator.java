package com.runninglight.tickettoride.communication;

import android.os.AsyncTask;

import com.runninglight.shared.Command;
import com.runninglight.shared.Results;
import com.runninglight.shared.Serializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ClientCommunicator
{
    public static final String PATH_COMMAND = "/command";
    public static final String PATH_POLL = "/poll";
    private static final String METHOD_POST = "POST";

    private static ClientCommunicator instance;

    private Poller poller;
    private Serializer serializer;
    private String domain;
    private int port;

    // Strictly for testing purposes
    public static void main(String[] args)
    {
        ClientCommunicator.getInstance().init("127.0.0.1", 8000);
        ClientCommunicator.getInstance().startPoller("test");
    }

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
        try
        {
            URL url = new URL("http://" + domain + ":" + port);
            poller = new Poller(url);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public void startPoller(String userName)
    {
        poller.startPoller(userName);
    }

    public void stopPoller()
    {
        poller.stopPoller();
    }

    public Results send(Command command)
    {
        try
        {
            return new SendCommandRequest().execute(command).get();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private class SendCommandRequest extends AsyncTask<Command, String, Results>
    {

        @Override
        protected Results doInBackground(Command... args)
        {
            Command command = args[0];
            Results results = null;
            try
            {
                String urlString = "http://" + domain + ":" + port + PATH_COMMAND;
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod(METHOD_POST);
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

                results = serializer.deserializeResults(stringBuilder.toString());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return results;
        }
    }
}
