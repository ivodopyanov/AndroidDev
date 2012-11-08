/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.LinkedList;
import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.LineCheckPatternSets;
import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.game.positionchecker.RotateScanner;
import ru.naumen.pentago.player.controller.ai.strategies.move.AIMoveStrategy;
import ru.naumen.pentago.player.controller.ai.strategies.move.DirectAIMoveStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.move.InvertedAIMoveStrategyImpl;
import ru.naumen.pentago.player.controller.ai.strategies.move.RandomMoveStrategy;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicCalculator extends AICalculatorImpl
{
    private final List<AIMoveStrategy> moveStrategies;
    private final RotateScanner rotateScanner;

    public StrategicCalculator(List<Player> players, Board board, EventBus eventBus)
    {
        super(players, eventBus);
        moveStrategies = new LinkedList<AIMoveStrategy>();
        for (CheckPatternSet patternSet : LineCheckPatternSets.MOVE)
        {
            if (patternSet.getWeight() > 0)
                moveStrategies.add(new DirectAIMoveStrategyImpl(board, patternSet));
            else
                moveStrategies.add(new InvertedAIMoveStrategyImpl(board, patternSet));
        }
        moveStrategies.add(new RandomMoveStrategy(board));
        rotateScanner = new RotateScanner(LineCheckPatternSets.ROTATE, LineIteratorFactories.POSITION_CHECK, board);
    }

    @Override
    protected Ball runMoveCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        for (AIMoveStrategy strategy : moveStrategies)
        {
            Ball result = strategy.apply(playerPos);
            if (result != null)
                return result;
        }
        return null;
    }

    @Override
    protected RotateInfo runRotateCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        RotateInfo result = rotateScanner.findRotate(playerPos);
        if (result != null)
            return result;
        return getRandomRotateInfo();
    }

    private RotateInfo getRandomRotateInfo()
    {
        Quarter quarter = Quarter.create((int)(Math.random() * 3));
        boolean clockwise = Math.random() < 0.5 ? true : false;
        return new RotateInfo(quarter, clockwise);
    }
}