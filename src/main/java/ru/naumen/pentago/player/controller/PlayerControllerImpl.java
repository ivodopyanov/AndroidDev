/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public abstract class PlayerControllerImpl implements PlayerController
{
    protected final Player player;
    protected final Board board;

    public PlayerControllerImpl(Player player, Board board)
    {
        this.player = player;
        this.board = board;
        EventBus.INSTANCE.register(RequestBoardRotateEvent.class, this);
        EventBus.INSTANCE.register(RequestBallMoveEvent.class, this);
    }
}