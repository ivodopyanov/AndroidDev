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
public class MoveBallEvent implements Event<MoveBallHandler>
{
    private final Ball ball;
    private final String playerCode;

    public MoveBallEvent(Ball ball, String playerCode)
    {
        this.ball = ball;
        this.playerCode = playerCode;
    }

    @Override
    public void dispatch(MoveBallHandler handler)
    {
        handler.onMoveBall(this);
    }

    public Ball getBall()
    {
        return ball;
    }

    public String getPlayerCode()
    {
        return playerCode;
    }
}