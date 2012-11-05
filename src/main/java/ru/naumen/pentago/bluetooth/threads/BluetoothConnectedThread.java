/**
 * 
 */
package ru.naumen.pentago.bluetooth.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.naumen.pentago.game.Constants;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothConnectedThread extends Thread
{
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler handler;

    public BluetoothConnectedThread(BluetoothSocket socket, Handler handler)
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

    @Override
    public void run()
    {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true)
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
