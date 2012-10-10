/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.poseval;

import java.util.List;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.game.positionchecker.LineCheckPredicate;
import ru.naumen.pentago.game.positionchecker.MoveFoundException;
import ru.naumen.pentago.game.positionchecker.PositionScannerMultiPredicate;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class AIPositionEvaluatorImpl extends PositionScannerMultiPredicate implements AIPositionEvaluator
{
    public AIPositionEvaluatorImpl(CheckPatternSet[] patternSets, LineIteratorFactory[] lineIteratorFactories,
            Board board)
    {
        super(patternSets, lineIteratorFactories, board);
    }

    @Override
    public double evaluate(int player)
    {
        double result = 0;
        for (LineIterator lineIterator : lineIterators)
        {
            lineIterator.reset();
            while (lineIterator.hasNext())
            {
                try
                {
                    List<Ball> line = lineIterator.next();
                    for (LineCheckPredicate predicate : predicates)
                    {
                        predicate.check(lineToString(line), line, intToCharacter(player), player);
                    }
                }
                catch (MoveFoundException e)
                {
                    result += e.getWeight();
                }
            }
        }
        return result;
    }
}