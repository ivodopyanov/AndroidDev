/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.strategies.RandomStrategy;
import android.os.Handler;
import android.os.Looper;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomCalculator extends AICalculatorImpl
{
    private final RandomStrategy strategy;

    public RandomCalculator(List<Player> players, Board board, Looper looper, Handler resultHandler)
    {
        super(players, looper, resultHandler);
        strategy = new RandomStrategy(board);
    }

    @Override
    protected AIMoveInfo runCalculation(Player player)
    {
        int index = players.indexOf(player);
        return strategy.apply(index);
    }
}