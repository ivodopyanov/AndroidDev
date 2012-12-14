package ru.naumen.pentago;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.bluetooth.GoToBluetoothGameListener;
import ru.naumen.pentago.bluetooth.threads.BluetoothConstants;
import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.GameActivity;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Player.PlayerType;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;

public class MainMenuActivity extends Activity
{
    private class MyLicenseCheckerCallback implements LicenseCheckerCallback
    {
        @Override
        public void allow(int reason)
        {
            if (isFinishing())
            {
                // Don't update UI if Activity is finishing.
                return;
            }
            startInitialAnimation();
        }

        @Override
        public void applicationError(int errorCode)
        {
        }

        @Override
        public void dontAllow(int reason)
        {
            if (isFinishing())
            {
                return;
            }

            if (reason == Policy.RETRY)
            {
                // If the reason received from the policy is RETRY, it was probably
                // due to a loss of connection with the service, so we should give the
                // user a chance to retry. So show a dialog to retry.
                new AlertDialog.Builder(MainMenuActivity.this).setTitle(R.string.license)
                        .setMessage(getResources().getString(R.string.licenseRetry))
                        .setPositiveButton("OK", new OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                doCheck();
                            }
                        }).show();
            }
            else
            {
                // Otherwise, the user is not licensed to use this app.
                // Your response should always inform the user that the application
                // is not licensed, but your behavior at that point can vary. You might
                // provide the user a limited access version of your app or you can
                // take them to Google Play to purchase the app.
                new AlertDialog.Builder(MainMenuActivity.this).setTitle(R.string.license)
                        .setMessage(getResources().getString(R.string.dont_allow))
                        .setPositiveButton("OK", new HideDialogClickListener()).show();

            }
        }
    }

    private static final String TAG = "MainMenu";
    private static boolean firstTime = true;

    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjaOEkQa1PWZieUEgxEbd4EhQySuPv8AwEdIKE4YIS6KxoByl1Wz7Rq/nGsGFBefA9oBn+udQc/Clu0R7Fgkr4O+0BsYJwOZOXdFuRx/vxmsBJnZ9zafxHP9WtVGT7yt8lKdrFu3noVPsQxznLD3tfY6P9lJ+3dKfvQWe5xDRXrkL6NGaznEIsdbcKL1KXBc+EBprPukyy+QrWJmnqGOISQNu5GIfzB8MhemHBwNbVBu1u9EMjk/JauqxMw4LowlOYgUft9sdNxyHg3pePvpP6O95Dl1CJ+720pkwNUUeNfKkqX9F1YfmoQl61wqjDK89LFgL8Du+Ere6tXSz+/yMowIDAQAB";

    private InitialAnimation initialAnimation;
    private GoToBluetoothGameListener btGameListener;
    private LicenseCheckerCallback callback;
    private LicenseChecker checker;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*
         * callback = new MyLicenseCheckerCallback(); checker = new
         * LicenseChecker(this, new ServerManagedPolicy(this, new
         * AESObfuscator("SALT".getBytes(), getPackageName(),
         * Secure.getString(getContentResolver(), Secure.ANDROID_ID))),
         * BASE64_PUBLIC_KEY // Your public licensing key. );
         */
        doCheck();

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
        if (requestCode == Constants.REQUEST_BT_DISCOVERABLE && resultCode != RESULT_CANCELED)
        {
            Log.d(TAG, "Set discoverable");
            btGameListener.showDialog();
        }
        else if (requestCode == Constants.REQUEST_BT_ENABLED && resultCode == RESULT_OK)
        {
            Log.d(TAG, "set enabled");
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled())
            {
                new AlertDialog.Builder(this).setTitle(R.string.bluetoothConnection)
                        .setMessage(getResources().getString(R.string.bluetoothEnableError))
                        .setPositiveButton("OK", new HideDialogClickListener()).show();
                return;
            }
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent, Constants.REQUEST_BT_DISCOVERABLE);
        }
        else if (requestCode == BluetoothConstants.BLUETOOTH_ACTIVITY_RESULT && resultCode == RESULT_OK)
        {
            Log.d(TAG, "connected");
            Boolean client = data.getExtras().getBoolean(BluetoothConstants.BLUETOOTH_CLIENT);
            String myName = data.getExtras().getString(BluetoothConstants.BLUETOOTH_MY_NAME);
            String remoteName = data.getExtras().getString(BluetoothConstants.BLUETOOTH_REMOTE_NAME);
            Player me = new Player(myName, "me", client ? R.drawable.blackball : R.drawable.whiteball,
                    PlayerType.humanBluetoothLocal);
            Player opponent = new Player(remoteName, "opp", client ? R.drawable.whiteball : R.drawable.blackball,
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //checker.onDestroy();
    }

    private void doCheck()
    {
        startInitialAnimation();
        //checker.checkAccess(callback);
    }

    private void startInitialAnimation()
    {
        initialAnimation = new InitialAnimation(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        findViewById(R.id.textVSHuman).setOnClickListener(new GoToLocalGameListener(this, Constants.TWO_HUMAN_PLAYERS));
        findViewById(R.id.textVSComp).setOnClickListener(
                new GoToLocalGameListener(this, Constants.HUMAN_COMPUTER_PLAYERS));
        findViewById(R.id.textBluetooth).setOnClickListener(btGameListener = new GoToBluetoothGameListener(this));
        findViewById(R.id.textHowTo).setOnClickListener(new GoToHowToListener(this));
    }
}