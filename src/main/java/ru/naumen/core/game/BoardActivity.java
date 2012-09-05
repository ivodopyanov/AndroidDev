package ru.naumen.core.game;

import java.util.Set;

import ru.naumen.core.R;
import ru.naumen.core.game.controller.CornerController;
import ru.naumen.core.game.controller.CornerViewDescription;
import ru.naumen.core.game.controller.GameController;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.controller.OnGameOverListener;
import ru.naumen.core.game.controller.RotateImageDescription;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.SquareArea;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.google.common.collect.Sets;

public class BoardActivity extends Activity
{
    private static final int HALF = Constants.BOARD_SIZE / 2;
    private static final float[] ANGLES = new float[] { 0.0f, -90.0f, 90.0f, 180.0f };
    private GameController gameController;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        initBoard(savedInstanceState);
        initGrid();
    }

    private void initBoard(Bundle savedInstanceState)
    {
        gameController = new GameController(initGame(savedInstanceState));
        gameController.addOnGameOverListener(new OnGameOverListener()
        {
            @Override
            public void onGameOver(int winner)
            {
                new AlertDialog.Builder(BoardActivity.this).setTitle(R.string.endOfGame)
                        .setMessage(gameController.getGame().getPlayers().get(winner).getTitle()).show();
            }
        });
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
                    top ? R.id.imageViewTop : R.id.imageViewBottom, 
                    left ? R.drawable.ic_arrowleft: R.drawable.ic_arrowright, 
                    top ^ left ? R.string.rotateCounterClockwise : R.string.rotateClockwise,
                    top ^ left ? RotateDirection.CounterClockwise : RotateDirection.Clockwise));
            
            images.add(new RotateImageDescription(
                    left ? R.id.imageViewLeft : R.id.imageViewRight, 
                    top ? R.drawable.ic_arrowdown : R.drawable.ic_arrowup, 
                    top ^ left ? R.string.rotateCounterClockwise : R.string.rotateClockwise,
                    top ^ left ? RotateDirection.CounterClockwise : RotateDirection.Clockwise));
            //@formatter:on
            SquareArea area = new SquareArea(left ? 0 : HALF, top ? 0 : HALF, HALF);
            LinearLayout cornerView = new CornerController(getApplicationContext(), new CornerViewDescription(area,
                    gameController, images, ANGLES[i]));
            cornerView.setGravity((top ? Gravity.BOTTOM : Gravity.TOP) | (left ? Gravity.RIGHT : Gravity.LEFT));
            cornerView.setPadding(left ? 0 : 5, top ? 0 : 5, left ? 5 : 0, top ? 5 : 0);
            ((LinearLayout)findViewById(top ? R.id.toprow : R.id.bottomrow)).addView(cornerView);
        }
    }
}