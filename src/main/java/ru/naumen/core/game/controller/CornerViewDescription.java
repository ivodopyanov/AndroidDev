/**
 * 
 */
package ru.naumen.core.game.controller;

import java.util.Set;

import ru.naumen.core.game.model.Quarter;

/**
 * @author ivodopyanov
 * @since 03.09.2012
 * 
 */
public class CornerViewDescription
{
    private final Quarter area;
    private final Set<RotateImageDescription> images;
    private final float angle;

    public CornerViewDescription(Quarter area, Set<RotateImageDescription> images, float angle)
    {
        this.area = area;
        this.images = images;
        this.angle = angle;
    }

    public float getAngle()
    {
        return angle;
    }

    public Quarter getArea()
    {
        return area;
    }

    public Set<RotateImageDescription> getImages()
    {
        return images;
    }
}