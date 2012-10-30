/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDevicesReceiver extends BroadcastReceiver
{
    private final BluetoothDeviceAdapter deviceAdapter;

    public BluetoothDevicesReceiver(BluetoothDeviceAdapter deviceAdapter)
    {
        this.deviceAdapter = deviceAdapter;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (!BluetoothDevice.ACTION_FOUND.equals(intent.getAction()))
            return;
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        deviceAdapter.addDevice(device);
    }
}