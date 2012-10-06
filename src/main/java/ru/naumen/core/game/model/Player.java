package ru.naumen.core.game.model;

import java.io.Serializable;

public class Player implements Serializable
{
    public static enum PlayerType
    {
        human, computer;
    }

    private static final long serialVersionUID = -5896810674770613182L;

    private String title;
    private int ballResource;
    private final String code;
    private final PlayerType type;

    public Player(String title, String code, int ballResource, PlayerType type)
    {
        this.title = title;
        this.ballResource = ballResource;
        this.code = code;
        this.type = type;
    }

    public int getBallResource()
    {
        return ballResource;
    }

    public String getCode()
    {
        return code;
    }

    public String getTitle()
    {
        return title;
    }

    public PlayerType getType()
    {
        return type;
    }

    public void setBallResource(int ballResource)
    {
        this.ballResource = ballResource;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return new StringBuilder("Player ").append(title).toString();
    }
}