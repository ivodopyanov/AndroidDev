package ru.naumen.core.game.controller;

import ru.naumen.core.R;
import ru.naumen.core.game.BoardListAdapter;
import ru.naumen.core.game.controller.GameController.GamePhase;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.SquareArea;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class CornerController extends LinearLayout
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
            if (GamePhase.RotateBoard.equals(desc.getController().getPhase()))
                desc.getController().rotateBoard(area, direction);
        }
    }

    private GridView table;
    private ListAdapter adapter;
    private CornerViewDescription desc;
    private final View layout;

    public CornerController(Context context)
    {
        super(context);
        layout = LayoutInflater.from(context).inflate(R.layout.corner, this, true);
    }

    public CornerController(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        layout = LayoutInflater.from(context).inflate(R.layout.corner, this, true);
    }

    public GridView getTable()
    {
        return table;
    }

    public void init(final CornerViewDescription desc)
    {
        this.desc = desc;

        for (RotateImageDescription imageDesc : desc.getImages())
        {
            CustomView imageView = imageDesc.getImage();
            imageView.setContentDescription(getResources().getString(imageDesc.getDescId()));
            boolean flip = RotateDirection.CounterClockwise == imageDesc.getDir();
            imageView.setFlip(flip);
            imageView.setOnClickListener(new RotateQuaterListener(desc.getArea(), imageDesc.getDir()));
        }
        adapter = new BoardListAdapter(getContext(), desc.getController().getGame().getBoard(), desc.getController()
                .getGame().getPlayers(), getResources(), desc.getArea());
        table = (GridView)layout.findViewById(R.id.gridView1);
        table.setAdapter(adapter);
        table.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if (GamePhase.PutBall.equals(desc.getController().getPhase()))
                    desc.getController().makeMove((Ball)adapter.getItem(arg2));
            }
        });

        desc.getController().addOnBoardStateChangedListener(new OnBoardStateChangedListener()
        {
            @Override
            public void onBoardStateChanged()
            {
                table.invalidateViews();
                if (GamePhase.PutBall.equals(desc.getController().getPhase()))
                    setArrowsVisibility(View.INVISIBLE);
                else
                    setArrowsVisibility(View.VISIBLE);
            }
        });
        setArrowsVisibility(View.INVISIBLE);
    }

    private void setArrowsVisibility(int visibility)
    {
        for (RotateImageDescription imageDesc : desc.getImages())
        {
            imageDesc.getImage().setVisibility(visibility);
        }
    }
}