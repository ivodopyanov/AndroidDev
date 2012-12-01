/**
 * 
 */
package ru.naumen.pentago.bluetooth.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.naumen.pentago.bluetooth.BluetoothService;
import ru.naumen.pentago.game.Constants;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothConnectedThread extends Thread
{
    private static final String TAG = "BTConnectedThread";
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler handler;
    private volatile boolean finisher = false;

    public BluetoothConnectedThread(BluetoothSocket socket, Handler handler, Boolean client)
    {
        this.handler = handler;
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try
        {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        }
        catch (IOException e)
        {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
        BluetoothService.get().setThread(this);
        Message message = handler.obtainMessage();
        String remoteName = socket.getRemoteDevice().getName();
        String myName = BluetoothAdapter.getDefaultAdapter().getName();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BluetoothConstants.BLUETOOTH_CLIENT, client);
        bundle.putString(BluetoothConstants.BLUETOOTH_MY_NAME, myName);
        bundle.putString(BluetoothConstants.BLUETOOTH_REMOTE_NAME, remoteName);
        Log.d(TAG, "created connected thread; client=" + client + ", myName=" + myName + ", remoteName=" + remoteName);
        message.what = BluetoothConstants.CONNECTED;
        message.obj = bundle;
        handler.dispatchMessage(message);
    }

    public void cancel()
    {
        try
        {
            mmSocket.close();
        }
        catch (IOException e)
        {
        }
    }

    public void finish()
    {
        finisher = true;
    }

    @Override
    public void run()
    {
        byte[] buffer = new byte[1024];
        int bytes;

        while (!finisher)
        {
            try
            {
                bytes = mmInStream.read(buffer);
                handler.obtainMessage(Constants.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
            }
            catch (IOException e)
            {
                break;
            }
        }
    }

    public void write(byte[] bytes)
    {
        try
        {
            mmOutStream.write(bytes);
        }
        catch (IOException e)
        {
        }
    }
}
