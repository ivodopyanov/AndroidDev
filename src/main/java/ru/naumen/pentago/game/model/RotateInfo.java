/**
 * 
 */
package ru.naumen.pentago.game.model;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class RotateInfo
{
    private final Quarter quarter;
    private final boolean clockwise;

    public RotateInfo(Quarter quarter, boolean clockwise)
    {
        this.quarter = quarter;
        this.clockwise = clockwise;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof RotateInfo))
        {
            return false;
        }
        RotateInfo rotateInfo = (RotateInfo)o;
        return quarter.equals(rotateInfo.getQuarter()) && (clockwise == rotateInfo.isClockwise());
    }

    public Quarter getQuarter()
    {
        return quarter;
    }

    @Override
    public int hashCode()
    {
        return (quarter.isLeft() ? 0 : 1) + (quarter.isTop() ? 0 : 2) + (clockwise ? 0 : 4);
    }

    public boolean isClockwise()
    {
        return clockwise;
    }

    @Override
    public String toString()
    {
        return quarter.toString() + (clockwise ? ", clockwise" : ", counter-clockwise");
    }
}