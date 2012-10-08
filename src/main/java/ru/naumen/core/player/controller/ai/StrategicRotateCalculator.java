/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.Arrays;
import java.util.List;

import ru.naumen.core.game.Constants.LineCheckPatterns;
import ru.naumen.core.game.controller.events.RotateBoardEvent;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.RotateInfo;
import ru.naumen.core.player.controller.ai.strategies.rotate.AIRotateStrategy;
import ru.naumen.core.player.controller.ai.strategies.rotate.DirectAIRotateStrategyImpl;
import ru.naumen.core.player.controller.ai.strategies.rotate.InvertedAIRotateStrategyImpl;
import ru.naumen.core.player.controller.ai.strategies.rotate.RandomRotateStrategy;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class StrategicRotateCalculator extends AICalculatorImpl<RotateBoardEvent> implements BestRotateCalculator
{
    private final List<AIRotateStrategy> strategies;

    public StrategicRotateCalculator(List<Player> players, Board board)
    {
        super(players);
        //@formatter:off
        strategies = Arrays.asList(
                new DirectAIRotateStrategyImpl(board, LineCheckPatterns.PLAYER_CAN_WIN), 
                new InvertedAIRotateStrategyImpl(board, LineCheckPatterns.PLAYER_CAN_WIN), 
                new InvertedAIRotateStrategyImpl(board, LineCheckPatterns.THREE_IN_A_ROW),
                new DirectAIRotateStrategyImpl(board, LineCheckPatterns.THREE_IN_A_ROW),
                new DirectAIRotateStrategyImpl(board, LineCheckPatterns.TWO_IN_A_ROW),
                new DirectAIRotateStrategyImpl(board, LineCheckPatterns.SINGLE),
                new RandomRotateStrategy());
        //@formatter:on
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