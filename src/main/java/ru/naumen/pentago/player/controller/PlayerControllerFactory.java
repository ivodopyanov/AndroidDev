/**
 * 
 */
package ru.naumen.pentago.player.controller;

import java.util.List;

import ru.naumen.pentago.framework.collections.Function;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.AIStrategies;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Player.PlayerType;
import ru.naumen.pentago.player.controller.ai.StrategicMoveCalculator;
import ru.naumen.pentago.player.controller.ai.StrategicRotateCalculator;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class PlayerControllerFactory implements Function<Player, PlayerController>
{
    private final EventBus eventBus;
    private final Board board;
    private final List<Player> players;

    public PlayerControllerFactory(EventBus eventBus, Board board, List<Player> players)
    {
        this.eventBus = eventBus;
        this.board = board;
        this.players = players;
    }

    @Override
    public PlayerController apply(Player input)
    {
        if (PlayerType.human.equals(input.getType()))
            return new HumanPlayerController(input, eventBus, board);
        else if (PlayerType.computer.equals(input.getType()))
            return new ComputerPlayer(input, eventBus, board, new StrategicMoveCalculator(players, board,
                    AIStrategies.ONLY_POSITIVE), new StrategicRotateCalculator(players, board,
                    AIStrategies.ONLY_POSITIVE));
        return null;
    }
}