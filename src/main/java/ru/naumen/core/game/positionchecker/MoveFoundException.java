/**
 * 
 */
package ru.naumen.core.game.positionchecker;

import ru.naumen.core.game.model.Ball;

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

    public MoveFoundException(Ball ball, int player)
    {
        this.ball = ball;
        this.player = player;
    }

    public Ball getBall()
    {
        return ball;
    }

    public int getPlayer()
    {
        return player;
    }
}