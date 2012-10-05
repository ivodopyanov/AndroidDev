/**
 * 
 */
package ru.naumen.core.player.controller.ai.strategies;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.positionchecker.BoardPositionChecker;
import ru.naumen.core.game.positionchecker.CheckPattern;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class InvertedAIMoveStrategyImpl implements AIMoveStrategy
{
    private final BoardPositionChecker positionChecker;

    public InvertedAIMoveStrategyImpl(CheckPattern... patterns)
    {
        positionChecker = new BoardPositionChecker(patterns);
    }

    @Override
    public Ball apply(Board board, int player)
    {
        return positionChecker.findMove(board, 1 - player);
    }
}