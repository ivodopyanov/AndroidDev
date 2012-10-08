/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.game.model.Ball;

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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder().append("{");
        for (int i = 0; i < patterns.length; i++)
        {
            if (i != 0)
                sb.append(", ");
            sb.append(patterns[i].toString());
        }
        sb.append("}");
        return sb.toString();
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