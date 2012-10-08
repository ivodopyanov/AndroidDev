/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPattern;
import ru.naumen.pentago.game.positionchecker.RotationScanner;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public abstract class AIRotateStrategyImpl implements AIRotateStrategy
{
    protected final RotationScanner rotationScanner;

    protected AIRotateStrategyImpl(Board board, CheckPattern... patterns)
    {
        rotationScanner = new RotationScanner(patterns, LineIteratorFactories.POSITION_CHECK, board);
    }
}