package ru.naumen.core.game.model;

public class SquareArea
{
    // Координаты верхнего левого угла
    private final int x;
    private final int y;
    private final int l;

    public SquareArea(int x, int y, int l)
    {
        this.x = x;
        this.y = y;
        this.l = l;
    }

    public int getLength()
    {
        return l;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return "Area [x=" + x + ", y=" + y + ", length=" + l + "]";
    }
}