/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public interface AIRotateStrategy
{
    RotateInfo apply(int player);
}