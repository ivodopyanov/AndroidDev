/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.bluetooth.BluetoothService;
import ru.naumen.pentago.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.pentago.game.controller.events.RotateCornerEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.bluetooth.BluetoothControllerConstants;
import ru.naumen.pentago.player.controller.bluetooth.BluetoothConverterFacade;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class HumanBluetoothLocalController extends HumanController
{
    public HumanBluetoothLocalController(Player player, Board board)
    {
        super(player, board);
    }

    @Override
    public void onInsertBallInCorner(InsertBallInCornerEvent event)
    {
        if (active)
        {
            BluetoothService
                    .get()
                    .getThread()
                    .write(BluetoothConverterFacade.convertToString(BluetoothControllerConstants.INSERTED_BALL, event)
                            .getBytes());
        }
        super.onInsertBallInCorner(event);
    }

    @Override
    public void onRotateCorner(RotateCornerEvent event)
    {
        if (active)
        {
            BluetoothService
                    .get()
                    .getThread()
                    .write(BluetoothConverterFacade.convertToString(BluetoothControllerConstants.ROTATED_BOARD, event)
                            .getBytes());
        }
        super.onRotateCorner(event);
    }
}