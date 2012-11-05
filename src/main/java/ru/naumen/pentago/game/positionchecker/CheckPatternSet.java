/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

/**
 * Набор CheckPattern, которые задают ситуацию одинаковой ценности
 * 
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class CheckPatternSet
{
    /**
     * Набор паттернов, описывающих ситуацию
     */
    private final CheckPattern[] patterns;
    /**
     * Значение веса-ценности-важности ситуации
     */
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