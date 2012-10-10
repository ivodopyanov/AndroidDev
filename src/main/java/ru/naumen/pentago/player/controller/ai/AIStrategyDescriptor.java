/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import ru.naumen.pentago.game.positionchecker.CheckPatternSet;

/**
 * @author ivodopyanov
 * @since 08.10.2012
 * 
 */
public class AIStrategyDescriptor
{
    private final String strategyType;
    private final CheckPatternSet patternSet;

    public AIStrategyDescriptor(String strategyType, CheckPatternSet patternSet)
    {
        this.strategyType = strategyType;
        this.patternSet = patternSet;
    }

    public CheckPatternSet getPatternSet()
    {
        return patternSet;
    }

    public String getStrategyType()
    {
        return strategyType;
    }
}