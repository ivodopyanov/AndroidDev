/**
 * 
 */
package ru.naumen.core.player.controller;

import ru.naumen.core.framework.eventbus.EventBus;
import ru.naumen.core.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.core.game.controller.events.InsertBallInCornerHandler;
import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.controller.events.RotateBoardEvent;
import ru.naumen.core.game.controller.events.RotateCornerEvent;
import ru.naumen.core.game.controller.events.RotateCornerHandler;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public class HumanPlayerController extends PlayerControllerImpl implements InsertBallInCornerHandler,
        RotateCornerHandler
{
    private boolean active = false;

    public HumanPlayerController(Player player, EventBus eventBus, Board board)
    {
        super(player, eventBus, board);
        eventBus.register(InsertBallInCornerEvent.class, this);
        eventBus.register(RotateCornerEvent.class, this);
    }

    @Override
    public void onInsertBallInCorner(InsertBallInCornerEvent event)
    {
        if (active)
        {
            eventBus.fireEvent(new MoveBallEvent(event.getBall(), player.getCode()));
        }
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        active = event.getActivePlayerCode().equals(player.getCode());
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        active = event.getActivePlayerCode().equals(player.getCode());
    }

    @Override
    public void onRotateCorner(RotateCornerEvent event)
    {
        if (active)
        {
            eventBus.fireEvent(new RotateBoardEvent(new RotateInfo(event.getArea(), event.getDirection()), player
                    .getCode()));
        }
    }
}