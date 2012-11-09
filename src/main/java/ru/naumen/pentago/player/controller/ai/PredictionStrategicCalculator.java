/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 09.11.2012
 * 
 */
public class PredictionStrategicCalculator extends AICalculatorImpl
{
    private final StrategicCalculator strategicCalculator;

    public PredictionStrategicCalculator(List<Player> players, Board board, EventBus eventBus)
    {
        super(players, eventBus);
        this.strategicCalculator = new StrategicCalculator(players, board, eventBus);
    }

    @Override
    protected Ball runMoveCalculation(Player player)
    {
        return null;
    }

    @Override
    protected RotateInfo runRotateCalculation(Player player)
    {
        return null;
    }
}