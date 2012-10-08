package ru.naumen.pentago.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.naumen.pentago.MainMenuActivity;
import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.framework.eventbus.SimpleEventBus;
import ru.naumen.pentago.game.controller.CornerController;
import ru.naumen.pentago.game.controller.CornerViewDescription;
import ru.naumen.pentago.game.controller.CustomView;
import ru.naumen.pentago.game.controller.GameController;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.controller.RotateImageDescription;
import ru.naumen.pentago.game.controller.events.GameOverEvent;
import ru.naumen.pentago.game.controller.events.GameOverHandler;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.model.Game;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.player.controller.PlayerController;
import ru.naumen.pentago.player.controller.PlayerControllerFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

public class BoardActivity extends Activity implements GameOverHandler
{
    private final class GoToMainMenuListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent);
        }
    }

    private static final float[] ANGLES = new float[] { 0.0f, 90.0f, -90.0f, 180.0f };
    //Список пар изображений (повернуть по часовой, повернуть против часовой), разбитый по квадрантам
    private static final int[][] IMAGES = new int[][] { new int[] { R.id.ltc, R.id.ltcc },
            new int[] { R.id.rtc, R.id.rtcc }, new int[] { R.id.lbc, R.id.lbcc }, new int[] { R.id.rbc, R.id.rbcc } };
    private static final int[] CORNERS = new int[] { R.id.topleft, R.id.topright, R.id.bottomleft, R.id.bottomright };
    private Game game;
    private List<Player> players;
    private final EventBus eventBus = new SimpleEventBus();
    private BallViewFactory viewFactory;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        eventBus.register(GameOverEvent.class, this);
        setContentView(R.layout.gameboard);
        initBoard(savedInstanceState);
        viewFactory = new BallViewFactory(getApplicationContext(), getResources(), players);
        initGrid();
        eventBus.fireEvent(new RequestBallMoveEvent(players.get(0).getCode()));
    }

    @Override
    public void onGameOver(GameOverEvent event)
    {
        new AlertDialog.Builder(BoardActivity.this).setTitle(R.string.endOfGame)
                .setMessage(players.get(event.getWinner()).getTitle())
                .setPositiveButton("OK", new GoToMainMenuListener()).show();
    }

    private void initBoard(Bundle savedInstanceState)
    {
        players = (List<Player>)getIntent().getExtras().get(Constants.PLAYERS_EXTRA);
        game = initGame(savedInstanceState);
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
            return new Game();
        }
    }

    private void initGrid()
    {
        for (int i = 0; i < 4; i++)
        {
            boolean top = i < 2;
            boolean left = i % 2 == 0;
            Set<RotateImageDescription> images = new HashSet<RotateImageDescription>();
            //@formatter:off
            images.add(new RotateImageDescription(
                    (CustomView)findViewById(IMAGES[i][0]), 
                    R.string.rotateClockwise,
                    RotateDirection.Clockwise));
            
            images.add(new RotateImageDescription(
                    (CustomView)findViewById(IMAGES[i][1]), 
                    R.string.rotateCounterClockwise,
                    RotateDirection.CounterClockwise));
            //@formatter:on
            Quarter area = Quarter.create(left, top);

            CornerController cornerView = (CornerController)findViewById(CORNERS[i]);
            cornerView.init(new CornerViewDescription(area, images, ANGLES[i]), game, eventBus, viewFactory);
            cornerView.setGravity((top ? Gravity.BOTTOM : Gravity.TOP) | (left ? Gravity.RIGHT : Gravity.LEFT));
            cornerView.setPadding(left ? 0 : 5, top ? 0 : 5, left ? 5 : 0, top ? 5 : 0);
        }
    }
}