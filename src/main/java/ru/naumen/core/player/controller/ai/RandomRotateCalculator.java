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
    public RotateBoardEvent calculate(String playerCode)
    {
        return new RotateBoardEvent(strategy.apply(getPlayerIndex(playerCode)), playerCode);
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