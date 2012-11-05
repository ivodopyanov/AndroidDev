/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothHandler extends Handler
{
    private Dialog connectionDialog;

    @Override
    public void handleMessage(Message msg)
    {
        String test = "";
        connectionDialog.dismiss();
    }

    public void setConnectionDialog(Dialog connectionDialog)
    {
        this.connectionDialog = connectionDialog;
    }
}