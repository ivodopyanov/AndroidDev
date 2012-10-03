/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.model.SquareArea;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class CornerRotatedEvent
{
    private final SquareArea area;
    private final RotateDirection direction;

    public CornerRotatedEvent(SquareArea area, RotateDirection direction)
    {
        this.area = area;
        this.direction = direction;
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