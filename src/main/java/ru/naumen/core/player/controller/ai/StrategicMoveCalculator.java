/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.Arrays;
import java.util.List;

import ru.naumen.core.game.Constants.LineCheckPatterns;
import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.player.controller.ai.strategies.move.AIMoveStrategy;
import ru.naumen.core.player.controller.ai.strategies.move.DirectAIMoveStrategyImpl;
import ru.naumen.core.player.controller.ai.strategies.move.InvertedAIMoveStrategyImpl;
import ru.naumen.core.player.controller.ai.strategies.move.RandomMoveStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicMoveCalculator extends AICalculatorImpl<MoveBallEvent> implements BestMoveCalculator
{
    private final List<AIMoveStrategy> strategies;

    public StrategicMoveCalculator(List<Player> players, Board board)
    {
        super(players);
        //@formatter:off
        strategies = Arrays.asList(
                new DirectAIMoveStrategyImpl(board, LineCheckPatterns.PLAYER_CAN_WIN), 
                new InvertedAIMoveStrategyImpl(board, LineCheckPatterns.PLAYER_CAN_WIN), 
                new InvertedAIMoveStrategyImpl(board, LineCheckPatterns.THREE_IN_A_ROW),
                new DirectAIMoveStrategyImpl(board, LineCheckPatterns.THREE_IN_A_ROW),
                new DirectAIMoveStrategyImpl(board, LineCheckPatterns.TWO_IN_A_ROW),
                new DirectAIMoveStrategyImpl(board, LineCheckPatterns.SINGLE),
                new RandomMoveStrategy(board));
        //@formatter:on
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