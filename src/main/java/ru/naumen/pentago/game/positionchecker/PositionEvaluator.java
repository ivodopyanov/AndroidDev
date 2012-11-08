/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.factories.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 06.11.2012
 * 
 */
public class PositionEvaluator extends PositionScannerMultiPredicate
{
    public PositionEvaluator(CheckPatternSet[] patternSets, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patternSets, lineIteratorFactories, board);
    }

    public double evaluate(int player)
    {
        int result = 0;
        for (LineIterator lineIterator : lineIterators)
        {
            lineIterator.reset();
            while (lineIterator.hasNext())
            {
                List<Ball> line = lineIterator.next();
                for (LineCheckPredicate predicate : predicates)
                {
                    int playerToEvaluate = player;
                    if (predicate.isInverted())
                        playerToEvaluate = 1 - playerToEvaluate;
                    double value = predicate.evaluate(lineToString(line), line, intToCharacter(playerToEvaluate),
                            playerToEvaluate);
                    if (value != 0)
                    {
                        result += value;
                        break;
                    }
                }
            }
        }
        return result;
    }
}
