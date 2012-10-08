/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public interface AIRotateStrategy
{
    RotateInfo apply(int player);
}