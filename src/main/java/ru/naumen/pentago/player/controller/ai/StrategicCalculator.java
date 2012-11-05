/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.player.controller.ai.strategies.move.AIMoveStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.move.DirectAIMoveStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.move.InvertedAIMoveStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.move.RandomMoveStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.AIRotateStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.DirectAIRotateStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.InvertedAIRotateStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.rotate.RandomRotateStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicCalculator extends AICalculatorImpl
{
    private final List<AIMoveStrategy> moveStrategies;
    private final List<AIRotateStrategy> rotateStrategies;

    public StrategicCalculator(List<Player> players, Board board, AIStrategyDescriptor[] strategyDescriptors,
            EventBus eventBus)
    {
        super(players, eventBus);
        moveStrategies = new LinkedList<AIMoveStrategy>();
        for (AIStrategyDescriptor strategyDescriptor : strategyDescriptors)
        {
            if (Constants.AIStrategy.DIRECT.equals(strategyDescriptor.getStrategyType()))
                moveStrategies.add(new DirectAIMoveStrategyImpl(board, strategyDescriptor.getPatternSet()));
            else if (Constants.AIStrategy.INVERT.equals(strategyDescriptor.getStrategyType()))
                moveStrategies.add(new InvertedAIMoveStrategyImpl(board, strategyDescriptor.getPatternSet()));
        }
        moveStrategies.add(new RandomMoveStrategy(board));
        rotateStrategies = new LinkedList<AIRotateStrategy>();
        for (AIStrategyDescriptor strategyDescriptor : strategyDescriptors)
        {
            if (Constants.AIStrategy.DIRECT.equals(strategyDescriptor.getStrategyType()))
                rotateStrategies.add(new DirectAIRotateStrategyImpl(board, strategyDescriptor.getPatternSet()));
            else if (Constants.AIStrategy.INVERT.equals(strategyDescriptor.getStrategyType()))
                rotateStrategies.add(new InvertedAIRotateStrategyImpl(board, strategyDescriptor.getPatternSet()));
        }
        rotateStrategies.add(new RandomRotateStrategy());
    }

    @Override
    protected Ball runMoveCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        for (AIMoveStrategy strategy : moveStrategies)
        {
            Ball result = strategy.apply(playerPos);
            if (result != null)
                return result;
        }
        return null;
    }

    @Override
    protected RotateInfo runRotateCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        for (AIRotateStrategy strategy : rotateStrategies)
        {
            RotateInfo result = strategy.apply(playerPos);
            if (result != null)
                return result;
        }
        return null;
    }
}