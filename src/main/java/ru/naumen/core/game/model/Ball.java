package ru.naumen.core.game.model;

import java.io.IOException;
import java.io.Serializable;

public class Ball implements Serializable
{
    public static final int NO_PLAYER = -1;
    private static final long serialVersionUID = 2193369284913944667L;
    private int x;
    private int y;
    private int player;

    public Ball(int x, int y, int player)
    {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getPlayer()
    {
        return player;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean inside(SquareArea area)
    {
        return area.getX() <= x && x < area.getX() + area.getLength() && area.getY() <= y
                && y < area.getY() + area.getLength();
    }

    public void setPlayer(int player)
    {
        this.player = player;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "Ball [x=" + x + ", y=" + y + ", player=" + player + "]";
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        x = in.readInt();
        y = in.readInt();
        player = in.readInt();
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        out.write(x);
        out.write(y);
        out.write(player);
    }
}