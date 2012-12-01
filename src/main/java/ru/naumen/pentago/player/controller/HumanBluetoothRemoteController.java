/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.bluetooth.events.BluetoothMessageEvent;
import ru.naumen.pentago.bluetooth.events.BluetoothMessageHandler;
import ru.naumen.pentago.framework.Pair;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.events.GameOverEvent;
import ru.naumen.pentago.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.controller.events.RotateBoardEvent;
import ru.naumen.pentago.game.controller.events.RotateCornerEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.player.controller.bluetooth.BluetoothControllerConstants;
import ru.naumen.pentago.player.controller.bluetooth.BluetoothConverterFacade;
import android.bluetooth.BluetoothDevice;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class HumanBluetoothRemoteController extends PlayerControllerImpl implements BluetoothMessageHandler
{
    public HumanBluetoothRemoteController(Player player, EventBus eventBus, Board board)
    {
        super(player, eventBus, board);
        eventBus.register(BluetoothMessageEvent.class, this);
    }

    @Override
    public void onBluetoothMessage(BluetoothMessageEvent event)
    {
        if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(event.getData())
                || BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(event.getData()))
        {
            eventBus.fireEvent(new GameOverEvent(-1));
            return;
        }
        Pair<String, Object> data = BluetoothConverterFacade.convertFromString(event.getData());
        if (BluetoothControllerConstants.INSERTED_BALL.equals(data.getFirst()))
        {
            InsertBallInCornerEvent remoteEvent = (InsertBallInCornerEvent)data.getSecond();
            eventBus.fireEvent(new MoveBallEvent(remoteEvent.getBall(), player));
        }
        else if (BluetoothControllerConstants.ROTATED_BOARD.equals(data.getFirst()))
        {
            RotateCornerEvent remoteEvent = (RotateCornerEvent)data.getSecond();
            eventBus.fireEvent(new RotateBoardEvent(new RotateInfo(remoteEvent.getArea(), remoteEvent.isClockwise()),
                    player));
        }
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
    }
}