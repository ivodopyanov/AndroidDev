/**
 * 
 */
package ru.naumen.pentago.player.controller.bluetooth;

import ru.naumen.pentago.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.pentago.game.model.Ball;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class BluetoothConverterInsertedBallImpl implements BluetoothConverter
{

    @Override
    public Object convertFromString(String value)
    {
        String[] splitted = value.split(",");
        return new InsertBallInCornerEvent(new Ball(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]),
                Ball.NO_PLAYER));
    }

    @Override
    public String convertToString(Object value)
    {
        InsertBallInCornerEvent event = (InsertBallInCornerEvent)value;
        return new StringBuilder().append(event.getBall().getX()).append(",").append(event.getBall().getY()).toString();
    }
}