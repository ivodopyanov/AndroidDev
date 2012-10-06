/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.List;

import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.player.controller.ai.strategies.move.AIMoveStrategy;
import ru.naumen.core.player.controller.ai.strategies.move.RandomMoveStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomMoveCalculator extends AICalculatorImpl<MoveBallEvent> implements BestMoveCalculator
{
    private final AIMoveStrategy strategy;

    public RandomMoveCalculator(List<Player> players, Board board)
    {
        super(players);
        strategy = new RandomMoveStrategy(board);
    }

    @Override
    public MoveBallEvent calculate(String playerCode)
    {
        return new MoveBallEvent(strategy.apply(getPlayerIndex(playerCode)), playerCode);
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