/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.move;

import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPattern;
import ru.naumen.pentago.game.positionchecker.MoveScanner;

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
