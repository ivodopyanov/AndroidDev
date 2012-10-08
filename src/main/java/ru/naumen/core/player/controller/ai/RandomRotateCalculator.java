/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.List;

import ru.naumen.core.game.controller.events.RotateBoardEvent;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.player.controller.ai.strategies.rotate.AIRotateStrategy;
import ru.naumen.core.player.controller.ai.strategies.rotate.RandomRotateStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomRotateCalculator extends AICalculatorImpl<RotateBoardEvent> implements BestRotateCalculator
{
    private final AIRotateStrategy strategy;

    public RandomRotateCalculator(List<Player> players)
    {
        super(players);
        strategy = new RandomRotateStrategy();
    }

    @Override
    public RotateBoardEvent calculate(Player player)
    {
        return new RotateBoardEvent(strategy.apply(players.indexOf(player)), player);
    }
}