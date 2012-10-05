/**
 * 
 */
package ru.naumen.core.game.positionchecker;

import java.util.List;

import ru.naumen.core.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class LineCheckPredicate
{
    private final CheckPattern[] patterns;

    public LineCheckPredicate(CheckPattern... patterns)
    {
        this.patterns = patterns;
    }

    void check(String line, List<Ball> balls, char playerSymbol, int player) throws MoveFoundException
    {
        for (CheckPattern pattern : patterns)
        {
            int pos = line.indexOf(pattern.getPattern().replace('?', playerSymbol));
            if (pos != -1)
                throw new MoveFoundException(balls.get(pos + pattern.getDisp()), player);
        }
    }
}