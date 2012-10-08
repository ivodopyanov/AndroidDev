/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.move;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPattern;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class DirectAIMoveStrategyImpl extends AIMoveStrategyImpl
{
    public DirectAIMoveStrategyImpl(Board board, CheckPattern... patterns)
    {
        super(board, patterns);
    }

    @Override
    public Ball apply(int player)
    {
        return moveScanner.findMove(player);
    }
}