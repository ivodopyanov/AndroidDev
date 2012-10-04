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
public class RotateCornerEvent implements Event<RotateCornerHandler>
{
    private final SquareArea area;
    private final RotateDirection direction;

    public RotateCornerEvent(SquareArea area, RotateDirection direction)
    {
        this.area = area;
        this.direction = direction;
    }

    @Override
    public void dispatch(RotateCornerHandler handler)
    {
        handler.onRotateCorner(this);
    }

    public SquareArea getArea()
    {
        return area;
    }

    public RotateDirection getDirection()
    {
        return direction;
    }
}