/**
 * 
 */
package ru.naumen.core.game.positionchecker;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class CheckPattern
{
    private final String pattern;
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