/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class RotateBoardEvent implements Event<RotateBoardHandler>
{
    private final RotateInfo rotateInfo;
    private final String playerCode;

    public RotateBoardEvent(RotateInfo rotateInfo, String playerCode)
    {
        this.rotateInfo = rotateInfo;
        this.playerCode = playerCode;
    }

    @Override
    public void dispatch(RotateBoardHandler handler)
    {
        handler.onRotateBoard(this);
    }

    public String getPlayerCode()
    {
        return playerCode;
    }

    public RotateInfo getRotateInfo()
    {
        return rotateInfo;
    }
}