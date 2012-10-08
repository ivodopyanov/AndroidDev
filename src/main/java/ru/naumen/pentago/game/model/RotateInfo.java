/**
 * 
 */
package ru.naumen.pentago.game.model;

import ru.naumen.pentago.game.controller.GameController.RotateDirection;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class RotateInfo
{
    private final Quarter quarter;
    private final RotateDirection direction;

    public RotateInfo(Quarter quarter, RotateDirection direction)
    {
        this.quarter = quarter;
        this.direction = direction;
    }

    public RotateDirection getDirection()
    {
        return direction;
    }

    public Quarter getQuarter()
    {
        return quarter;
    }
}