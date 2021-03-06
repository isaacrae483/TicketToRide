package com.runninglight.server.communication;

import com.runninglight.shared.Command;
import com.runninglight.shared.Serializer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PollHandler implements HttpHandler
{
    private Serializer serializer;

    public PollHandler()
    {
        serializer = new Serializer();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        InputStream reqBody = httpExchange.getRequestBody();
        String userId = serializer.deserializeId(new InputStreamReader(reqBody));
        Command command = ServerCommunicator.getInstance().getCommandForUser(userId);
        String json = serializer.serialize(command);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream respBody = httpExchange.getResponseBody();
        respBody.write(json.getBytes());
        respBody.close();
    }
}
