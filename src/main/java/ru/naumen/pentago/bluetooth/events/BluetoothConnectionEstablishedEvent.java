/**
 * 
 */
package ru.naumen.pentago.bluetooth.events;

import ru.naumen.pentago.framework.eventbus.Event;
import android.bluetooth.BluetoothSocket;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothConnectionEstablishedEvent implements Event<BluetoothConnectionEstablishedHandler>
{
    private final BluetoothSocket socket;

    public BluetoothConnectionEstablishedEvent(BluetoothSocket socket)
    {
        this.socket = socket;
    }

    @Override
    public void dispatch(BluetoothConnectionEstablishedHandler handler)
    {
        handler.onBluetoothConnectionEstablished(this);
    }

    public BluetoothSocket getSocket()
    {
        return socket;
    }

}
