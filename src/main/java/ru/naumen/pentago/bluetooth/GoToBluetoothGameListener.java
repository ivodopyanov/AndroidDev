/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import ru.naumen.pentago.HideDialogClickListener;
import ru.naumen.pentago.R;
import ru.naumen.pentago.bluetooth.threads.BluetoothConstants;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class GoToBluetoothGameListener implements OnClickListener
{
    private static final String TAG = "goToBTListener";

    private final Activity activity;
    private final EventBus eventBus;

    public GoToBluetoothGameListener(Activity activity, EventBus eventBus)
    {
        this.activity = activity;
        this.eventBus = eventBus;
    }

    @Override
    public void onClick(View v)
    {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null)
        {
            Log.d(TAG, "btAdapter==null");
            new AlertDialog.Builder(activity).setTitle(R.string.bluetoothConnection)
                    .setMessage(activity.getResources().getString(R.string.noBluetooth))
                    .setPositiveButton("OK", new HideDialogClickListener()).show();
            return;
        }
        if (!btAdapter.isEnabled())
        {
            Log.d(TAG, "start intent enable bt");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, Constants.REQUEST_BT_ENABLED);
            return;
        }
        else
        {
            Log.d(TAG, "start intent discoverable bt");
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            activity.startActivityForResult(discoverableIntent, Constants.REQUEST_BT_DISCOVERABLE);
        }
    }

    public void showDialog()
    {
        Log.d(TAG, "show dialog");
        Intent bluetoothIntent = new Intent(activity, BluetoothActivity.class);
        activity.startActivityForResult(bluetoothIntent, BluetoothConstants.BLUETOOTH_ACTIVITY_RESULT);
    }
}