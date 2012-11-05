package ru.naumen.pentago;

import ru.naumen.pentago.bluetooth.GoToBluetoothGameListener;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.framework.eventbus.SimpleEventBus;
import ru.naumen.pentago.game.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

public class MainMenuActivity extends Activity
{

    private static boolean firstTime = true;

    private InitialAnimation initialAnimation;
    private GoToBluetoothGameListener btGameListener;
    private final EventBus eventBus = new SimpleEventBus();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialAnimation = new InitialAnimation(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        findViewById(R.id.textVSHuman).setOnClickListener(new GoToLocalGameListener(this, Constants.TWO_HUMAN_PLAYERS));
        findViewById(R.id.textVSComp).setOnClickListener(
                new GoToLocalGameListener(this, Constants.HUMAN_COMPUTER_PLAYERS));
        findViewById(R.id.textBluetooth).setOnClickListener(
                btGameListener = new GoToBluetoothGameListener(this, eventBus));
        findViewById(R.id.textHowTo).setOnClickListener(new GoToHowToListener(this));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && firstTime)
        {
            firstTime = false;
            initialAnimation.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.REQUEST_BT_ENABLED && resultCode == RESULT_OK)
        {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled())
            {
                new AlertDialog.Builder(this).setTitle(R.string.bluetoothConnection)
                        .setMessage(getResources().getString(R.string.bluetoothEnableError))
                        .setPositiveButton("OK", new HideDialogClickListener()).show();
                return;
            }
            btGameListener.showDialog();
        }
    }
}