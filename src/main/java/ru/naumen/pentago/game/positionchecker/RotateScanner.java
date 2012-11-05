/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.factories.LineIteratorFactory;

/**
 * 
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public class RotateScanner extends PositionScannerSinglePredicate
{
    private final boolean greater;

    public RotateScanner(CheckPatternSet patternSet, LineIteratorFactory[] lineIteratorFactories, Board board,
            boolean greater)
    {
        super(patternSet, lineIteratorFactories, board);
        this.greater = greater;
    }

    public RotateInfo findRotate(int player)
    {
        int originalPositionCount = calculatePositionsCount(player);
        RotateInfo bestOption = null;
        int bestPositionCountAfterRotation = greater ? 0 : 999;
        for (int i = 0; i < 3; i++)
        {
            Quarter quarter = Quarter.create(i);
            int positionCount = checkWithRotation(quarter, player, true);
            if (improves(bestPositionCountAfterRotation, positionCount, true))
            {
                bestPositionCountAfterRotation = positionCount;
                if (improves(originalPositionCount, positionCount, false))
                    bestOption = new RotateInfo(quarter, true);
            }
            positionCount = checkWithRotation(quarter, player, false);
            if (improves(bestPositionCountAfterRotation, positionCount, true))
            {
                bestPositionCountAfterRotation = positionCount;
                if (improves(originalPositionCount, positionCount, false))
                    bestOption = new RotateInfo(quarter, false);
            }
        }
        return bestOption;
    }

    private int calculatePositionsCount(int player)
    {
        int result = 0;
        for (LineIterator lineIterator : lineIterators)
        {
            lineIterator.reset();
            while (lineIterator.hasNext())
            {
                List<Ball> line = lineIterator.next();
                try
                {
                    predicate.check(lineToString(line), line, intToCharacter(player), player);
                }
                catch (MoveFoundException e)
                {
                    result++;
                }
            }
        }
        return result;
    }

    private int checkWithRotation(Quarter quarter, int player, boolean clockwise)
    {
        board.rotate(quarter, clockwise);
        int result = calculatePositionsCount(player);
        board.rotate(quarter, !clockwise);
        return result;
    }

    private boolean improves(int currentValue, int newValue, boolean equals)
    {
        if (equals && currentValue == newValue)
            return true;
        if (greater)
            return newValue > currentValue;
        else
            return newValue < currentValue;
    }
}