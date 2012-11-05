/**
 * 
 */
package ru.naumen.pentago.bluetooth.threads;

import java.io.IOException;

import ru.naumen.pentago.bluetooth.BluetoothDevicesReceiver;
import ru.naumen.pentago.game.Constants;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothClientThread extends BluetoothThread
{
    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private final Context context;
    private final BluetoothDevicesReceiver devicesReceiver;

    public BluetoothClientThread(BluetoothDevice device, Handler handler, Context context,
            BluetoothDevicesReceiver devicesReceiver)
    {
        super(handler);
        this.device = device;
        this.context = context;
        this.devicesReceiver = devicesReceiver;
        BluetoothSocket tmp = null;
        try
        {
            tmp = device.createRfcommSocketToServiceRecord(Constants.uuid);
        }
        catch (IOException e)
        {
        }
        socket = tmp;
    }

    public void cancel()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
        }
    }

    @Override
    public void run()
    {
        btAdapter.cancelDiscovery();

        try
        {
            socket.connect();
        }
        catch (IOException connectException)
        {
            try
            {
                socket.close();
            }
            catch (IOException closeException)
            {
            }
            return;
        }
        context.unregisterReceiver(devicesReceiver);
        manageConnectedSocket(socket);
        btThread.write(BluetoothConstants.INSTALLED.getBytes());
    }
}
