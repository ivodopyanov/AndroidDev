/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 08.10.2012
 * 
 */
public class FinishedBallAnimationEvent implements Event<FinishedBallAnimationHandler>
{
    private final Ball ball;

    public FinishedBallAnimationEvent(Ball ball)
    {
        this.ball = ball;
    }

    @Override
    public void dispatch(FinishedBallAnimationHandler handler)
    {
        handler.onFinishedBallAnimation(this);
    }

    public Ball getBall()
    {
        return ball;
    }
}