/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.game.Constants.AIStrategy;
import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.strategies.move.AIMoveStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.move.DirectAIMoveStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.move.InvertedAIMoveStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.move.RandomMoveStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicMoveCalculator extends AICalculatorImpl<MoveBallEvent> implements BestMoveCalculator
{
    private final List<AIMoveStrategy> strategies;

    public StrategicMoveCalculator(List<Player> players, Board board, AIStrategyDescriptor[] strategyDescriptors)
    {
        super(players);
        strategies = new LinkedList<AIMoveStrategy>();
        for (AIStrategyDescriptor strategyDescriptor : strategyDescriptors)
        {
            if (AIStrategy.DIRECT.equals(strategyDescriptor.getStrategyType()))
                strategies.add(new DirectAIMoveStrategyImpl(board, strategyDescriptor.getPatterns()));
            else if (AIStrategy.INVERT.equals(strategyDescriptor.getStrategyType()))
                strategies.add(new InvertedAIMoveStrategyImpl(board, strategyDescriptor.getPatterns()));
        }
        strategies.add(new RandomMoveStrategy(board));
    }

    @Override
    public MoveBallEvent calculate(Player player)
    {
        int playerPos = players.indexOf(player);
        for (AIMoveStrategy strategy : strategies)
        {
            Ball ball = strategy.apply(playerPos);
            if (ball != null)
                return new MoveBallEvent(ball, player);
        }
        return null;
    }
}