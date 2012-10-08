/**
 * 
 */
package ru.naumen.pentago.game.positionchecker.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public abstract class LineIterator implements Iterator<List<Ball>>
{
    protected final Board board;
    private List<Ball> line;
    protected List<Ball> nextLine;
    protected int pos = -1;
    private final int limit;

    protected LineIterator(Board board, int limit)
    {
        this.board = board;
        this.limit = limit;
        line = new LinkedList<Ball>();
        nextLine = new LinkedList<Ball>();
    }

    @Override
    public boolean hasNext()
    {
        return !nextLine.isEmpty();
    }

    @Override
    public List<Ball> next()
    {
        List<Ball> buf = line;
        line = nextLine;
        nextLine = buf;
        findNextNotEmptyLine();
        return line;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    public void reset()
    {
        pos = -1;
        findNextNotEmptyLine();
    }

    protected abstract void fillNextLine();

    private void findNextNotEmptyLine()
    {
        do
        {
            nextLine.clear();
            pos++;
            if (pos == limit)
                return;
            fillNextLine();
        }
        while (nextLine.isEmpty());
    }
}