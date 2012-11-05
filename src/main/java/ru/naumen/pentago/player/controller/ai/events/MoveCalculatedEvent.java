/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.events;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Player;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class MoveCalculatedEvent implements Event<MoveCalculatedHandler>
{
    private final Ball ball;
    private final Player player;

    public MoveCalculatedEvent(Ball ball, Player player)
    {
        this.ball = ball;
        this.player = player;
    }

    @Override
    public void dispatch(MoveCalculatedHandler handler)
    {
        handler.onMoveCalculated(this);
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