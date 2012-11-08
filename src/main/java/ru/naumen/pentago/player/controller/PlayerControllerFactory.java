/**
 * 
 */
package ru.naumen.pentago.player.controller;

import java.util.List;

import ru.naumen.pentago.framework.collections.Function;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Player.PlayerType;
import ru.naumen.pentago.player.controller.ai.StrategicCalculator;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class PlayerControllerFactory implements Function<Player, PlayerController>
{
    private final EventBus eventBus;
    private final Board board;
    private final StrategicCalculator strCalc;

    public PlayerControllerFactory(EventBus eventBus, Board board, List<Player> players)
    {
        this.eventBus = eventBus;
        this.board = board;
        strCalc = new StrategicCalculator(players, board, eventBus);

    }

    @Override
    public PlayerController apply(Player input)
    {
        if (PlayerType.human.equals(input.getType()))
            return new HumanPlayerController(input, eventBus, board);
        else if (PlayerType.computer.equals(input.getType()))
            return new ComputerPlayer(input, eventBus, board, strCalc);
        return null;
    }
}