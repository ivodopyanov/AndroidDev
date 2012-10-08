/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class RotateBoardEvent implements Event<RotateBoardHandler>
{
    private final RotateInfo rotateInfo;
    private final Player player;

    public RotateBoardEvent(RotateInfo rotateInfo, Player player)
    {
        this.rotateInfo = rotateInfo;
        this.player = player;
    }

    @Override
    public void dispatch(RotateBoardHandler handler)
    {
        handler.onRotateBoard(this);
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