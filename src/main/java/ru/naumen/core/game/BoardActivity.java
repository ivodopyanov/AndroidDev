package ru.naumen.core.game;

import java.util.List;
import java.util.Set;

import ru.naumen.core.R;
import ru.naumen.core.game.controller.CornerController;
import ru.naumen.core.game.controller.CornerViewDescription;
import ru.naumen.core.game.controller.CustomView;
import ru.naumen.core.game.controller.GameController;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.controller.RotateImageDescription;
import ru.naumen.core.game.controller.events.GameOverEvent;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.SquareArea;
import ru.naumen.core.player.controller.PlayerController;
import ru.naumen.core.player.controller.PlayerControllerFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class BoardActivity extends Activity
{

    private static final int HALF = Constants.BOARD_SIZE / 2;
    private static final float[] ANGLES = new float[] { 0.0f, 90.0f, -90.0f, 180.0f };
    //Список пар изображений (повернуть по часовой, повернуть против часовой), разбитый по квадрантам
    private static final int[][] IMAGES = new int[][] { new int[] { R.id.ltc, R.id.ltcc },
            new int[] { R.id.rtc, R.id.rtcc }, new int[] { R.id.lbc, R.id.lbcc }, new int[] { R.id.rbc, R.id.rbcc } };
    private static final int[] CORNERS = new int[] { R.id.topleft, R.id.topright, R.id.bottomleft, R.id.bottomright };
    private GameController gameController;
    private List<PlayerController> playerControllers;
    private final EventBus eventBus = new EventBus();

    @Subscribe
    public void gameOver(GameOverEvent event)
    {
        new AlertDialog.Builder(BoardActivity.this).setTitle(R.string.endOfGame)
                .setMessage(gameController.getGame().getPlayers().get(event.getWinner()).getTitle()).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        setContentView(R.layout.gameboard);
        initBoard(savedInstanceState);
        initGrid();
    }

    private void initBoard(Bundle savedInstanceState)
    {
        List<Player> players = (List<Player>)getIntent().getExtras().get(Constants.PLAYERS_EXTRA);
        Game game = initGame(savedInstanceState);
        gameController = new GameController(game, players, eventBus);
        playerControllers = Lists.transform(players, new PlayerControllerFactory(eventBus, game.getBoard()));
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
            Set<RotateImageDescription> images = Sets.newHashSet();
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
            SquareArea area = new SquareArea(left ? 0 : HALF, top ? 0 : HALF, HALF);

            CornerController cornerView = (CornerController)findViewById(CORNERS[i]);
            cornerView.init(new CornerViewDescription(area, images, ANGLES[i]), gameController.getGame(), eventBus);
            cornerView.setGravity((top ? Gravity.BOTTOM : Gravity.TOP) | (left ? Gravity.RIGHT : Gravity.LEFT));
            cornerView.setPadding(left ? 0 : 5, top ? 0 : 5, left ? 5 : 0, top ? 5 : 0);
        }
    }
}