/**
 * 
 */
package ru.naumen.core.player.controller.ai.strategies.move;

import ru.naumen.core.game.Constants.LineIteratorFactories;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.positionchecker.CheckPattern;
import ru.naumen.core.game.positionchecker.MoveScanner;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public abstract class AIMoveStrategyImpl implements AIMoveStrategy
{
    protected final MoveScanner moveScanner;

    protected AIMoveStrategyImpl(Board board, CheckPattern... patterns)
    {
        moveScanner = new MoveScanner(patterns, LineIteratorFactories.POSITION_CHECK, board);
    }
}
