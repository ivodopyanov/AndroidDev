package ru.naumen.pentago.game;

import java.io.Serializable;
import java.util.List;

import ru.naumen.pentago.MainMenuActivity;
import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.framework.eventbus.SimpleEventBus;
import ru.naumen.pentago.game.controller.BoardController;
import ru.naumen.pentago.game.controller.GameController;
import ru.naumen.pentago.game.controller.events.GameOverEvent;
import ru.naumen.pentago.game.controller.events.GameOverHandler;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
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
import android.view.View;
import android.view.View.OnClickListener;

public class GameActivity extends Activity implements GameOverHandler
{
    private final class GoToMainMenuListener implements DialogInterface.OnClickListener, OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            goBack();
        }

        @Override
        public void onClick(View v)
        {
            goBack();
        }

        private void goBack()
        {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent);
        }
    }

    private class ResetClickListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)players);
            finish();
            startActivity(intent);
        }
    }

    private Game game;
    private List<Player> players;
    private final EventBus eventBus = new SimpleEventBus();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("gameActivity", "activity started");
        setContentView(R.layout.gameboard);
        players = (List<Player>)getIntent().getExtras().get(Constants.PLAYERS_EXTRA);
        game = initGame(savedInstanceState);
        eventBus.register(GameOverEvent.class, this);
        findViewById(R.id.goback).setOnClickListener(new GoToMainMenuListener());
        findViewById(R.id.newgame).setOnClickListener(new ResetClickListener());

        initBoard();
        BoardController boardController = new BoardController(getApplicationContext(), findViewById(R.id.board));
        boardController.init(game, players, eventBus);
        initPlayersInfo();
        eventBus.fireEvent(new RequestBallMoveEvent(players.get(0).getCode()));
    }

    @Override
    public void onGameOver(GameOverEvent event)
    {
        new AlertDialog.Builder(GameActivity.this).setTitle(R.string.endOfGame)
                .setMessage(players.get(event.getWinner()).getTitle())
                .setPositiveButton("OK", new GoToMainMenuListener()).show();
    }

    private void initBoard()
    {
        GameController gameController = new GameController(game, players, eventBus);
        List<PlayerController> playerControllers = Collections.transform(players, new PlayerControllerFactory(eventBus,
                game.getBoard(), players));
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
            return new Game(players);
        }
    }

    private void initPlayersInfo()
    {
        PlayerInfo playerInfo = new PlayerInfo(getApplicationContext(), findViewById(R.id.player1));
        playerInfo.init(players.get(0), eventBus);
        playerInfo = new PlayerInfo(getApplicationContext(), findViewById(R.id.player2));
        playerInfo.init(players.get(1), eventBus);
    }
}