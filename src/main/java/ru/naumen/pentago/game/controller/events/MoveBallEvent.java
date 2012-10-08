/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Player;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class MoveBallEvent implements Event<MoveBallHandler>
{
    private final Ball ball;
    private final Player player;

    public MoveBallEvent(Ball ball, Player player)
    {
        this.ball = ball;
        this.player = player;
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

    public Player getPlayer()
    {
        return player;
    }
}