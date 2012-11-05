/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.events;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public class RotateCalculatedEvent implements Event<RotateCalculatedHandler>
{
    private final RotateInfo rotateInfo;
    private final Player player;

    public RotateCalculatedEvent(RotateInfo rotateInfo, Player player)
    {
        this.rotateInfo = rotateInfo;
        this.player = player;
    }

    @Override
    public void dispatch(RotateCalculatedHandler handler)
    {
        handler.onRotateCalculated(this);
    }

    public Player getPlayer()
    {
        return player;
    }

    public RotateInfo getRotateInfo()
    {
        return rotateInfo;
    }
}