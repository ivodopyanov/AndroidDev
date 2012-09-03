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
    private final int imageResourceId;
    private final int descId;
    private final RotateDirection dir;

    public RotateImageDescription(int imageId, int imageResourceId, int descId, RotateDirection dir)
    {
        this.imageId = imageId;
        this.imageResourceId = imageResourceId;
        this.descId = descId;
        this.dir = dir;
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

    public int getImageResourceId()
    {
        return imageResourceId;
    }
}