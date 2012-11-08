package ru.naumen.pentago.game.model;

import java.io.Serializable;

public class Player implements Serializable
{
    public static enum PlayerType
    {
        human, humanBluetoothLocal, humanBluetoothRemote, computer;
    }

    private static final long serialVersionUID = -5896810674770613182L;

    private final String title;
    private final int ballResource;
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

    @Override
    public String toString()
    {
        return new StringBuilder("Player ").append(code).toString();
    }
}