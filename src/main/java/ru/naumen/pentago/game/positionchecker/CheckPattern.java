/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

/**
 * POJO для описания последовательности шариков, которые определяют следующий
 * ход
 * 
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class CheckPattern
{
    /**
     * Строка с символами ? и O, где ? - это шарик игрока, O - это пустое место,
     * куда надо поставить шарик
     */
    private final String pattern;
    /**
     * Номер позиции сммвола O
     */
    private final int disp;

    public CheckPattern(String pattern, int disp)
    {
        this.pattern = pattern;
        this.disp = disp;
    }

    public int getDisp()
    {
        return disp;
    }

    public String getPattern()
    {
        return pattern;
    }

    @Override
    public String toString()
    {
        return pattern;
    }
}