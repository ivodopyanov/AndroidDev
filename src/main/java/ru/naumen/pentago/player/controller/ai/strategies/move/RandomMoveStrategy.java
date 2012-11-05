/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies.move;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.collections.Predicate;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomMoveStrategy implements AIMoveStrategy
{
    private static final Predicate<Ball> FREE_BALL_FILTER = new Predicate<Ball>()
    {
        @Override
        public boolean apply(Ball ball)
        {
            return ball.getPlayer() == Ball.NO_PLAYER;
        }
    };

    private final Board board;

    public RandomMoveStrategy(Board board)
    {
        this.board = board;
    }

    @Override
    public Ball apply(int player)
    {
        return getRandomBall();
    }

    private Ball getRandomBall()
    {
        List<Ball> freeBalls = new ArrayList<Ball>(Collections.filter(board.getBalls(), FREE_BALL_FILTER));
        int pos = (int)(Math.random() * (freeBalls.size() - 1));
        return freeBalls.get(pos);
    }
}