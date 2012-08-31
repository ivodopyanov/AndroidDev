package ru.naumen.core.game.model;

import java.io.Serializable;

public class Player implements Serializable
{
    private static final long serialVersionUID = -5896810674770613182L;

    private String title;
    private int ballResource;

    public Player(String title, int ballResource)
    {
        this.title = title;
        this.ballResource = ballResource;
    }

    public int getBallResource()
    {
        return ballResource;
    }

    public String getTitle()
    {
        return title;
    }

    public void setBallResource(int ballResource)
    {
        this.ballResource = ballResource;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}