/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.List;

import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.player.controller.ai.strategies.AIMoveStrategy;
import ru.naumen.core.player.controller.ai.strategies.RandomStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomMoveCalculator extends AICalculatorImpl<MoveBallEvent> implements BestMoveCalculator
{
    private final AIMoveStrategy strategy = new RandomStrategy();

    public RandomMoveCalculator(List<Player> players)
    {
        super(players);
    }

    @Override
    public MoveBallEvent calculate(Board board, String playerCode)
    {
        return new MoveBallEvent(strategy.apply(board, getPlayerIndex(playerCode)), playerCode);
    }

    private int getPlayerIndex(String playerCode)
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getCode().equals(playerCode))
                return i;
        }
        return -1;
    }
}