package com.runninglight.tickettoride.communication;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.runninglight.shared.Command;
import com.runninglight.shared.Results;
import com.runninglight.shared.Serializer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Poller
{
    private final static int HANDLER_DELAY = 1000;

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
                    if (command != null)
                    {
                        command.execute();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                commandHandler.postDelayed(this, HANDLER_DELAY);
            }
        };
    }

    public void startPoller(String userId)
    {
        this.userId = userId;
        commandHandler.postDelayed(commandRunnable, HANDLER_DELAY);
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
                URL pollUrl = new URL(serverUrl.toString() + ClientCommunicator.PATH_POLL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) pollUrl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(serializer.serialize(userId));
                outputStreamWriter.close();

                InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());
                Command command = new Command(streamReader);
                return command;
            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }
            return null;
        }
    }
}
