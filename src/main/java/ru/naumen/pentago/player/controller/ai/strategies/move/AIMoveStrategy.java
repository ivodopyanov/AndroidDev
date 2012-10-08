/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.move;

import ru.naumen.pentago.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AIMoveStrategy
{
    Ball apply(int player);
}
