/**
 * 
 */
package ru.naumen.core.game.controller;

import ru.naumen.core.game.controller.GameController.RotateDirection;

/**
 * @author ivodopyanov
 * @since 03.09.2012
 * 
 */
public class RotateImageDescription
{
    private final int imageId;
    private final int descId;
    private final RotateDirection dir;
    private final float angle;
    private final boolean flip;

    public RotateImageDescription(int imageId, int descId, RotateDirection dir, float angle, boolean flip)
    {
        this.imageId = imageId;
        this.descId = descId;
        this.dir = dir;
        this.angle = angle;
        this.flip = flip;
    }

    public float getAngle()
    {
        return angle;
    }

    public int getDescId()
    {
        return descId;
    }

    public RotateDirection getDir()
    {
        return dir;
    }

    public int getImageId()
    {
        return imageId;
    }

    public boolean isFlip()
    {
        return flip;
    }
}