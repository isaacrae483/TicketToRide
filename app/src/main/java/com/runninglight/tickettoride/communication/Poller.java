package com.runninglight.tickettoride.communication;

import android.os.AsyncTask;
import android.os.Handler;

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

public class Poller
{
    private Handler commandHandler;
    private Runnable commandRunnable;
    private URL serverUrl;
    private String userId;

    public Poller(final URL url)
    {
        this.serverUrl = url;

        commandHandler = new Handler();
        commandRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Command command = new GetCommandRequest().execute(url).get();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
    }

    public void startPoller(String userId, int delay)
    {
        this.userId = userId;
        commandHandler.postDelayed(commandRunnable, delay);
    }

    public void stopPoller()
    {
        commandHandler.removeCallbacks(commandRunnable);
    }

    private class GetCommandRequest extends AsyncTask<URL, String, Command>
    {

        @Override
        protected Command doInBackground(URL... args)
        {
            Serializer serializer = new Serializer();
            try
            {
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                httpURLConnection.setRequestMethod(ClientCommunicator.METHOD_POST);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(serializer.serialize(userId));
                outputStreamWriter.close();

                InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());
                Command command = new Command(streamReader);
                System.out.println("command recieved for " + userId  + ": " + command.getClassName());
                return command;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
