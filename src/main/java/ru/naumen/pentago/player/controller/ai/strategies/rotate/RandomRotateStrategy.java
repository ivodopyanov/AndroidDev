/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class RandomRotateStrategy implements AIRotateStrategy
{
    @Override
    public RotateInfo apply(int player)
    {
        Quarter quarter = Quarter.create((int)(Math.random() * 3));
        RotateDirection direction = Math.random() < 0.5 ? RotateDirection.Clockwise : RotateDirection.CounterClockwise;
        return new RotateInfo(quarter, direction);
    }
}