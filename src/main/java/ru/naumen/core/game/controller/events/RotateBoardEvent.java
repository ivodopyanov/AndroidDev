/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.model.SquareArea;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class RotateBoardEvent implements Event<RotateBoardHandler>
{
    private final SquareArea area;
    private final RotateDirection direction;
    private final String playerCode;

    public RotateBoardEvent(SquareArea area, RotateDirection direction, String playerCode)
    {
        this.area = area;
        this.direction = direction;
        this.playerCode = playerCode;
    }

    @Override
    public void dispatch(RotateBoardHandler handler)
    {
        handler.onRotateBoard(this);
    }

    public SquareArea getArea()
    {
        return area;
    }

    public RotateDirection getDirection()
    {
        return direction;
    }

    public String getPlayerCode()
    {
        return playerCode;
    }
}