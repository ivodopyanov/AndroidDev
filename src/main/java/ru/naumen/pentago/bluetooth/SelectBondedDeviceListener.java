/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import java.util.List;

import ru.naumen.pentago.bluetooth.events.BluetoothDeviceSelectedEvent;
import ru.naumen.pentago.framework.eventbus.EventBus;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class SelectBondedDeviceListener implements OnItemClickListener
{
    private final EventBus eventBus;
    private final List<BluetoothDevice> devices;

    public SelectBondedDeviceListener(EventBus eventBus, List<BluetoothDevice> devices)
    {
        this.eventBus = eventBus;
        this.devices = devices;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        eventBus.fireEvent(new BluetoothDeviceSelectedEvent(devices.get(position)));
    }
}