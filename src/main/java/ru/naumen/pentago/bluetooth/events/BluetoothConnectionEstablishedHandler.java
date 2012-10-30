/**
 * 
 */
package ru.naumen.pentago.bluetooth.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public interface BluetoothConnectionEstablishedHandler extends Handler
{
    void onBluetoothConnectionEstablished(BluetoothConnectionEstablishedEvent event);
}