/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.rotate;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.CheckPattern;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class DirectAIRotateStrategyImpl extends AIRotateStrategyImpl
{
    public DirectAIRotateStrategyImpl(Board board, CheckPattern[] patterns)
    {
        super(board, patterns);
    }

    @Override
    public RotateInfo apply(int player)
    {
        return rotationScanner.findMove(player);
    }
}