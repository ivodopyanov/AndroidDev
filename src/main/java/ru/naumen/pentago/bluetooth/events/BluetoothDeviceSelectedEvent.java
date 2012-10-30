/**
 * 
 */
package ru.naumen.pentago.bluetooth.events;

import ru.naumen.pentago.framework.eventbus.Event;
import android.bluetooth.BluetoothDevice;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDeviceSelectedEvent implements Event<BluetoothDeviceSelectedHandler>
{
    private final BluetoothDevice device;

    public BluetoothDeviceSelectedEvent(BluetoothDevice device)
    {
        this.device = device;
    }

    @Override
    public void dispatch(BluetoothDeviceSelectedHandler handler)
    {
        handler.onBluetoothDeviceSelected(this);
    }

    public BluetoothDevice getDevice()
    {
        return device;
    }
}