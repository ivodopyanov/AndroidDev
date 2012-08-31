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
        for (int i = 0; i < BoardActivity.COL_NUM; i++)
        {
            for (int j = 0; j < BoardActivity.ROW_NUM; j++)
            {
                balls.add(new Ball(i, j, -1));
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