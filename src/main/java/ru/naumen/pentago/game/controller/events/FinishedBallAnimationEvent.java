/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Ball;

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