/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import ru.naumen.pentago.bluetooth.events.BluetoothMessageEvent;
import ru.naumen.pentago.bluetooth.threads.BluetoothConstants;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothHandler extends Handler
{
    private static final String TAG = "BTHandler";
    private Dialog connectionDialog;
    private final Activity activity;

    public BluetoothHandler(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.what)
        {
        case BluetoothConstants.CONNECTED:
        {
            Log.d(TAG, "connected");
            connectionDialog.dismiss();
            Intent intent = new Intent();
            intent.replaceExtras((Bundle)msg.obj);
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
            break;
        }
        case Constants.MESSAGE_READ:
        {
            byte[] bytes = (byte[])msg.obj;
            String data = new String(bytes, 0, msg.arg1);
            EventBus.INSTANCE.fireEvent(new BluetoothMessageEvent(data));
        }
        }
    }

    public void setConnectionDialog(Dialog connectionDialog)
    {
        this.connectionDialog = connectionDialog;
    }
}