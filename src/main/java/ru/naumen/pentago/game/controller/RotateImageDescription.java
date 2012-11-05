/**
 * 
 */
package ru.naumen.pentago.game.controller;

import android.widget.ImageView;

/**
 * @author ivodopyanov
 * @since 03.09.2012
 * 
 */
public class RotateImageDescription
{
    private final int descId;
    private final boolean clockwise;
    private final ImageView image;

    public RotateImageDescription(ImageView image, int descId, boolean clockwise)
    {
        this.image = image;
        this.descId = descId;
        this.clockwise = clockwise;
    }

    public int getDescId()
    {
        return descId;
    }

    public ImageView getImage()
    {
        return image;
    }

    public boolean isClockwise()
    {
        return clockwise;
    }
}