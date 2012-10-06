/**
 * 
 */
package ru.naumen.core.player.controller.ai.strategies.move;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.positionchecker.CheckPattern;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class InvertedAIMoveStrategyImpl extends AIMoveStrategyImpl
{
    public InvertedAIMoveStrategyImpl(Board board, CheckPattern... patterns)
    {
        super(board, patterns);
    }

    @Override
    public Ball apply(int player)
    {
        return moveScanner.findMove(1 - player);
    }
}