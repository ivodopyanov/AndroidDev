/**
 * 
 */
package ru.naumen.pentago.bluetooth.threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothThread extends Thread
{
    protected final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private final Handler handler;
    protected BluetoothConnectedThread btThread;

    protected BluetoothThread(Handler handler)
    {
        this.handler = handler;
    }

    public BluetoothConnectedThread getBluetoothThread()
    {
        return btThread;
    }

    protected void manageConnectedSocket(BluetoothSocket socket, Boolean client)
    {
        btThread = new BluetoothConnectedThread(socket, handler, client);
        btThread.start();
    }
}