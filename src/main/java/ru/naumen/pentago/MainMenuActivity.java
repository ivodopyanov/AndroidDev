package ru.naumen.pentago;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.bluetooth.GoToBluetoothGameListener;
import ru.naumen.pentago.bluetooth.threads.BluetoothConstants;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.framework.eventbus.SimpleEventBus;
import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.GameActivity;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Player.PlayerType;
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
        else if (requestCode == BluetoothConstants.BLUETOOTH_ACTIVITY_RESULT && resultCode == RESULT_OK)
        {
            Boolean client = data.getExtras().getBoolean(BluetoothConstants.BLUETOOTH_CLIENT);
            String myName = data.getExtras().getString(BluetoothConstants.BLUETOOTH_MY_NAME);
            String remoteName = data.getExtras().getString(BluetoothConstants.BLUETOOTH_REMOTE_NAME);
            Player me = new Player(myName, "me", client ? R.drawable.blackball : R.drawable.whiteball,
                    PlayerType.humanBluetoothLocal);
            Player opponent = new Player(remoteName, "opp", client ? R.drawable.blackball : R.drawable.whiteball,
                    PlayerType.humanBluetoothRemote);
            List<Player> players = new ArrayList<Player>();
            if (client)
            {
                players.add(opponent);
                players.add(me);
            }
            else
            {
                players.add(me);
                players.add(opponent);
            }
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)players);
            startActivity(intent);
        }
    }
}