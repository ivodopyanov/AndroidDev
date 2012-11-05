/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.game.positionchecker.RotateScanner;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public abstract class AIRotateStrategyImpl implements AIRotateStrategy
{
    protected final RotateScanner rotateScanner;

    public AIRotateStrategyImpl(Board board, CheckPatternSet patternSet, boolean greater)
    {
        rotateScanner = new RotateScanner(patternSet, LineIteratorFactories.POSITION_CHECK, board, greater);
    }
}