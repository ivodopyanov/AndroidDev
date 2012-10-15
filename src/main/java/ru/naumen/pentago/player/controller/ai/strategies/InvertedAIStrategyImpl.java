/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.player.controller.ai.AIMoveInfo;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class InvertedAIStrategyImpl extends AIStrategyImpl
{
    public InvertedAIStrategyImpl(Board board, CheckPatternSet patternSet)
    {
        super(board, patternSet);
    }

    @Override
    public AIMoveInfo apply(int player)
    {
        return moveScanner.findMove(1 - player);
    }
}