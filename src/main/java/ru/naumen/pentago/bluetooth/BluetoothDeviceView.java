/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import android.R;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDeviceView extends TextView
{
    public BluetoothDeviceView(Context context, BluetoothDevice device)
    {
        super(context);
        setText(device.getName());
        setTextAppearance(context, R.attr.textAppearanceLarge);
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setLayoutParams(lp);
    }

    public void setDevice(BluetoothDevice device)
    {
        setText(device.getName());
    }
}