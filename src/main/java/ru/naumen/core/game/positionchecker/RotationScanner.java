/**
 * 
 */
package ru.naumen.core.game.positionchecker;

import java.util.List;

import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Quarter;
import ru.naumen.core.game.model.RotateInfo;
import ru.naumen.core.game.positionchecker.iterators.LineIterator;
import ru.naumen.core.game.positionchecker.iterators.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class RotationScanner extends PositionScanner
{
    public RotationScanner(CheckPattern[] patterns, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patterns, lineIteratorFactories, board);
    }

    public RotateInfo findMove(int player)
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
                return new RotateInfo(quarter, RotateDirection.Clockwise);
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
                return new RotateInfo(quarter, RotateDirection.CounterClockwise);
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