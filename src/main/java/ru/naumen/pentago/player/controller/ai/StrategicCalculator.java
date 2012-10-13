/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.strategies.AIStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.DirectAIStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.InvertedAIStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.RandomStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicCalculator extends AICalculatorImpl
{
    private final List<AIStrategy> strategies;

    public StrategicCalculator(List<Player> players, Board board, AIStrategyDescriptor[] strategyDescriptors,
            EventBus eventBus)
    {
        super(players, eventBus);
        strategies = new LinkedList<AIStrategy>();
        for (AIStrategyDescriptor strategyDescriptor : strategyDescriptors)
        {
            if (Constants.AIStrategy.DIRECT.equals(strategyDescriptor.getStrategyType()))
                strategies.add(new DirectAIStrategyImpl(board, strategyDescriptor.getPatternSet()));
            else if (Constants.AIStrategy.INVERT.equals(strategyDescriptor.getStrategyType()))
                strategies.add(new InvertedAIStrategyImpl(board, strategyDescriptor.getPatternSet()));
        }
        strategies.add(new RandomStrategy(board));
    }

    @Override
    protected AIMoveInfo runCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        for (AIStrategy strategy : strategies)
        {
            AIMoveInfo result = strategy.apply(playerPos);
            if (result != null)
                return result;
        }
        return null;
    }
}