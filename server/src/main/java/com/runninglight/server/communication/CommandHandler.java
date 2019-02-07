package com.runninglight.server.communication;

import com.runninglight.shared.Command;
import com.runninglight.shared.Results;
import com.runninglight.shared.Serializer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import sun.rmi.runtime.Log;

public class CommandHandler implements HttpHandler
{
    private static final String TAG = "TicketToRide.CommandHandler";

    private Serializer serializer;

    public CommandHandler()
    {
        serializer = new Serializer();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        InputStream reqBody = httpExchange.getRequestBody();
        Command command = new Command(new InputStreamReader(reqBody));

        Results results;
        Object response = command.execute();

        if (response instanceof Exception)
        {
            results = new Results(false, null, ((Exception) response).getMessage());
        }
        else
        {
            results = new Results(true, response, null);
        }

        System.out.println(TAG + ": " + results.getData());

        String json = serializer.serialize(results);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream respBody = httpExchange.getResponseBody();
        respBody.write(json.getBytes());
        respBody.close();


    }
}

