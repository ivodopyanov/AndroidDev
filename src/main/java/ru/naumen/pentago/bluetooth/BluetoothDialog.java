/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import ru.naumen.pentago.HideDialogClickListener;
import ru.naumen.pentago.R;
import ru.naumen.pentago.bluetooth.events.BluetoothConnectionEstablishedEvent;
import ru.naumen.pentago.bluetooth.events.BluetoothDeviceSelectedEvent;
import ru.naumen.pentago.bluetooth.events.BluetoothDeviceSelectedHandler;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class BluetoothDialog extends Dialog implements BluetoothDeviceSelectedHandler
{
    private final View.OnClickListener WAIT_FOR_CALL_LISTENER = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            BluetoothServerSocket serverSocket = null;
            BluetoothSocket socket = null;
            try
            {
                serverSocket = btAdapter.listenUsingRfcommWithServiceRecord(Constants.APPLICATION_TITLE, uuid);
                socket = serverSocket.accept();
                serverSocket.close();
                eventBus.fireEvent(new BluetoothConnectionEstablishedEvent(socket));
            }
            catch (IOException e)
            {
                try
                {
                    if (socket != null)
                    {
                        socket.close();
                    }
                    if (serverSocket != null)
                    {
                        serverSocket.close();
                    }
                }
                catch (IOException e2)
                {

                }

                new AlertDialog.Builder(getContext()).setTitle(R.string.bluetoothConnection)
                        .setMessage(getContext().getResources().getString(R.string.bluetoothConnectionError))
                        .setPositiveButton("OK", new HideDialogClickListener()).show();
            }

        }
    };

    private final static BluetoothDeviceComparator btdComparator = new BluetoothDeviceComparator();
    private final static UUID uuid = UUID.fromString(Constants.APPLICATION_TITLE);

    private final BluetoothAdapter btAdapter;
    private final List<BluetoothDevice> devices;
    private final BluetoothDeviceAdapter deviceAdapter;
    private final BluetoothDevicesReceiver devicesReceiver;
    private final EventBus eventBus;

    public BluetoothDialog(Context context, EventBus eventBus)
    {
        super(context);
        this.eventBus = eventBus;
        setContentView(R.layout.bluetooth);
        this.btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        this.devices = new LinkedList<BluetoothDevice>(pairedDevices);
        Collections.sort(devices, btdComparator);
        this.deviceAdapter = new BluetoothDeviceAdapter(devices, context);
        this.devicesReceiver = new BluetoothDevicesReceiver(deviceAdapter);
        context.registerReceiver(devicesReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        ListView list = (ListView)findViewById(R.id.devicesList);
        list.setAdapter(deviceAdapter);
        list.setOnItemClickListener(new SelectBondedDeviceListener(eventBus, devices));
        TextView refresh = (TextView)findViewById(R.id.textRefresh);
        refresh.setOnClickListener(new SearchDevicesListener(btAdapter));
        TextView wait = (TextView)findViewById(R.id.textWaitForCall);
        wait.setOnClickListener(WAIT_FOR_CALL_LISTENER);
    }

    @Override
    public void onBluetoothDeviceSelected(BluetoothDeviceSelectedEvent event)
    {
        btAdapter.cancelDiscovery();
        getContext().unregisterReceiver(devicesReceiver);
        BluetoothSocket socket = null;
        try
        {
            socket = event.getDevice().createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            eventBus.fireEvent(new BluetoothConnectionEstablishedEvent(socket));
        }
        catch (IOException e)
        {
            try
            {
                if (socket != null)
                    socket.close();
            }
            catch (IOException e2)
            {
            }
            new AlertDialog.Builder(getContext()).setTitle(R.string.bluetoothConnection)
                    .setMessage(getContext().getResources().getString(R.string.bluetoothConnectionError))
                    .setPositiveButton("OK", new HideDialogClickListener()).show();
        }
    }
}