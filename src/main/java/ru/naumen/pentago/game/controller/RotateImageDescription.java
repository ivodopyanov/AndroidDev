/**
 * 
 */
package ru.naumen.pentago.game.controller;

import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import android.widget.ImageView;

/**
 * @author ivodopyanov
 * @since 03.09.2012
 * 
 */
public class RotateImageDescription
{
    private final int descId;
    private final RotateDirection dir;
    private final ImageView image;

    public RotateImageDescription(ImageView image, int descId, RotateDirection dir)
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

    public ImageView getImage()
    {
        return image;
    }
}