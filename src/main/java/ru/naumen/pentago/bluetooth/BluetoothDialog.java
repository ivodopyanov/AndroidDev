/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ru.naumen.pentago.R;
import ru.naumen.pentago.bluetooth.threads.BluetoothClientThread;
import ru.naumen.pentago.bluetooth.threads.BluetoothServerThread;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDialog extends Dialog
{
    private final View.OnClickListener WAIT_FOR_CALL_LISTENER = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            serverThread.start();
            handler.setConnectionDialog(new AlertDialog.Builder(getContext()).setTitle(R.string.waitingForCall)
                    .setNegativeButton(R.string.cancel, new OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            serverThread.cancel();
                            serverThread.interrupt();
                        }
                    }).setOnCancelListener(new OnCancelListener()
                    {
                        @Override
                        public void onCancel(DialogInterface dialog)
                        {
                            serverThread.cancel();
                            serverThread.interrupt();
                        }
                    }).show());
        }
    };

    private final OnItemClickListener CONNECT_TO_DEVICE_LISTENER = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            WAIT_FOR_CALL_LISTENER.onClick(null);
            final BluetoothClientThread clientThread = new BluetoothClientThread(devices.get(position), handler,
                    getContext(), devicesReceiver);
            clientThread.start();
            handler.setConnectionDialog(new AlertDialog.Builder(getContext()).setTitle(R.string.stopCalling)
                    .setNegativeButton(R.string.cancel, new OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            clientThread.cancel();
                            clientThread.interrupt();
                        }
                    }).setOnCancelListener(new OnCancelListener()
                    {
                        @Override
                        public void onCancel(DialogInterface dialog)
                        {
                            clientThread.cancel();
                            clientThread.interrupt();
                        }
                    }).show());
        }
    };

    private final View.OnClickListener SEARCH_DEVICES = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            btAdapter.startDiscovery();
        }
    };

    private final OnDismissListener DISMISS_LISTENER = new OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            btAdapter.cancelDiscovery();
            getContext().unregisterReceiver(devicesReceiver);
        }
    };

    private final static BluetoothDeviceComparator btdComparator = new BluetoothDeviceComparator();

    private final BluetoothAdapter btAdapter;
    private final List<BluetoothDevice> devices;
    private final BluetoothDeviceAdapter deviceAdapter;
    private final BluetoothDevicesReceiver devicesReceiver;
    private final BluetoothHandler handler = new BluetoothHandler();
    private final BluetoothServerThread serverThread;

    public BluetoothDialog(Context context)
    {
        super(context);
        setContentView(R.layout.bluetooth);
        this.setOnDismissListener(DISMISS_LISTENER);
        this.setTitle(R.string.bluetoothConnection);
        this.btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        this.devices = new LinkedList<BluetoothDevice>(pairedDevices);
        Collections.sort(devices, btdComparator);
        this.deviceAdapter = new BluetoothDeviceAdapter(devices, context);
        this.devicesReceiver = new BluetoothDevicesReceiver(deviceAdapter);
        context.registerReceiver(devicesReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        serverThread = new BluetoothServerThread(handler, context, devicesReceiver);

        ListView list = (ListView)findViewById(R.id.devicesList);
        list.setAdapter(deviceAdapter);
        list.setOnItemClickListener(CONNECT_TO_DEVICE_LISTENER);
        TextView refresh = (TextView)findViewById(R.id.textRefresh);
        refresh.setOnClickListener(SEARCH_DEVICES);
        TextView wait = (TextView)findViewById(R.id.textWaitForCall);
        wait.setOnClickListener(WAIT_FOR_CALL_LISTENER);
    }
}