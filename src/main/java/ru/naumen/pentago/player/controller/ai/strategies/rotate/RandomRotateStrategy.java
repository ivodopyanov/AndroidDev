/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public class RandomRotateStrategy implements AIRotateStrategy
{
    @Override
    public RotateInfo apply(int player)
    {
        Quarter quarter = Quarter.create((int)(Math.random() * 3));
        boolean clockwise = Math.random() < 0.5 ? true : false;
        return new RotateInfo(quarter, clockwise);
    }
}