/**
 * 
 */
package ru.naumen.pentago.bluetooth.events;

import ru.naumen.pentago.framework.eventbus.Event;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class BluetoothMessageEvent implements Event<BluetoothMessageHandler>
{
    private final String data;

    public BluetoothMessageEvent(String data)
    {
        this.data = data;
    }

    @Override
    public void dispatch(BluetoothMessageHandler handler)
    {
        handler.onBluetoothMessage(this);
    }

    public String getData()
    {
        return data;
    }
}