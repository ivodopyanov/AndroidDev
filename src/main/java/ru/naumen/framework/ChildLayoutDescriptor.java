/**
 * 
 */
package ru.naumen.framework;

/**
 * @author ivodopyanov
 * @since 24.10.2012
 * 
 */
public class ChildLayoutDescriptor
{
    private final float widthPercent;
    private final float heightPercent;
    private final float leftPercent;
    private final float topPercent;

    public ChildLayoutDescriptor(float widthPercent, float heightPercent, float leftPercent, float topPercent)
    {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        this.leftPercent = leftPercent;
        this.topPercent = topPercent;
    }

    public float getHeightPercent()
    {
        return heightPercent;
    }

    public float getLeftPercent()
    {
        return leftPercent;
    }

    public float getTopPercent()
    {
        return topPercent;
    }

    public float getWidthPercent()
    {
        return widthPercent;
    }
}