/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import ru.naumen.pentago.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class MoveFoundException extends Exception
{
    private static final long serialVersionUID = 6646065831224284922L;

    private final Ball ball;
    private final int player;
    private final double weight;

    public MoveFoundException(Ball ball, int player, double weight)
    {
        this.ball = ball;
        this.player = player;
        this.weight = weight;
    }

    public Ball getBall()
    {
        return ball;
    }

    public int getPlayer()
    {
        return player;
    }

    public double getWeight()
    {
        return weight;
    }
}