/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import ru.naumen.pentago.game.positionchecker.CheckPattern;

/**
 * @author ivodopyanov
 * @since 08.10.2012
 * 
 */
public class AIStrategyDescriptor
{
    private final String strategyType;
    private final CheckPattern[] patterns;

    public AIStrategyDescriptor(String strategyType, CheckPattern[] patterns)
    {
        this.strategyType = strategyType;
        this.patterns = patterns;
    }

    public CheckPattern[] getPatterns()
    {
        return patterns;
    }

    public String getStrategyType()
    {
        return strategyType;
    }
}