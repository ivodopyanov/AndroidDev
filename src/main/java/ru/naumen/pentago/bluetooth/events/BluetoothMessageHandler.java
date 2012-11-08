/**
 * 
 */
package ru.naumen.pentago.bluetooth.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public interface BluetoothMessageHandler extends Handler
{
    void onBluetoothMessage(BluetoothMessageEvent event);
}