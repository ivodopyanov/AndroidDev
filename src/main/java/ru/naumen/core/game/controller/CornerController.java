package ru.naumen.core.game.controller;

import ru.naumen.core.R;
import ru.naumen.core.game.BoardListAdapter;
import ru.naumen.core.game.controller.GameController.GamePhase;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.SquareArea;
import android.content.Context;
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

    private final CornerViewDescription desc;

    private View layout;

    public CornerController(Context context, CornerViewDescription desc)
    {
        super(context);
        this.desc = desc;
        initLayout(context);
    }

    public GridView getTable()
    {
        return table;
    }

    private void initLayout(Context context)
    {
        layout = LayoutInflater.from(context).inflate(R.layout.corner, this, true);
        layout.findViewById(R.id.imageViewTop).setVisibility(View.GONE);
        layout.findViewById(R.id.imageViewLeft).setVisibility(View.GONE);
        layout.findViewById(R.id.imageViewRight).setVisibility(View.GONE);
        layout.findViewById(R.id.imageViewBottom).setVisibility(View.GONE);
        layout.findViewById(R.id.tableRow1).setVisibility(View.GONE);
        layout.findViewById(R.id.tableRow3).setVisibility(View.GONE);

        for (RotateImageDescription imageDesc : desc.getImages())
        {
            CustomView imageView = (CustomView)layout.findViewById(imageDesc.getImageId());
            imageView.setContentDescription(getResources().getString(imageDesc.getDescId()));
            imageView.setAngle(imageDesc.getAngle());
            imageView.setFlip(imageDesc.isFlip());
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rotate));
            layout.findViewById(imageDesc.getImageId()).setOnClickListener(
                    new RotateQuaterListener(desc.getArea(), imageDesc.getDir()));
        }
        adapter = new BoardListAdapter(context, desc.getController().getGame().getBoard(), desc.getController()
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
            int id = imageDesc.getImageId();
            layout.findViewById(id).setVisibility(visibility);
            if (id == R.id.imageViewTop)
                layout.findViewById(R.id.tableRow1).setVisibility(visibility);
            if (id == R.id.imageViewBottom)
                layout.findViewById(R.id.tableRow3).setVisibility(visibility);
        }
    }
}