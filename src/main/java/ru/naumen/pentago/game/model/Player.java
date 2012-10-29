package ru.naumen.pentago.game.model;

import java.io.Serializable;

public class Player implements Serializable
{
    public static enum PlayerType
    {
        human, computer;
    }

    private static final long serialVersionUID = -5896810674770613182L;

    private final int titleCode;
    private int ballResource;
    private final String code;
    private final PlayerType type;

    public Player(int titleCode, String code, int ballResource, PlayerType type)
    {
        this.titleCode = titleCode;
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

    public int getTitleCode()
    {
        return titleCode;
    }

    public PlayerType getType()
    {
        return type;
    }

    public void setBallResource(int ballResource)
    {
        this.ballResource = ballResource;
    }

    @Override
    public String toString()
    {
        return new StringBuilder("Player ").append(code).toString();
    }
}