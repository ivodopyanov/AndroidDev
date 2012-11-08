/**
 * 
 */
package ru.naumen.pentago.player.controller.bluetooth;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public interface BluetoothConverter
{
    Object convertFromString(String value);

    String convertToString(Object value);
}