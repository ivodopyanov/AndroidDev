package ru.naumen.pentago.game;

import java.io.Serializable;
import java.util.List;

import ru.naumen.pentago.R;
import ru.naumen.pentago.bluetooth.BluetoothService;
import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.BoardController;
import ru.naumen.pentago.game.controller.GameController;
import ru.naumen.pentago.game.controller.events.GameOverEvent;
import ru.naumen.pentago.game.controller.events.GameOverHandler;
import ru.naumen.pentago.game.model.Game;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.PlayerController;
import ru.naumen.pentago.player.controller.PlayerControllerFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity implements GameOverHandler
{
    private final class GoToMainMenuListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            goBack();
        }

        private void goBack()
        {
            finish();
        }
    }

    private Game game;

    @Override
    public void onBackPressed()
    {
        BluetoothService.get().stop();
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("gameActivity", "activity started");
        setContentView(R.layout.gameboard);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        game = initGame(savedInstanceState);
        EventBus.INSTANCE.register(GameOverEvent.class, this);

        GameController gameController = new GameController(game, game.getPlayers());
        List<PlayerController> playerControllers = Collections.transform(game.getPlayers(),
                new PlayerControllerFactory(game.getBoard(), game.getPlayers()));
        BoardController boardController = (BoardController)findViewById(R.id.board);
        boardController.init(game);
        initPlayersInfo();
        gameController.performNextStep();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.layout.boardmenu, menu);
        return true;
    }

    @Override
    public void onGameOver(GameOverEvent event)
    {
        BluetoothService.get().stop();
        if (event.getWinner() == Constants.GAME_OVER_BLUETOOTH_DISCONNECTED)
        {
            new AlertDialog.Builder(GameActivity.this).setTitle(R.string.endOfGame)
                    .setMessage(getResources().getString(R.string.endOfGameBluetooth))
                    .setPositiveButton("OK", new GoToMainMenuListener()).show();
        }
        else
        {
            new AlertDialog.Builder(GameActivity.this)
                    .setTitle(R.string.endOfGame)
                    .setMessage(
                            getResources().getString(R.string.winner) + ": "
                                    + game.getPlayers().get(event.getWinner()).getTitle())
                    .setPositiveButton("OK", new GoToMainMenuListener()).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_newgame)
        {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)game.getPlayers());
            finish();
            startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.BOARD_EXTRA, game);
    }

    private Game initGame(Bundle savedInstanceState)
    {
        if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(Constants.BOARD_EXTRA))
        {
            return (Game)getIntent().getExtras().get(Constants.BOARD_EXTRA);
        }
        else if (savedInstanceState != null && savedInstanceState.containsKey(Constants.BOARD_EXTRA))
        {
            return (Game)savedInstanceState.get(Constants.BOARD_EXTRA);
        }
        else
        {
            List<Player> players = (List<Player>)getIntent().getExtras().get(Constants.PLAYERS_EXTRA);
            return new Game(players);
        }
    }

    private void initPlayersInfo()
    {
        PlayerInfo playerInfo = new PlayerInfo(getApplicationContext(), findViewById(R.id.player1));
        playerInfo.init(game.getPlayers().get(0));
        playerInfo = new PlayerInfo(getApplicationContext(), findViewById(R.id.player2));
        playerInfo.init(game.getPlayers().get(1));
    }
}