/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.AIMoveInfo;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class MoveCalculatedEvent implements Event<MoveCalculatedHandler>
{
    private final AIMoveInfo moveInfo;
    private final Player player;

    public MoveCalculatedEvent(AIMoveInfo moveInfo, Player player)
    {
        this.moveInfo = moveInfo;
        this.player = player;
    }

    @Override
    public void dispatch(MoveCalculatedHandler handler)
    {
        handler.onMoveCalculated(this);
    }

    public AIMoveInfo getMoveInfo()
    {
        return moveInfo;
    }

    public Player getPlayer()
    {
        return player;
    }
}