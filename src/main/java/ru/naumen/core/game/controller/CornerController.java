package ru.naumen.core.game.controller;

import ru.naumen.core.R;
import ru.naumen.core.framework.eventbus.EventBus;
import ru.naumen.core.game.BoardListAdapter;
import ru.naumen.core.game.controller.GameController.GamePhase;
import ru.naumen.core.game.controller.GameController.RotateDirection;
import ru.naumen.core.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveHandler;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateHandler;
import ru.naumen.core.game.controller.events.RotateCornerEvent;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.Quarter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class CornerController extends LinearLayout implements RequestBoardRotateHandler, RequestBallMoveHandler
{
    private class BallInsertionListener implements OnItemClickListener
    {
        private final EventBus eventBus;
        private final ListAdapter adapter;

        public BallInsertionListener(EventBus eventBus, ListAdapter adapter)
        {
            this.eventBus = eventBus;
            this.adapter = adapter;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            eventBus.fireEvent(new InsertBallInCornerEvent((Ball)adapter.getItem(position)));
        }
    }

    private class RotateQuaterListener implements OnClickListener
    {
        private final Quarter area;
        private final RotateDirection direction;
        private final EventBus eventBus;

        public RotateQuaterListener(Quarter area, RotateDirection direction, EventBus eventBus)
        {
            this.area = area;
            this.direction = direction;
            this.eventBus = eventBus;
        }

        @Override
        public void onClick(View v)
        {
            if (GamePhase.RotateBoard.equals(gamePhase))
                eventBus.fireEvent(new RotateCornerEvent(area, direction));
        }
    }

    private GridView table;
    private CornerViewDescription desc;
    private final View layout;
    private GamePhase gamePhase = GamePhase.PutBall;

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

    public void init(CornerViewDescription desc, Game game, EventBus eventBus)
    {
        this.desc = desc;
        eventBus.register(RequestBoardRotateEvent.class, this);
        eventBus.register(RequestBallMoveEvent.class, this);

        for (RotateImageDescription imageDesc : desc.getImages())
        {
            CustomView imageView = imageDesc.getImage();
            imageView.setContentDescription(getResources().getString(imageDesc.getDescId()));
            boolean flip = RotateDirection.CounterClockwise == imageDesc.getDir();
            imageView.setFlip(flip);
            imageView.setOnClickListener(new RotateQuaterListener(desc.getArea(), imageDesc.getDir(), eventBus));
        }
        ListAdapter adapter = new BoardListAdapter(getContext(), game.getBoard(), game.getPlayers(), getResources(),
                desc.getArea());
        table = (GridView)layout.findViewById(R.id.gridView1);
        table.setAdapter(adapter);
        table.setOnItemClickListener(new BallInsertionListener(eventBus, adapter));
        setArrowsVisibility(View.INVISIBLE);
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        table.invalidateViews();
        setArrowsVisibility(View.INVISIBLE);
        gamePhase = GamePhase.PutBall;
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        table.invalidateViews();
        setArrowsVisibility(View.VISIBLE);
        gamePhase = GamePhase.RotateBoard;
    }

    private void setArrowsVisibility(int visibility)
    {
        for (RotateImageDescription imageDesc : desc.getImages())
        {
            imageDesc.getImage().setVisibility(visibility);
        }
    }
}