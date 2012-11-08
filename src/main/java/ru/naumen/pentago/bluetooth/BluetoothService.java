/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import ru.naumen.pentago.bluetooth.threads.BluetoothConnectedThread;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class BluetoothService
{
    private static BluetoothService INSTANCE;

    public static BluetoothService get()
    {
        if (INSTANCE == null)
            INSTANCE = new BluetoothService();
        return INSTANCE;
    }

    private BluetoothHandler handler;

    private BluetoothConnectedThread thread;

    public BluetoothHandler getHandler()
    {
        synchronized (this)
        {
            return handler;
        }
    }

    public BluetoothConnectedThread getThread()
    {
        synchronized (this)
        {
            return thread;
        }
    }

    public void setHandler(BluetoothHandler handler)
    {
        synchronized (this)
        {
            this.handler = handler;
        }
    }

    public void setThread(BluetoothConnectedThread thread)
    {
        synchronized (this)
        {
            this.thread = thread;
        }
    }

    public void stop()
    {
        if (thread != null)
        {
            thread.cancel();
            thread.finish();
        }
        handler = null;
        thread = null;
    }
}