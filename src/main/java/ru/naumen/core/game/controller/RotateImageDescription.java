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
    private final int descId;
    private final RotateDirection dir;
    private final CustomView image;

    public RotateImageDescription(CustomView image, int descId, RotateDirection dir)
    {
        this.image = image;
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

    public CustomView getImage()
    {
        return image;
    }
}