/**
 * 
 */
package ru.naumen.pentago.bluetooth;

import ru.naumen.pentago.HideDialogClickListener;
import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class GoToBluetoothGameListener implements OnClickListener
{

    private final Activity activity;
    private final EventBus eventBus;
    private final BluetoothDialog dialog;

    public GoToBluetoothGameListener(Activity activity, EventBus eventBus)
    {
        this.dialog = new BluetoothDialog(activity, eventBus);
        this.activity = activity;
        this.eventBus = eventBus;
    }

    @Override
    public void onClick(View v)
    {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null)
        {
            new AlertDialog.Builder(activity).setTitle(R.string.bluetoothConnection)
                    .setMessage(activity.getResources().getString(R.string.noBluetooth))
                    .setPositiveButton("OK", new HideDialogClickListener()).show();
            return;
        }
        if (!btAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, Constants.REQUEST_BT_ENABLED);
            return;
        }
        showDialog();
    }

    public void showDialog()
    {
        dialog.show();
    }
}