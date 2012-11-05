/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.LogTag;
import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.controller.events.RotateBoardEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.AICalculator;
import ru.naumen.pentago.player.controller.ai.events.MoveCalculatedEvent;
import ru.naumen.pentago.player.controller.ai.events.MoveCalculatedHandler;
import ru.naumen.pentago.player.controller.ai.events.RotateCalculatedEvent;
import ru.naumen.pentago.player.controller.ai.events.RotateCalculatedHandler;
import android.util.Log;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public class ComputerPlayer extends PlayerControllerImpl implements MoveCalculatedHandler, RotateCalculatedHandler
{
    private final AICalculator aiCalc;

    public ComputerPlayer(Player player, EventBus eventBus, Board board, AICalculator aiCalc)
    {
        super(player, eventBus, board);
        this.aiCalc = aiCalc;
        eventBus.register(MoveCalculatedEvent.class, this);
        eventBus.register(RotateCalculatedEvent.class, this);
    }

    @Override
    public void onMoveCalculated(MoveCalculatedEvent event)
    {
        if (!event.getPlayer().equals(player))
            return;
        Log.d(LogTag.COMPUTER, "onMoveCalculated");
        eventBus.fireEvent(new MoveBallEvent(event.getBall(), player));
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        if (!event.getActivePlayerCode().equals(player.getCode()))
            return;
        Log.d(LogTag.COMPUTER, "onRequestBallMove");
        aiCalc.calculateMove(player);
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        if (!event.getActivePlayerCode().equals(player.getCode()))
            return;
        Log.d(LogTag.COMPUTER, "onRequestBoardRotate");
        aiCalc.calculateRotate(player);

    }

    @Override
    public void onRotateCalculated(RotateCalculatedEvent event)
    {
        if (!event.getPlayer().equals(player))
            return;
        Log.d(LogTag.COMPUTER, "onRotateCalculated");
        eventBus.fireEvent(new RotateBoardEvent(event.getRotateInfo(), player));
    }
}