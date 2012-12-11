/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.pentago.game.controller.events.InsertBallInCornerHandler;
import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.controller.events.RotateBoardEvent;
import ru.naumen.pentago.game.controller.events.RotateCornerEvent;
import ru.naumen.pentago.game.controller.events.RotateCornerHandler;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public class HumanController extends PlayerControllerImpl implements InsertBallInCornerHandler, RotateCornerHandler
{
    protected boolean active = false;

    public HumanController(Player player, Board board)
    {
        super(player, board);
        EventBus.INSTANCE.register(InsertBallInCornerEvent.class, this);
        EventBus.INSTANCE.register(RotateCornerEvent.class, this);
    }

    @Override
    public void onInsertBallInCorner(InsertBallInCornerEvent event)
    {
        if (active)
        {
            EventBus.INSTANCE.fireEvent(new MoveBallEvent(event.getBall(), player));
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
            EventBus.INSTANCE.fireEvent(new RotateBoardEvent(new RotateInfo(event.getArea(), event.isClockwise()),
                    player));
        }
    }
}