/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.framework.Pair;
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
    private static final Comparator<Pair<RotateInfo, Double>> ROTATE_INFO_COMPARATOR = new Comparator<Pair<RotateInfo, Double>>()
    {
        @Override
        public int compare(Pair<RotateInfo, Double> object1, Pair<RotateInfo, Double> object2)
        {
            return (int)(object2.getSecond() - object1.getSecond());
        }
    };

    private final PositionEvaluator evaluator;
    private final Board board;

    public RotateScanner(CheckPatternSet[] patternSets, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        this.evaluator = new PositionEvaluator(patternSets, lineIteratorFactories, board);
        this.board = board;
    }

    public List<RotateInfo> findRotate(int player)
    {
        List<Pair<RotateInfo, Double>> buf = new LinkedList<Pair<RotateInfo, Double>>();
        for (int i = 0; i < 4; i++)
        {
            Quarter quarter = Quarter.create(i);
            double positionValue = checkWithRotation(quarter, player, true);
            buf.add(Pair.create(new RotateInfo(quarter, true), positionValue));
            positionValue = checkWithRotation(quarter, player, false);
            buf.add(Pair.create(new RotateInfo(quarter, false), positionValue));
        }
        Collections.sort(buf, ROTATE_INFO_COMPARATOR);
        List<RotateInfo> result = new ArrayList<RotateInfo>();
        for (Pair<RotateInfo, Double> pair : buf)
        {
            result.add(pair.getFirst());
        }
        return result;
    }

    private double checkWithRotation(Quarter quarter, int player, boolean clockwise)
    {
        board.rotate(quarter, clockwise);
        double result = evaluator.evaluate(player);
        board.rotate(quarter, !clockwise);
        return result;
    }
}