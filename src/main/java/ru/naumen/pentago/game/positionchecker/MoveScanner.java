/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;
import ru.naumen.pentago.player.controller.ai.AIMoveInfo;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class MoveScanner extends PositionScannerSinglePredicate
{
    public MoveScanner(CheckPatternSet patternSet, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patternSet, lineIteratorFactories, board);
    }

    public AIMoveInfo findMove(int player)
    {
        for (int i = 0; i < 3; i++)
        {
            Quarter quarter = Quarter.create(i);
            AIMoveInfo result = checkClockwise(quarter, player);
            if (result != null)
                return result;
            result = checkCounterClockwise(quarter, player);
            if (result != null)
                return result;
        }
        return null;
    }

    private AIMoveInfo checkClockwise(Quarter quarter, int player)
    {
        board.rotate(quarter, RotateDirection.Clockwise);
        try
        {
            findMoveInRotated(player);
        }
        catch (MoveFoundException e)
        {
            Ball ball = e.getBall();
            if (!ball.inside(quarter))
                return new AIMoveInfo(ball, new RotateInfo(quarter, RotateDirection.Clockwise));
            else
            {
                int startx = quarter.isLeft() ? 0 : Constants.BOARD_SIZE / 2;
                int starty = quarter.isTop() ? 0 : Constants.BOARD_SIZE / 2;
                int x = ball.getX() - startx;
                int y = ball.getY() - starty;
                int xx = y;
                int yy = -x - 1 + Constants.BOARD_SIZE / 2;
                return new AIMoveInfo(board.getBall(xx + startx, yy + starty), new RotateInfo(quarter,
                        RotateDirection.Clockwise));
            }
        }
        finally
        {
            board.rotate(quarter, RotateDirection.CounterClockwise);
        }
        return null;
    }

    private AIMoveInfo checkCounterClockwise(Quarter quarter, int player)
    {
        board.rotate(quarter, RotateDirection.CounterClockwise);
        try
        {
            findMoveInRotated(player);
        }
        catch (MoveFoundException e)
        {
            Ball ball = e.getBall();
            if (!ball.inside(quarter))
                return new AIMoveInfo(ball, new RotateInfo(quarter, RotateDirection.CounterClockwise));
            else
            {
                int startx = quarter.isLeft() ? 0 : Constants.BOARD_SIZE / 2;
                int starty = quarter.isTop() ? 0 : Constants.BOARD_SIZE / 2;
                int x = ball.getX() - startx;
                int y = ball.getY() - starty;
                int xx = -y - 1 + Constants.BOARD_SIZE / 2;
                int yy = x;
                return new AIMoveInfo(board.getBall(xx + startx, yy + starty), new RotateInfo(quarter,
                        RotateDirection.CounterClockwise));
            }
        }
        finally
        {
            board.rotate(quarter, RotateDirection.Clockwise);
        }
        return null;
    }

    private void findMoveInRotated(int player) throws MoveFoundException
    {
        for (LineIterator lineIterator : lineIterators)
        {
            lineIterator.reset();
            while (lineIterator.hasNext())
            {
                List<Ball> line = lineIterator.next();
                predicate.check(lineToString(line), line, intToCharacter(player), player);
            }
        }
    }

}