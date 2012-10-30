/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class SearchDevicesListener implements OnClickListener
{
    private final BluetoothAdapter btAdapter;

    public SearchDevicesListener(BluetoothAdapter btAdapter)
    {
        this.btAdapter = btAdapter;
    }

    @Override
    public void onClick(View v)
    {
        btAdapter.startDiscovery();
    }
}