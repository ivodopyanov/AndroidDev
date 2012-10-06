package ru.naumen.core.game.positionchecker;

import java.util.List;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.positionchecker.iterators.LineIterator;
import ru.naumen.core.game.positionchecker.iterators.LineIteratorFactory;

public class WinStateScanner extends PositionScanner
{
    public WinStateScanner(CheckPattern[] patterns, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patterns, lineIteratorFactories, board);
    }

    public int hasAnyPlayerWon()
    {
        int[] players = new int[] { 0, 1 };
        try
        {
            for (LineIterator lineIterator : lineIterators)
            {
                lineIterator.reset();
                while (lineIterator.hasNext())
                {
                    List<Ball> line = lineIterator.next();
                    for (int player : players)
                        predicate.check(lineToString(line), line, intToCharacter(player), player);
                }
            }
        }
        catch (MoveFoundException e)
        {
            return e.getBall().getPlayer();
        }
        return Ball.NO_PLAYER;
    }
}