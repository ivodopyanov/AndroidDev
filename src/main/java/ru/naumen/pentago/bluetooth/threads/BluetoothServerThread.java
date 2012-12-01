/**
 * 
 */
package ru.naumen.pentago.bluetooth.threads;

import java.io.IOException;

import ru.naumen.pentago.bluetooth.BluetoothDevicesReceiver;
import ru.naumen.pentago.game.Constants;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothServerThread extends BluetoothThread
{
    private static final String TAG = "BTServerThread";
    private final BluetoothServerSocket serverSocket;
    private final Context context;
    private final BluetoothDevicesReceiver devicesReceiver;

    public BluetoothServerThread(Handler handler, Context context, BluetoothDevicesReceiver devicesReceiver)
    {
        super(handler);
        this.context = context;
        this.devicesReceiver = devicesReceiver;
        BluetoothServerSocket tmp = null;
        try
        {
            tmp = btAdapter.listenUsingRfcommWithServiceRecord(Constants.APPLICATION_TITLE, Constants.uuid);
        }
        catch (IOException e)
        {
        }
        serverSocket = tmp;
    }

    /** отмена ожидания сокета */
    public void cancel()
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
        }
    }

    @Override
    public void run()
    {
        BluetoothSocket socket = null;
        while (true)
        {
            try
            {
                socket = serverSocket.accept();
            }
            catch (IOException e)
            {
                break;
            }
            if (socket != null)
            {
                try
                {
                    Log.d(TAG, "connected");
                    context.unregisterReceiver(devicesReceiver);
                    manageConnectedSocket(socket, false);
                    serverSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}