package ru.naumen.pentago.game.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;

public class Board implements Serializable
{
    private static final long serialVersionUID = 2946478645439374161L;
    private List<Ball> balls = new ArrayList<Ball>();

    public Board()
    {
        for (int y = 0; y < Constants.BOARD_SIZE; y++)
        {
            for (int x = 0; x < Constants.BOARD_SIZE; x++)
            {
                balls.add(new Ball(x, y, -1));
            }
        }
    }

    public Ball getBall(int x, int y)
    {
        return balls.get(y * Constants.BOARD_SIZE + x);
    }

    public List<Ball> getBalls()
    {
        return balls;
    }

    public void rotate(Quarter quarter, RotateDirection direction)
    {
        int startx = quarter.isLeft() ? 0 : Constants.BOARD_SIZE / 2;
        int starty = quarter.isTop() ? 0 : Constants.BOARD_SIZE / 2;

        List<Integer> bufBalls = new LinkedList<Integer>();
        for (int y = 0; y < Constants.BOARD_SIZE / 2; y++)
        {
            for (int x = 0; x < Constants.BOARD_SIZE / 2; x++)
            {
                int xx = x + startx;
                int yy = y + starty;
                bufBalls.add(balls.get(xx + yy * Constants.BOARD_SIZE).getPlayer());
            }
        }

        for (int y = 0; y < Constants.BOARD_SIZE / 2; y++)
        {
            for (int x = 0; x < Constants.BOARD_SIZE / 2; x++)
            {
                int xx = 0;
                int yy = 0;
                if (RotateDirection.Clockwise.equals(direction))
                {
                    xx = -y - 1 + Constants.BOARD_SIZE / 2;
                    yy = x;
                }
                else if (RotateDirection.CounterClockwise.equals(direction))
                {
                    xx = y;
                    yy = -x - 1 + Constants.BOARD_SIZE / 2;
                }

                balls.get((xx + startx) + (yy + starty) * Constants.BOARD_SIZE).setPlayer(
                        bufBalls.get(x + y * Constants.BOARD_SIZE / 2));
            }
        }
    }

    public void rotate(RotateInfo rotateInfo)
    {
        rotate(rotateInfo.getQuarter(), rotateInfo.getDirection());
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