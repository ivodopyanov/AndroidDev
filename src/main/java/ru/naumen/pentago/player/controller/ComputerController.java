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
public class ComputerController extends PlayerControllerImpl implements MoveCalculatedHandler, RotateCalculatedHandler
{
    private final AICalculator aiCalc;

    public ComputerController(Player player, Board board, AICalculator aiCalc)
    {
        super(player, board);
        this.aiCalc = aiCalc;
        EventBus.INSTANCE.register(MoveCalculatedEvent.class, this);
        EventBus.INSTANCE.register(RotateCalculatedEvent.class, this);
    }

    @Override
    public void onMoveCalculated(MoveCalculatedEvent event)
    {
        if (!event.getPlayer().equals(player))
            return;
        Log.d(LogTag.COMPUTER, "onMoveCalculated");
        EventBus.INSTANCE.fireEvent(new MoveBallEvent(event.getBall(), player));
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
        EventBus.INSTANCE.fireEvent(new RotateBoardEvent(event.getRotateInfo(), player));
    }
}