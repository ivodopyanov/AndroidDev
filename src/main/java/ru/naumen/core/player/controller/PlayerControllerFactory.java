/**
 * 
 */
package ru.naumen.core.player.controller;

import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.Player.PlayerType;

import com.google.common.base.Function;
import com.google.common.eventbus.EventBus;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class PlayerControllerFactory implements Function<Player, PlayerController>
{
    private final EventBus eventBus;
    private final Board board;

    public PlayerControllerFactory(EventBus eventBus, Board board)
    {
        this.eventBus = eventBus;
        this.board = board;
    }

    @Override
    public PlayerController apply(Player input)
    {
        if (PlayerType.human.equals(input.getType()))
            return new HumanPlayerController(input, eventBus, board);
        else if (PlayerType.computer.equals(input.getType()))
            return new ComputerPlayer(input, eventBus, board);
        return null;
    }
}