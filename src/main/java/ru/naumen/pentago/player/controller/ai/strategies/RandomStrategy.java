/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.collections.Predicate;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.player.controller.ai.AIMoveInfo;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomStrategy implements AIStrategy
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

    public RandomStrategy(Board board)
    {
        this.board = board;
    }

    @Override
    public AIMoveInfo apply(int player)
    {
        return new AIMoveInfo(getRandomBall(), getRandomRotate());
    }

    private Ball getRandomBall()
    {
        List<Ball> freeBalls = new ArrayList<Ball>(Collections.filter(board.getBalls(), FREE_BALL_FILTER));
        int pos = (int)(Math.random() * (freeBalls.size() - 1));
        return freeBalls.get(pos);
    }

    private RotateInfo getRandomRotate()
    {
        Quarter quarter = Quarter.create((int)(Math.random() * 3));
        RotateDirection direction = Math.random() < 0.5 ? RotateDirection.Clockwise : RotateDirection.CounterClockwise;
        return new RotateInfo(quarter, direction);
    }
}