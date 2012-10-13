/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.strategies.RandomStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomCalculator extends AICalculatorImpl
{
    private final RandomStrategy strategy;

    public RandomCalculator(List<Player> players, Board board, EventBus eventBus)
    {
        super(players, eventBus);
        strategy = new RandomStrategy(board);
    }

    @Override
    protected AIMoveInfo runCalculation(Player player)
    {
        int index = players.indexOf(player);
        return strategy.apply(index);
    }
}