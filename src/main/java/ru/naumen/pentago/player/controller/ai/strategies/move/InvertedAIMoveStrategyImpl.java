/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.move;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class InvertedAIMoveStrategyImpl extends AIMoveStrategyImpl
{
    public InvertedAIMoveStrategyImpl(Board board, CheckPatternSet patternSet)
    {
        super(board, patternSet);
    }

    @Override
    public Ball apply(int player)
    {
        return moveScanner.findMove(1 - player);
    }
}