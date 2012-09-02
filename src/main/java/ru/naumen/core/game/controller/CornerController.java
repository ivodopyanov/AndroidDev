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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;

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

    private TableRow topRow;
    private TableRow bottomRow;
    private ImageView rotateClockwise;
    private ImageView rotateCounterClockwise;
    private GridView table;
    private ListAdapter adapter;
    private final LayoutParams paramsFW = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    private final LayoutParams paramsFF = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    private final LayoutParams paramsWW = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    private float rotationAngle = 0;
    private SquareArea area = new SquareArea(0, 0, Constants.BOARD_SIZE / 2);
    private GameController gameController;

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

    public ImageView getRotateClockwiseImage()
    {
        return rotateClockwise;
    }

    public ImageView getRotateCounterClockwiseIamge()
    {
        return rotateCounterClockwise;
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

    private ListAdapter initAdapter(Context context)
    {
        return new BoardListAdapter(context, gameController.getGame().getBoard(),
                gameController.getGame().getPlayers(), getResources(), area);
    }

    private TableRow initBottomRow(Context context)
    {
        TableRow result = new TableRow(context);
        result.setLayoutParams(paramsFW);
        result.addView(rotateCounterClockwise);
        result.addView(table);
        return result;
    }

    private GridView initGridView(Context context)
    {
        GridView result = new GridView(context);
        result.setAdapter(adapter);
        result.setNumColumns(area.getLength());
        result.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if (GamePhase.PutBall.equals(gameController.getPhase()))
                    gameController.makeMove((Ball)adapter.getItem(arg2));
            }
        });
        return result;
    }

    private ImageView initImage(Context context, int imageId, RotateDirection rotateDirection)
    {
        ImageView result = new ImageView(context);
        result.setLayoutParams(paramsFF);
        result.setImageDrawable(getResources().getDrawable(imageId));
        result.setScaleType(ScaleType.CENTER_CROP);
        result.setOnClickListener(new RotateQuaterListener(area, rotateDirection));
        return result;
    }

    private void initLayout(Context context)
    {
        rotateClockwise = initImage(context, R.drawable.ic_arrowright, RotateDirection.Clockwise);
        rotateCounterClockwise = initImage(context, R.drawable.ic_arrowdown, RotateDirection.CounterClockwise);
        adapter = initAdapter(context);
        table = initGridView(context);
        topRow = initTopRow(context);
        bottomRow = initBottomRow(context);
        addView(topRow, paramsFW);
        addView(bottomRow, paramsFW);
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

    private TableRow initTopRow(Context context)
    {
        TableRow result = new TableRow(context);
        result.setLayoutParams(paramsFW);
        result.addView(rotateClockwise);
        return result;
    }

    private void setArrowsVisibility(int visibility)
    {
        rotateClockwise.setVisibility(visibility);
        rotateCounterClockwise.setVisibility(visibility);
    }
}