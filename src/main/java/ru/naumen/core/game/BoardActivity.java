package ru.naumen.core.game;

import java.util.Set;

import ru.naumen.core.R;
import ru.naumen.core.game.controller.CornerController;
import ru.naumen.core.game.controller.GameController;
import ru.naumen.core.game.controller.GameController.GamePhase;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.controller.OnGameOverListener;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.SquareArea;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

import com.google.common.collect.Sets;

public class BoardActivity extends Activity
{
    private class RotateQuaterListener implements OnClickListener
    {
        private final SquareArea area;
        private final RotateDirection direction;

        public RotateQuaterListener(SquareArea area, RotateDirection direction)
        {
            this.area = area;
            this.direction = direction;
        }

        @Override
        public void onClick(View v)
        {
            if (GamePhase.RotateBoard.equals(gameController.getPhase()))
                gameController.rotateBoard(area, direction);
        }
    }

    private static int HALF = Constants.BOARD_SIZE / 2;

    private CornerController lefttop;
    private CornerController righttop;
    private CornerController leftbottom;
    private CornerController rightbottom;
    private GameController gameController;
    private final Set<CornerController> corners = Sets.newHashSet();

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

        lefttop = new CornerController(getApplicationContext(), 0, new SquareArea(0, 0, HALF), gameController);
        righttop = new CornerController(getApplicationContext(), -90, new SquareArea(HALF, 0, HALF), gameController);
        leftbottom = new CornerController(getApplicationContext(), 90, new SquareArea(0, HALF, HALF), gameController);
        rightbottom = new CornerController(getApplicationContext(), 180, new SquareArea(HALF, HALF, HALF),
                gameController);

        ((TableRow)findViewById(R.id.tableRow1)).addView(lefttop);
        ((TableRow)findViewById(R.id.tableRow1)).addView(righttop);
        ((TableRow)findViewById(R.id.tableRow2)).addView(leftbottom);
        ((TableRow)findViewById(R.id.tableRow2)).addView(rightbottom);

        corners.add(lefttop);
        corners.add(righttop);
        corners.add(leftbottom);
        corners.add(rightbottom);
    }
}