/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class BallInsertedInCornerEvent
{
    private final Ball ball;

    public BallInsertedInCornerEvent(Ball ball)
    {
        this.ball = ball;
    }

    public Ball getBall()
    {
        return ball;
    }
}