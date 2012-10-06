/**
 * 
 */
package ru.naumen.core.game.positionchecker;

import java.util.LinkedList;
import java.util.List;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.positionchecker.iterators.LineIterator;
import ru.naumen.core.game.positionchecker.iterators.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class PositionScanner
{
    protected final LineCheckPredicate predicate;
    protected final List<LineIterator> lineIterators;
    protected final Board board;

    public PositionScanner(CheckPattern[] patterns, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        this.predicate = new LineCheckPredicate(patterns);
        this.board = board;
        lineIterators = new LinkedList<LineIterator>();
        for (LineIteratorFactory lineIteratorFactory : lineIteratorFactories)
        {
            lineIterators.add(lineIteratorFactory.create(board));
        }
    }

    protected char intToCharacter(int player)
    {
        return (char)(player + 48);
    }

    protected String lineToString(List<Ball> balls)
    {
        StringBuilder sb = new StringBuilder();
        for (Ball ball : balls)
        {
            if (ball.getPlayer() == Ball.NO_PLAYER)
                sb.append("O");
            else
                sb.append(intToCharacter(ball.getPlayer()));
        }
        return sb.toString();
    }
}
