/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies;

import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.game.positionchecker.MoveScanner;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public abstract class AIStrategyImpl implements AIStrategy
{
    protected final MoveScanner moveScanner;

    protected AIStrategyImpl(Board board, CheckPatternSet patternSet)
    {
        moveScanner = new MoveScanner(patternSet, LineIteratorFactories.POSITION_CHECK, board);
    }
}