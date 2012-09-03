package ru.naumen.core.game.controller;

import ru.naumen.core.R;
import ru.naumen.core.game.BoardListAdapter;
import ru.naumen.core.game.Constants;
import ru.naumen.core.game.controller.GameController.GamePhase;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.SquareArea;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TableLayout;

public class CornerController extends TableLayout
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

    private GridView table;
    private ListAdapter adapter;
    private float rotationAngle = 0;
    private SquareArea area = new SquareArea(0, 0, Constants.BOARD_SIZE / 2);
    private GameController gameController;

    private View layout;

    public CornerController(Context context)
    {
        super(context);
        // initLayout(context);
    }

    public CornerController(Context context, AttributeSet attrSet)
    {
        super(context, attrSet);
        // initLayout(context);
    }

    public CornerController(Context context, float rotationAngle, SquareArea area, GameController gameController)
    {
        super(context);
        this.rotationAngle = rotationAngle;
        this.area = area;
        this.gameController = gameController;
        initLayout(context);
    }

    public GridView getTable()
    {
        return table;
    }

    public void setRotationAngle(float rotationAngle)
    {
        this.rotationAngle = rotationAngle;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(rotationAngle);
        super.onDraw(canvas);
        canvas.restore();
    }

    private void initLayout(Context context)
    {
        layout = LayoutInflater.from(context).inflate(R.layout.corner, null);
        layout.findViewById(R.id.imageView2).setOnClickListener(
                new RotateQuaterListener(area, RotateDirection.Clockwise));
        layout.findViewById(R.id.imageView1).setOnClickListener(
                new RotateQuaterListener(area, RotateDirection.CounterClockwise));
        adapter = new BoardListAdapter(context, gameController.getGame().getBoard(), gameController.getGame()
                .getPlayers(), getResources(), area);
        table = (GridView)layout.findViewById(R.id.gridView1);
        table.setAdapter(adapter);
        table.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if (GamePhase.PutBall.equals(gameController.getPhase()))
                    gameController.makeMove((Ball)adapter.getItem(arg2));
            }
        });

        gameController.addOnBoardStateChangedListener(new OnBoardStateChangedListener()
        {
            @Override
            public void onBoardStateChanged()
            {
                table.invalidateViews();
                if (GamePhase.PutBall.equals(gameController.getPhase()))
                    setArrowsVisibility(View.INVISIBLE);
                else
                    setArrowsVisibility(View.VISIBLE);
            }
        });
        setArrowsVisibility(View.INVISIBLE);
    }

    private void setArrowsVisibility(int visibility)
    {
        layout.findViewById(R.id.imageView1).setVisibility(visibility);
        layout.findViewById(R.id.imageView2).setVisibility(visibility);
    }
}