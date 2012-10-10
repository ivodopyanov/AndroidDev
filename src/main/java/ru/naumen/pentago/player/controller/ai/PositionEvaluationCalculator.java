/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.Constants.PositionEvaluationPatterns;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.player.controller.ai.poseval.AIPositionEvaluator;
import ru.naumen.pentago.player.controller.ai.poseval.AIPositionEvaluatorImpl;
import android.os.Handler;
import android.os.Looper;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class PositionEvaluationCalculator extends AICalculatorImpl
{
    private class MoveDesc implements Comparable<MoveDesc>
    {
        private final Ball ball;
        private final Quarter quarter;
        private final RotateDirection direction;
        private final double weight;

        public MoveDesc(Ball ball, Quarter quarter, RotateDirection direction, double weight)
        {
            this.ball = ball;
            this.quarter = quarter;
            this.direction = direction;
            this.weight = weight;
        }

        @Override
        public int compareTo(MoveDesc another)
        {
            return (int)(weight - another.weight);
        }

        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof MoveDesc))
                return false;
            MoveDesc moveDesc = (MoveDesc)o;
            return ball.equals(moveDesc.ball) && direction.equals(moveDesc.direction)
                    && quarter.equals(moveDesc.quarter);
        }

        public Ball getBall()
        {
            return ball;
        }

        public RotateDirection getDirection()
        {
            return direction;
        }

        public Quarter getQuarter()
        {
            return quarter;
        }

        public double getWeight()
        {
            return weight;
        }

        @Override
        public int hashCode()
        {
            return quarter.hashCode() + direction.hashCode() + ball.hashCode() + (int)weight;
        }
    }

    private final AIPositionEvaluator positionEvaluator;

    private final Board board;

    public PositionEvaluationCalculator(List<Player> players, Board board, Looper looper, Handler resultHandler)
    {
        super(players, looper, resultHandler);
        this.board = board;
        positionEvaluator = new AIPositionEvaluatorImpl(PositionEvaluationPatterns.ALL,
                LineIteratorFactories.POSITION_CHECK, board);
    }

    @Override
    protected AIMoveInfo runCalculation(Player player)
    {
        List<MoveDesc> data = gatherData(player);
        MoveDesc bestMove = data.get(0);
        return new AIMoveInfo(bestMove.getBall(), new RotateInfo(bestMove.getQuarter(), bestMove.getDirection()));
    }

    private List<MoveDesc> gatherData(Player player)
    {
        List<MoveDesc> result = new LinkedList<MoveDesc>();
        int index = players.indexOf(player);
        for (Ball ball : board.getBalls())
        {
            if (ball.getPlayer() != Ball.NO_PLAYER)
                continue;
            ball.setPlayer(index);
            for (int i = 0; i < 8; i++)
            {
                Quarter quarter = Quarter.create(i / 2);
                RotateDirection direct = i % 2 == 0 ? RotateDirection.Clockwise : RotateDirection.CounterClockwise;
                RotateDirection invert = i % 2 == 0 ? RotateDirection.CounterClockwise : RotateDirection.Clockwise;
                board.rotate(quarter, direct);
                double value = positionEvaluator.evaluate(index);
                board.rotate(quarter, invert);
                result.add(new MoveDesc(ball, quarter, direct, value));
            }
            ball.setPlayer(Ball.NO_PLAYER);
        }
        Collections.sort(result);
        return result;
    }

}