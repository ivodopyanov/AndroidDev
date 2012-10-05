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
import ru.naumen.core.player.controller.ai.strategies.AIMoveStrategy;
import ru.naumen.core.player.controller.ai.strategies.DirectAIMoveStrategyImpl;
import ru.naumen.core.player.controller.ai.strategies.InvertedAIMoveStrategyImpl;
import ru.naumen.core.player.controller.ai.strategies.RandomStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicMoveCalculator extends AICalculatorImpl<MoveBallEvent> implements BestMoveCalculator
{
    private final List<AIMoveStrategy> strategies;

    public StrategicMoveCalculator(List<Player> players)
    {
        super(players);
        //@formatter:off
        strategies = Arrays.asList(
                new DirectAIMoveStrategyImpl(LineCheckPatterns.PLAYER_CAN_WIN), 
                new InvertedAIMoveStrategyImpl(LineCheckPatterns.PLAYER_CAN_WIN), 
                new InvertedAIMoveStrategyImpl(LineCheckPatterns.THREE_IN_A_ROW),
                new DirectAIMoveStrategyImpl(LineCheckPatterns.THREE_IN_A_ROW),
                new DirectAIMoveStrategyImpl(LineCheckPatterns.TWO_IN_A_ROW),
                new DirectAIMoveStrategyImpl(LineCheckPatterns.SINGLE),
                new RandomStrategy());
        //@formatter:on
    }

    @Override
    public MoveBallEvent calculate(Board board, String playerCode)
    {
        int playerPos = getPlayerPos(playerCode);
        for (AIMoveStrategy strategy : strategies)
        {
            Ball ball = strategy.apply(board, playerPos);
            if (ball != null)
                return new MoveBallEvent(ball, playerCode);
        }
        return null;
    }

    private int getPlayerPos(String playerCode)
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (playerCode.equals(players.get(i).getCode()))
                return i;
        }
        return -1;
    }
}