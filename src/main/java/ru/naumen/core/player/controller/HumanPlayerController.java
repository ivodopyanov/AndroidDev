/**
 * 
 */
package ru.naumen.core.player.controller;

import ru.naumen.core.game.controller.events.BallInsertedInCornerEvent;
import ru.naumen.core.game.controller.events.CornerRotatedEvent;
import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.controller.events.RotateBoardEvent;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public class HumanPlayerController extends PlayerControllerImpl
{
    private boolean active = false;

    public HumanPlayerController(Player player, EventBus eventBus, Board board)
    {
        super(player, eventBus, board);
    }

    @Subscribe
    public void ballInsertedInCorner(BallInsertedInCornerEvent event)
    {
        if (active)
        {
            eventBus.post(new MoveBallEvent(event.getBall(), player.getCode()));
        }
    }

    @Subscribe
    public void cornerRotatedEvent(CornerRotatedEvent event)
    {
        if (active)
        {
            eventBus.post(new RotateBoardEvent(event.getArea(), event.getDirection(), player.getCode()));
        }
    }

    @Override
    @Subscribe
    public void requestBoardRotation(RequestBoardRotateEvent event)
    {
        active = event.getActivePlayerCode().equals(player.getCode());
    }

    @Override
    @Subscribe
    public void requestMove(RequestBallMoveEvent event)
    {
        active = event.getActivePlayerCode().equals(player.getCode());
    }
}