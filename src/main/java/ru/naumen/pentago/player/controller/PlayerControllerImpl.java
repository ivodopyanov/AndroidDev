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
    protected final EventBus eventBus;
    protected final Board board;

    public PlayerControllerImpl(Player player, EventBus eventBus, Board board)
    {
        this.player = player;
        this.eventBus = eventBus;
        this.board = board;
        eventBus.register(RequestBoardRotateEvent.class, this);
        eventBus.register(RequestBallMoveEvent.class, this);
    }
}