/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.iterators.factories.LineIteratorFactory;

/**
 * 
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public class RotateScanner
{
    private final PositionEvaluator evaluator;
    private final Board board;

    public RotateScanner(CheckPatternSet[] patternSets, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        this.evaluator = new PositionEvaluator(patternSets, lineIteratorFactories, board);
        this.board = board;
    }

    public RotateInfo findRotate(int player)
    {
        double bestPositionValue = evaluator.evaluate(player);
        RotateInfo bestOption = null;
        for (int i = 0; i < 4; i++)
        {
            Quarter quarter = Quarter.create(i);
            double positionValue = checkWithRotation(quarter, player, true);
            if (positionValue > bestPositionValue)
            {
                bestPositionValue = positionValue;
                bestOption = new RotateInfo(quarter, true);
            }
            positionValue = checkWithRotation(quarter, player, false);
            if (positionValue > bestPositionValue)
            {
                bestPositionValue = positionValue;
                bestOption = new RotateInfo(quarter, false);
            }
        }
        return bestOption;
    }

    private double checkWithRotation(Quarter quarter, int player, boolean clockwise)
    {
        board.rotate(quarter, clockwise);
        double result = evaluator.evaluate(player);
        board.rotate(quarter, !clockwise);
        return result;
    }
}