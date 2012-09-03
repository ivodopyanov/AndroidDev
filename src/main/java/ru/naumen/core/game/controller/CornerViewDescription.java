/**
 * 
 */
package ru.naumen.core.game.controller;

import java.util.Set;

import ru.naumen.core.game.model.SquareArea;

/**
 * @author ivodopyanov
 * @since 03.09.2012
 * 
 */
public class CornerViewDescription
{
    private final SquareArea area;
    private final GameController controller;
    private final Set<RotateImageDescription> images;

    public CornerViewDescription(SquareArea area, GameController controller, Set<RotateImageDescription> images)
    {
        this.area = area;
        this.controller = controller;
        this.images = images;
    }

    public SquareArea getArea()
    {
        return area;
    }

    public GameController getController()
    {
        return controller;
    }

    public Set<RotateImageDescription> getImages()
    {
        return images;
    }
}