/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.framework.Pair;
import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.factories.LineIteratorFactory;

/**
 * Класс вычисляет лучший ход на текущей доске для игрока player. Для этого он
 * перебирает линии на доске для всех возможных поворотов четвертей и находит
 * лучший ход
 * 
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

    public Ball findMove(int player)
    {
        for (int i = 0; i < 4; i++)
        {
            Quarter quarter = Quarter.create(i);
            Ball result = checkWithRotation(quarter, player, true);
            if (result != null)
                return result;
            result = checkWithRotation(quarter, player, false);
            if (result != null)
                return result;
        }
        return null;
    }

    private Ball checkWithRotation(Quarter quarter, int player, boolean clockwise)
    {
        board.rotate(quarter, clockwise);
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
                Pair<Integer, Integer> coors = rotateCoors(ball, quarter, !clockwise);
                return board.getBall(coors.getFirst(), coors.getSecond());
            }
        }
        finally
        {
            board.rotate(quarter, !clockwise);
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

    private Pair<Integer, Integer> rotateCoors(Ball ball, Quarter quarter, boolean clockwise)
    {
        int startx = quarter.isLeft() ? 0 : Constants.BOARD_SIZE / 2;
        int starty = quarter.isTop() ? 0 : Constants.BOARD_SIZE / 2;
        int x = ball.getX() - startx;
        int y = ball.getY() - starty;
        Pair<Integer, Integer> result;
        if (clockwise)
            result = Pair.create(-y - 1 + Constants.BOARD_SIZE / 2 + startx, x + starty);
        else
            result = Pair.create(y + startx, -x - 1 + Constants.BOARD_SIZE / 2 + starty);
        return result;
    }
}