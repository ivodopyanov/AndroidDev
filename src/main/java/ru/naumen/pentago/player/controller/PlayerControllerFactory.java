/**
 * 
 */
package ru.naumen.pentago.player.controller;

import java.util.List;

import ru.naumen.pentago.framework.collections.Function;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.StrategicCalculator;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class PlayerControllerFactory implements Function<Player, PlayerController>
{
    private final Board board;
    private final StrategicCalculator strCalc;

    public PlayerControllerFactory(Board board, List<Player> players)
    {
        this.board = board;
        strCalc = new StrategicCalculator(players, board);
    }

    @Override
    public PlayerController apply(Player input)
    {
        switch (input.getType())
        {
        case human:
            return new HumanController(input, board);
        case computer:
            return new ComputerController(input, board, strCalc);
        case humanBluetoothLocal:
            return new HumanBluetoothLocalController(input, board);
        case humanBluetoothRemote:
            return new HumanBluetoothRemoteController(input, board);
        }
        return null;
    }
}