/**
 * 
 */
package ru.naumen.pentago.player.controller.bluetooth;

import java.util.HashMap;
import java.util.Map;

import ru.naumen.pentago.framework.Pair;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class BluetoothConverterFacade
{
    private static final Map<String, BluetoothConverter> converters;

    static
    {
        converters = new HashMap<String, BluetoothConverter>();
        converters.put(BluetoothControllerConstants.INSERTED_BALL, new BluetoothConverterInsertedBallImpl());
        converters.put(BluetoothControllerConstants.ROTATED_BOARD, new BluetoothConverterRotatedImpl());
    }

    public static Pair<String, Object> convertFromString(String message)
    {
        String[] splitted = message.split(":");
        String messageCode = splitted[0];
        return Pair.create(messageCode, converters.get(messageCode).convertFromString(splitted[1]));
    }

    public static String convertToString(String messageCode, Object object)
    {
        return new StringBuilder().append(messageCode).append(":")
                .append(converters.get(messageCode).convertToString(object)).toString();
    }
}