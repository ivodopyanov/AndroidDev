/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class CheckPatternSet
{
    private final CheckPattern[] patterns;
    private final double weight;

    public CheckPatternSet(CheckPattern[] patterns, double weight)
    {
        this.patterns = patterns;
        this.weight = weight;
    }

    public CheckPattern[] getPatterns()
    {
        return patterns;
    }

    public double getWeight()
    {
        return weight;
    }
}