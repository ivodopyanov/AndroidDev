package ru.naumen.core.game;

import ru.naumen.core.R;
import ru.naumen.core.game.model.Game;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class BoardActivity extends Activity
{
    public static final String BOARD_EXTRA = "board";
    public static final int COL_NUM = 9;
    public static final int ROW_NUM = 9;

    private Game game;

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
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(BOARD_EXTRA))
        {
            game = (Game)getIntent().getExtras().get(BOARD_EXTRA);
        }
        else if (savedInstanceState != null && savedInstanceState.containsKey(BOARD_EXTRA))
        {
            game = (Game)savedInstanceState.get(BOARD_EXTRA);
        }
        else
        {
            game = new Game();
        }
    }

    private void initGrid()
    {
        GridView grid = (GridView)findViewById(R.id.gridView1);
        grid.setNumColumns(COL_NUM);
        grid.setAdapter(new BoardListAdapter(this.getApplicationContext(), game.getBoard(), game.getPlayers(),
                getResources()));
    }
}