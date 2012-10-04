/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class InsertBallInCornerEvent implements Event<InsertBallInCornerHandler>
{
    private final Ball ball;

    public InsertBallInCornerEvent(Ball ball)
    {
        this.ball = ball;
    }

    @Override
    public void dispatch(InsertBallInCornerHandler handler)
    {
        handler.onInsertBallInCorner(this);
    }

    public Ball getBall()
    {
        return ball;
    }
}