/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import java.util.List;

import ru.naumen.pentago.R;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDeviceAdapter extends BaseAdapter
{
    private final List<BluetoothDevice> devices;
    private final Context context;

    public BluetoothDeviceAdapter(List<BluetoothDevice> devices, Context context)
    {
        this.devices = devices;
        this.context = context;
    }

    public void addDevice(BluetoothDevice device)
    {
        devices.add(device);
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return devices.size();
    }

    @Override
    public Object getItem(int position)
    {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView != null && convertView instanceof BluetoothDeviceView)
        {
            BluetoothDeviceView oldView = (BluetoothDeviceView)convertView;
            oldView.setDevice(devices.get(position));
            return oldView;
        }
        View result = LayoutInflater.from(context).inflate(R.layout.device, null);
        ((TextView)result.findViewById(R.id.textView1)).setText(devices.get(position).getName());
        return result;
    }
}