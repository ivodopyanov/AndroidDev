/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.strategies.move.AIMoveStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.move.RandomMoveStrategy;

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
    public MoveBallEvent calculate(Player player)
    {
        return new MoveBallEvent(strategy.apply(players.indexOf(player)), player);
    }
}