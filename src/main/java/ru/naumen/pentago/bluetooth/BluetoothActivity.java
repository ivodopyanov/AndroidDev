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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
public class BluetoothActivity extends Activity
{
    private static final String TAG = "btActivity";
    private final View.OnClickListener WAIT_FOR_CALL_LISTENER = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Log.d(TAG, "waiting for call");
            final BluetoothServerThread serverThread = new BluetoothServerThread(handler, BluetoothActivity.this,
                    devicesReceiver);
            serverThread.start();
            handler.setConnectionDialog(new AlertDialog.Builder(BluetoothActivity.this)
                    .setTitle(R.string.waitingForCall).setNegativeButton(R.string.cancel, new Dialog.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            serverThread.cancel();
                            serverThread.interrupt();
                        }
                    }).setOnCancelListener(new Dialog.OnCancelListener()
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
            Log.d(TAG, "calling");
            final BluetoothClientThread clientThread = new BluetoothClientThread(devices.get(position), handler,
                    BluetoothActivity.this, devicesReceiver);
            clientThread.start();
            handler.setConnectionDialog(new AlertDialog.Builder(BluetoothActivity.this).setTitle(R.string.stopCalling)
                    .setNegativeButton(R.string.cancel, new Dialog.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            clientThread.cancel();
                            clientThread.interrupt();
                        }
                    }).setOnCancelListener(new Dialog.OnCancelListener()
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
            Log.d(TAG, "searching devices");
            devices.clear();
            deviceAdapter.notifyDataSetChanged();
            btAdapter.startDiscovery();
        }
    };

    private final static BluetoothDeviceComparator btdComparator = new BluetoothDeviceComparator();

    private BluetoothAdapter btAdapter;
    private List<BluetoothDevice> devices;
    private BluetoothDeviceAdapter deviceAdapter;
    private BluetoothDevicesReceiver devicesReceiver;
    private final BluetoothHandler handler = new BluetoothHandler(this);

    @Override
    public void onBackPressed()
    {
        btAdapter.cancelDiscovery();
        unregisterReceiver(devicesReceiver);
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);
        BluetoothService.get().setHandler(handler);
        this.setTitle(R.string.bluetoothConnection);
        this.btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        this.devices = new LinkedList<BluetoothDevice>(pairedDevices);
        Collections.sort(devices, btdComparator);
        this.deviceAdapter = new BluetoothDeviceAdapter(devices, this);
        this.devicesReceiver = new BluetoothDevicesReceiver(deviceAdapter);
        registerReceiver(devicesReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(devicesReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED));
        registerReceiver(devicesReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED));

        ListView list = (ListView)findViewById(R.id.devicesList);
        list.setAdapter(deviceAdapter);
        list.setOnItemClickListener(CONNECT_TO_DEVICE_LISTENER);
        TextView refresh = (TextView)findViewById(R.id.textRefresh);
        refresh.setOnClickListener(SEARCH_DEVICES);
        TextView wait = (TextView)findViewById(R.id.textWaitForCall);
        wait.setOnClickListener(WAIT_FOR_CALL_LISTENER);
    }
}