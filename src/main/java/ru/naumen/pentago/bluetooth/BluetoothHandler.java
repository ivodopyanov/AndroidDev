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

/**
 * @author ivodopyanov
 * @since 01.11.2012
 * 
 */
public class BluetoothHandler extends Handler
{
    private Dialog connectionDialog;
    private final Activity activity;
    private EventBus eventBus;

    public BluetoothHandler(Activity activity)
    {
        this.activity = activity;
    }

    public EventBus getEventBus()
    {
        return eventBus;
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.what)
        {
        case BluetoothConstants.CONNECTED:
        {
            connectionDialog.dismiss();
            Intent intent = new Intent();
            intent.replaceExtras((Bundle)msg.obj);
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
            break;
        }
        case Constants.MESSAGE_READ:
        {
            byte[] bytes = (byte[])msg.obj;
            String data = new String(bytes);
            if (eventBus != null)
                eventBus.fireEvent(new BluetoothMessageEvent(data));
        }
        }
    }

    public void setConnectionDialog(Dialog connectionDialog)
    {
        this.connectionDialog = connectionDialog;
    }

    public void setEventBus(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }
}