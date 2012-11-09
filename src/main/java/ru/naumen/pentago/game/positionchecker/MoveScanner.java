/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.framework.Pair;
import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.collections.Predicate;
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
public class MoveScanner extends PositionScannerMultiPredicate
{
    private static final Predicate<Ball> FREE_BALL_FILTER = new Predicate<Ball>()
    {
        @Override
        public boolean apply(Ball ball)
        {
            return ball.getPlayer() == Ball.NO_PLAYER;
        }
    };

    public MoveScanner(CheckPatternSet[] patternSets, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patternSets, lineIteratorFactories, board);
    }

    public List<Ball> findMove(int player)
    {
        List<Ball> result = new LinkedList<Ball>();
        for (LineCheckPredicate predicate : predicates)
        {
            int playerForPredicate = player;
            if (predicate.isInverted())
                playerForPredicate = 1 - player;
            findInPredicate(predicate, playerForPredicate, result);
        }
        if (result.size() == 0)
            result.add(getRandomBall());
        return result;
    }

    private void checkLine(List<Ball> line, LineCheckPredicate predicate, Quarter quarter, int player,
            boolean clockwise, List<Ball> result)
    {
        try
        {
            predicate.check(lineToString(line), line, intToCharacter(player), player);
        }
        catch (MoveFoundException e)
        {
            Ball ball = e.getBall();
            if (!result.contains(ball))
            {
                if (!ball.inside(quarter))
                    result.add(ball);
                else
                {
                    Pair<Integer, Integer> coors = rotateCoors(ball, quarter, !clockwise);
                    result.add(board.getBall(coors.getFirst(), coors.getSecond()));
                }
            }
        }
    }

    private void findInLines(LineCheckPredicate predicate, Quarter quarter, int player, boolean clockwise,
            List<Ball> result)
    {

        for (LineIterator lineIterator : lineIterators)
        {
            lineIterator.reset();
            while (lineIterator.hasNext())
            {
                checkLine(lineIterator.next(), predicate, quarter, player, clockwise, result);
            }
        }
    }

    private void findInPredicate(LineCheckPredicate predicate, int player, List<Ball> result)
    {
        for (int i = 0; i < 4; i++)
        {
            Quarter quarter = Quarter.create(i);
            findInRotation(predicate, quarter, player, true, result);
            findInRotation(predicate, quarter, player, false, result);
        }
    }

    private void findInRotation(LineCheckPredicate predicate, Quarter quarter, int player, boolean clockwise,
            List<Ball> result)
    {
        board.rotate(quarter, clockwise);
        findInLines(predicate, quarter, player, clockwise, result);
        board.rotate(quarter, !clockwise);
    }

    private Ball getRandomBall()
    {
        List<Ball> freeBalls = new ArrayList<Ball>(Collections.filter(board.getBalls(), FREE_BALL_FILTER));
        int pos = (int)(Math.random() * (freeBalls.size() - 1));
        return freeBalls.get(pos);
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