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
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class MoveScanner extends PositionScanner
{
    public MoveScanner(CheckPattern[] patterns, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patterns, lineIteratorFactories, board);
    }

    public Ball findMove(int player)
    {
        for (int i = 0; i < 3; i++)
        {
            Quarter quarter = Quarter.create(i);
            board.rotate(quarter, RotateDirection.Clockwise);
            try
            {
                findMoveInRotated(player);
            }
            catch (MoveFoundException e)
            {
                Ball ball = e.getBall();
                if (!ball.inside(quarter))
                    return ball;
                else
                {
                    int startx = quarter.isLeft() ? 0 : Constants.BOARD_SIZE / 2;
                    int starty = quarter.isTop() ? 0 : Constants.BOARD_SIZE / 2;
                    int x = ball.getX() - startx;
                    int y = ball.getY() - starty;
                    int xx = y;
                    int yy = -x - 1 + Constants.BOARD_SIZE / 2;
                    return board.getBall(xx + startx, yy + starty);
                }
            }
            finally
            {
                board.rotate(quarter, RotateDirection.CounterClockwise);
            }
            board.rotate(quarter, RotateDirection.CounterClockwise);
            try
            {
                findMoveInRotated(player);
            }
            catch (MoveFoundException e)
            {
                Ball ball = e.getBall();
                if (!ball.inside(quarter))
                    return ball;
                else
                {
                    int startx = quarter.isLeft() ? 0 : Constants.BOARD_SIZE / 2;
                    int starty = quarter.isTop() ? 0 : Constants.BOARD_SIZE / 2;
                    int x = ball.getX() - startx;
                    int y = ball.getY() - starty;
                    int xx = -y - 1 + Constants.BOARD_SIZE / 2;
                    int yy = x;
                    return board.getBall(xx + startx, yy + starty);
                }
            }
            finally
            {
                board.rotate(quarter, RotateDirection.Clockwise);
            }
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