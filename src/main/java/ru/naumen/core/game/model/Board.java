package ru.naumen.core.game.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import ru.naumen.core.game.BoardActivity;

import com.google.common.collect.Lists;

public class Board implements Serializable
{
    private static final long serialVersionUID = 2946478645439374161L;
    private List<Ball> balls = Lists.newArrayList();

    public Board()
    {
        for (int y = 0; y < BoardActivity.BOARD_SIZE; y++)
        {
            for (int x = 0; x < BoardActivity.BOARD_SIZE; x++)
            {
                balls.add(new Ball(x, y, -1));
            }
        }
    }

    public void fromArray(SquareArea area, int[][] data)
    {
        for (Ball ball : balls)
        {
            if (ball.inside(area))
            {
                ball.setPlayer(data[ball.getX() - area.getX()][ball.getY() - area.getY()]);
            }
        }
    }

    public List<Ball> getBalls()
    {
        return balls;
    }

    public void setBalls(List<Ball> balls)
    {
        this.balls = balls;
    }

    public int[][] toArray()
    {
        int[][] result = new int[BoardActivity.BOARD_SIZE][];
        for (int i = 0; i < BoardActivity.BOARD_SIZE; i++)
            result[i] = new int[BoardActivity.BOARD_SIZE];
        for (Ball ball : balls)
        {
            result[ball.getX()][ball.getY()] = ball.getPlayer();
        }
        return result;
    }

    public int[][] toArray(SquareArea area)
    {
        int[][] result = new int[area.getLength()][];
        for (int i = 0; i < area.getLength(); i++)
            result[i] = new int[area.getLength()];
        for (Ball ball : balls)
        {
            if (ball.inside(area))
                result[ball.getX() - area.getX()][ball.getY() - area.getY()] = ball.getPlayer();
        }
        return result;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        balls.clear();
        int size = in.readInt();
        for (int i = 0; i < size; i++)
        {
            balls.add((Ball)in.readObject());
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        out.write(balls.size());
        for (Ball ball : balls)
        {
            out.writeObject(ball);
        }
    }
}