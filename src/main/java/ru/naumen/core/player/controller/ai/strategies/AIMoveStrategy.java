/**
 * 
 */
package ru.naumen.core.player.controller.ai.strategies;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AIMoveStrategy
{
    Ball apply(Board board, int player);
}
