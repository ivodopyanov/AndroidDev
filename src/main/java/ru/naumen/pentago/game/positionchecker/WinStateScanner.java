package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;

public class WinStateScanner extends PositionScannerSinglePredicate
{
    public WinStateScanner(CheckPatternSet patternSet, LineIteratorFactory[] lineIteratorFactories, Board board)
    {
        super(patternSet, lineIteratorFactories, board);
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