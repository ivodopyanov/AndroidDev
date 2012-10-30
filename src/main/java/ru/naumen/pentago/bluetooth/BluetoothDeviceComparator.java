/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import java.util.Comparator;

import android.bluetooth.BluetoothDevice;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDeviceComparator implements Comparator<BluetoothDevice>
{
    @Override
    public int compare(BluetoothDevice arg0, BluetoothDevice arg1)
    {
        return arg0.getName().compareTo(arg1.getName());
    }
}