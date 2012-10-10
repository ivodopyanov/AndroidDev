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
    private final CheckPatternSet patternSet;

    public LineCheckPredicate(CheckPatternSet patternSet)
    {
        this.patternSet = patternSet;
    }

    public void check(String line, List<Ball> balls, char playerSymbol, int player) throws MoveFoundException
    {
        for (CheckPattern pattern : patternSet.getPatterns())
        {
            int pos = line.indexOf(pattern.getPattern().replace('?', playerSymbol));
            if (pos != -1)
                throw new MoveFoundException(balls.get(pos + pattern.getDisp()), player, patternSet.getWeight());
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder().append("{");
        for (int i = 0; i < patternSet.getPatterns().length; i++)
        {
            if (i != 0)
                sb.append(", ");
            sb.append(patternSet.getPatterns()[i].toString());
        }
        sb.append("}, weight = ").append(patternSet.getWeight());
        return sb.toString();
    }
}