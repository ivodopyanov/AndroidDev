/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.game.Constants.AIStrategy;
import ru.naumen.pentago.game.controller.events.RotateBoardEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.AIRotateStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.DirectAIRotateStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.InvertedAIRotateStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.RandomRotateStrategy;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class StrategicRotateCalculator extends AICalculatorImpl<RotateBoardEvent> implements BestRotateCalculator
{
    private final List<AIRotateStrategy> strategies;

    public StrategicRotateCalculator(List<Player> players, Board board, AIStrategyDescriptor[] strategyDescriptors)
    {
        super(players);
        strategies = new LinkedList<AIRotateStrategy>();
        for (AIStrategyDescriptor strategyDescriptor : strategyDescriptors)
        {
            if (AIStrategy.DIRECT.equals(strategyDescriptor.getStrategyType()))
                strategies.add(new DirectAIRotateStrategyImpl(board, strategyDescriptor.getPatterns()));
            else if (AIStrategy.INVERT.equals(strategyDescriptor.getStrategyType()))
                strategies.add(new InvertedAIRotateStrategyImpl(board, strategyDescriptor.getPatterns()));
        }
        strategies.add(new RandomRotateStrategy());
    }

    @Override
    public RotateBoardEvent calculate(Player player)
    {
        int playerPos = players.indexOf(player);
        for (AIRotateStrategy strategy : strategies)
        {
            RotateInfo rotateInfo = strategy.apply(playerPos);
            if (rotateInfo != null)
                return new RotateBoardEvent(rotateInfo, player);
        }
        return null;
    }
}