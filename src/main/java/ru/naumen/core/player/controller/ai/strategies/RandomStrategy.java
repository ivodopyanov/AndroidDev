/**
 * 
 */
package ru.naumen.core.player.controller.ai.strategies;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.core.framework.collections.Collections;
import ru.naumen.core.framework.collections.Predicate;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomStrategy implements AIMoveStrategy
{
    private static final Predicate<Ball> FREE_BALL_FILTER = new Predicate<Ball>()
    {
        @Override
        public boolean apply(Ball ball)
        {
            return ball.getPlayer() == Ball.NO_PLAYER;
        }
    };

    @Override
    public Ball apply(Board board, int player)
    {
        List<Ball> freeBalls = new ArrayList<Ball>(Collections.filter(board.getBalls(), FREE_BALL_FILTER));
        int pos = (int)(Math.random() * (freeBalls.size() - 1));
        return freeBalls.get(pos);
    }
}