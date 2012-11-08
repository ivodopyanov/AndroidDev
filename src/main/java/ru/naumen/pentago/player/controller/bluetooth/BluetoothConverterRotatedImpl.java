/**
 * 
 */
package ru.naumen.pentago.player.controller.bluetooth;

import ru.naumen.pentago.game.controller.events.RotateCornerEvent;
import ru.naumen.pentago.game.model.Quarter;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class BluetoothConverterRotatedImpl implements BluetoothConverter
{
    @Override
    public Object convertFromString(String value)
    {
        String[] splitted = value.split(",");
        return new RotateCornerEvent(Quarter.create(Boolean.parseBoolean(splitted[0]),
                Boolean.parseBoolean(splitted[1])), Boolean.parseBoolean(splitted[2]));
    }

    @Override
    public String convertToString(Object value)
    {
        RotateCornerEvent event = (RotateCornerEvent)value;
        return new StringBuilder().append(event.getArea().isLeft()).append(",").append(event.getArea().isTop())
                .append(",").append(event.isClockwise()).toString();
    }
}