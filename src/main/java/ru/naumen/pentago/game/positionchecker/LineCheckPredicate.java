/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.List;

import ru.naumen.pentago.game.model.Ball;

/**
 * Класс, который проверяет соответствие строки line, описывающей очередную
 * линию шариков и пустых мест, и набора паттернов patternSet. В случае успеха
 * выбрасывает MoveFoundExceptionс шариком, который надо поставить
 * 
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

    /**
     * Выбрасывает MoveFoundException, если строка подходит под описание позиции
     */
    public void check(String line, List<Ball> balls, char playerSymbol, int player) throws MoveFoundException
    {
        for (CheckPattern pattern : patternSet.getPatterns())
        {
            int pos = line.indexOf(pattern.getPattern().replace('?', playerSymbol));
            if (pos != -1)
                throw new MoveFoundException(balls.get(pos + pattern.getDisp()), player, patternSet.getWeight());
        }
    }

    /**
     * Возвращает вес строки в соответствии с паттерном
     */
    public double evaluate(String line, List<Ball> balls, char playerSymbol, int player)
    {
        double result = 0;
        for (CheckPattern pattern : patternSet.getPatterns())
        {
            if (line.indexOf(pattern.getPattern().replace('?', playerSymbol)) != -1)
                result += patternSet.getWeight();
        }
        return result;
    }

    public boolean isInverted()
    {
        return patternSet.getWeight() < 0;
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