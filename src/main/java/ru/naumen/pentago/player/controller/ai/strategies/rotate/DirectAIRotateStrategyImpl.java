/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public class DirectAIRotateStrategyImpl extends AIRotateStrategyImpl
{
    public DirectAIRotateStrategyImpl(Board board, CheckPatternSet patternSet)
    {
        super(board, patternSet, true);
    }

    @Override
    public RotateInfo apply(int player)
    {
        return rotateScanner.findRotate(player);
    }
}
