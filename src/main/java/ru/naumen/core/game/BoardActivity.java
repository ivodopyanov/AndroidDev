package ru.naumen.core.game;

import java.util.Set;

import ru.naumen.core.R;
import ru.naumen.core.game.controller.GameController;
import ru.naumen.core.game.controller.GameController.GamePhase;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.controller.OnBoardStateChangedListener;
import ru.naumen.core.game.controller.OnGameOverListener;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.SquareArea;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;

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

    public static final String BOARD_EXTRA = "board";
    public static final int BOARD_SIZE = 6;
    private GameController gameController;
    private ListAdapter adapter;

    private Set<View> arrows;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        initBoard(savedInstanceState);
        initArrows();
        initGrid();
    }

    private void initArrows()
    {
        arrows = Sets.newHashSet();
        arrows.add(findViewById(R.id.imageViewLeftTopDirect));
        arrows.add(findViewById(R.id.imageViewLeftTopCounter));
        arrows.add(findViewById(R.id.imageViewRightTopDirect));
        arrows.add(findViewById(R.id.imageViewRightTopCounter));
        arrows.add(findViewById(R.id.imageViewLeftBottomDirect));
        arrows.add(findViewById(R.id.imageViewLeftBottomCounter));
        arrows.add(findViewById(R.id.imageViewRightBottomDirect));
        arrows.add(findViewById(R.id.imageViewRightBottomCounter));

        SquareArea topleft = new SquareArea(0, 0, BOARD_SIZE / 2);
        SquareArea topright = new SquareArea(BOARD_SIZE / 2, 0, BOARD_SIZE / 2);
        SquareArea bottomleft = new SquareArea(0, BOARD_SIZE / 2, BOARD_SIZE / 2);
        SquareArea bottomright = new SquareArea(BOARD_SIZE / 2, BOARD_SIZE / 2, BOARD_SIZE / 2);

        findViewById(R.id.imageViewLeftTopCounter).setOnClickListener(
                new RotateQuaterListener(topleft, RotateDirection.CounterClockwise));
        findViewById(R.id.imageViewLeftTopDirect).setOnClickListener(
                new RotateQuaterListener(topleft, RotateDirection.Clockwise));
        findViewById(R.id.imageViewRightTopCounter).setOnClickListener(
                new RotateQuaterListener(topright, RotateDirection.CounterClockwise));
        findViewById(R.id.imageViewRightTopDirect).setOnClickListener(
                new RotateQuaterListener(topright, RotateDirection.Clockwise));
        findViewById(R.id.imageViewLeftBottomDirect).setOnClickListener(
                new RotateQuaterListener(bottomleft, RotateDirection.Clockwise));
        findViewById(R.id.imageViewLeftBottomCounter).setOnClickListener(
                new RotateQuaterListener(bottomleft, RotateDirection.CounterClockwise));
        findViewById(R.id.imageViewRightBottomDirect).setOnClickListener(
                new RotateQuaterListener(bottomright, RotateDirection.Clockwise));
        findViewById(R.id.imageViewRightBottomCounter).setOnClickListener(
                new RotateQuaterListener(bottomright, RotateDirection.CounterClockwise));

        setArrowsVisibility(View.INVISIBLE);
    }

    private void initBoard(Bundle savedInstanceState)
    {
        gameController = new GameController(initGame(savedInstanceState));
        gameController.setOnBoardStateChangedListener(new OnBoardStateChangedListener()
        {
            @Override
            public void onBoardStateChanged()
            {

                ((GridView)findViewById(R.id.gridView1)).invalidateViews();
                if (GamePhase.PutBall.equals(gameController.getPhase()))
                    setArrowsVisibility(View.INVISIBLE);
                else
                    setArrowsVisibility(View.VISIBLE);
            }
        });
        gameController.setOnGameOverListener(new OnGameOverListener()
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
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(BOARD_EXTRA))
        {
            return (Game)getIntent().getExtras().get(BOARD_EXTRA);
        }
        else if (savedInstanceState != null && savedInstanceState.containsKey(BOARD_EXTRA))
        {
            return (Game)savedInstanceState.get(BOARD_EXTRA);
        }
        else
        {
            return new Game();
        }
    }

    private void initGrid()
    {
        adapter = new BoardListAdapter(this.getApplicationContext(), gameController.getGame().getBoard(),
                gameController.getGame().getPlayers(), getResources());
        GridView grid = (GridView)findViewById(R.id.gridView1);
        grid.setNumColumns(BOARD_SIZE);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (GamePhase.PutBall.equals(gameController.getPhase()))
                    gameController.makeMove(position);
            }
        });
    }

    private void setArrowsVisibility(int visibility)
    {
        for (View arrow : arrows)
        {
            arrow.setVisibility(visibility);
        }
    }
}