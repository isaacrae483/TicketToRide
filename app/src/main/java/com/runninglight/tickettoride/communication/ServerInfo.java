package com.runninglight.tickettoride.communication;

public class ServerInfo
{
    private String domain;
    private int port;

    public ServerInfo(String domain, int port)
    {
        this.domain = domain;
        this.port = port;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }
}
