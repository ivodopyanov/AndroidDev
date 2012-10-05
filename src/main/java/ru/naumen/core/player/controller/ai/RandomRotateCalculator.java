/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.List;

import ru.naumen.core.game.Constants;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.controller.events.RotateBoardEvent;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.SquareArea;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class RandomRotateCalculator extends AICalculatorImpl<RotateBoardEvent> implements BestRotateCalculator
{
    public RandomRotateCalculator(List<Player> players)
    {
        super(players);
    }

    @Override
    public RotateBoardEvent calculate(Board board, String playerCode)
    {
        int left = (int)Math.random();
        int top = (int)Math.random();
        RotateDirection dir = Math.random() < 0.5 ? RotateDirection.Clockwise : RotateDirection.CounterClockwise;
        return new RotateBoardEvent(new SquareArea(Constants.BOARD_SIZE * left / 2, Constants.BOARD_SIZE * top / 2,
                Constants.BOARD_SIZE / 2), dir, playerCode);
    }
}