/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Quarter;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class RotateCornerEvent implements Event<RotateCornerHandler>
{
    private final Quarter area;
    private final boolean clockwise;

    public RotateCornerEvent(Quarter area, boolean clockwise)
    {
        this.area = area;
        this.clockwise = clockwise;
    }

    @Override
    public void dispatch(RotateCornerHandler handler)
    {
        handler.onRotateCorner(this);
    }

    public Quarter getArea()
    {
        return area;
    }

    public boolean isClockwise()
    {
        return clockwise;
    }
}