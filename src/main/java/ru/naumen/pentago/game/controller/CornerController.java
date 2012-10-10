package ru.naumen.pentago.game.controller;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.BallViewFactory;
import ru.naumen.pentago.game.BoardListAdapter;
import ru.naumen.pentago.game.controller.GameController.GamePhase;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.controller.events.FinishedBallAnimationEvent;
import ru.naumen.pentago.game.controller.events.FinishedRotateAnimationEvent;
import ru.naumen.pentago.game.controller.events.FinishedRotateAnimationHandler;
import ru.naumen.pentago.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.controller.events.MoveBallHandler;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateHandler;
import ru.naumen.pentago.game.controller.events.RotateBoardEvent;
import ru.naumen.pentago.game.controller.events.RotateBoardHandler;
import ru.naumen.pentago.game.controller.events.RotateCornerEvent;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Game;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class CornerController extends LinearLayout implements RequestBoardRotateHandler, MoveBallHandler,
        RotateBoardHandler, FinishedRotateAnimationHandler
{
    private static class BallInsertionListener implements OnItemClickListener
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

    private static class BallMoveListener implements AnimationListener
    {
        private final Ball ball;
        private final Player player;
        private final EventBus eventBus;
        private final ImageView ballView;

        public BallMoveListener(Ball ball, Player player, EventBus eventBus, ImageView ballView)
        {
            this.ball = ball;
            this.player = player;
            this.eventBus = eventBus;
            this.ballView = ballView;
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            ballView.setVisibility(INVISIBLE);
            eventBus.fireEvent(new FinishedBallAnimationEvent(ball));
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {
        }

        @Override
        public void onAnimationStart(Animation animation)
        {
        }

    }

    private class RotateAnimationListener implements AnimationListener
    {
        private final RotateInfo rotateInfo;
        private boolean flag = false;

        public RotateAnimationListener(RotateInfo rotateInfo)
        {
            this.rotateInfo = rotateInfo;
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            clearAnimation();
            if (!flag)
                flag = true;
            else
            {
                eventBus.fireEvent(new FinishedRotateAnimationEvent(rotateInfo));
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {
        }

        @Override
        public void onAnimationStart(Animation animation)
        {
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
    private EventBus eventBus;
    private BoardListAdapter adapter;

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

    public void init(CornerViewDescription desc, Game game, EventBus eventBus, BallViewFactory viewFactory)
    {
        this.desc = desc;
        this.eventBus = eventBus;
        eventBus.register(RequestBoardRotateEvent.class, this);
        eventBus.register(MoveBallEvent.class, this);
        eventBus.register(RotateBoardEvent.class, this);
        eventBus.register(FinishedRotateAnimationEvent.class, this);

        for (RotateImageDescription imageDesc : desc.getImages())
        {
            CustomView imageView = imageDesc.getImage();
            imageView.setContentDescription(getResources().getString(imageDesc.getDescId()));
            boolean flip = RotateDirection.CounterClockwise == imageDesc.getDir();
            imageView.setFlip(flip);
            imageView.setOnClickListener(new RotateQuaterListener(desc.getArea(), imageDesc.getDir(), eventBus));
        }
        adapter = new BoardListAdapter(game.getBoard(), desc.getArea(), viewFactory);
        table = (GridView)layout.findViewById(R.id.gridView1);
        table.setAdapter(adapter);
        table.setOnItemClickListener(new BallInsertionListener(eventBus, adapter));
        setArrowsVisibility(View.INVISIBLE);
    }

    @Override
    public void onFinishedRotateAnimation(FinishedRotateAnimationEvent event)
    {
        table.invalidateViews();
        setArrowsVisibility(View.INVISIBLE);
        gamePhase = GamePhase.PutBall;
    }

    @Override
    public void onMoveBall(MoveBallEvent event)
    {
        if (!event.getBall().inside(desc.getArea()))
            return;
        ImageView ballView = (ImageView)findViewById(R.id.animball);
        ballView.setImageDrawable(getResources().getDrawable(event.getPlayer().getBallResource()));
        int ballPos = adapter.getItemPos(event.getBall());
        float deltaleft = getResources().getDimension(R.dimen.move_ball_animation_dx);
        float deltatop = getResources().getDimension(R.dimen.move_ball_animation_dy);
        float w = layout.getWidth();
        float h = layout.getHeight();
        float left = table.getChildAt(ballPos).getLeft() + deltaleft;
        float top = table.getChildAt(ballPos).getTop() + deltatop;
        TranslateAnimation anim = new TranslateAnimation(left, left, top - h / 2, top);
        anim.setDuration(1000);
        anim.setAnimationListener(new BallMoveListener(event.getBall(), event.getPlayer(), eventBus, ballView));
        ballView.setVisibility(VISIBLE);
        ballView.startAnimation(anim);
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        table.invalidateViews();
        setArrowsVisibility(View.VISIBLE);
        gamePhase = GamePhase.RotateBoard;
    }

    @Override
    public void onRotateBoard(RotateBoardEvent event)
    {
        if (!event.getRotateInfo().getQuarter().equals(desc.getArea()))
            return;
        int w = layout.getWidth() / 2;
        int h = layout.getHeight() / 2;
        float xpivot = w;//desc.getArea().isLeft() ? 0 : w + w / 2;
        float ypivot = h;//desc.getArea().isTop() ? 0 : h + h / 2;
        ScaleAnimation outside = new ScaleAnimation(1.5f, 0.6f, 1.5f, 0.6f, xpivot, ypivot);
        /*
         * TranslateAnimation outside = new TranslateAnimation(0,
         * desc.getArea().isLeft() ? -w : w, 0, desc.getArea() .isTop() ? -h :
         * h);
         */
        outside.setStartOffset(0);
        outside.setDuration(1000);
        RotateAnimation rotate = new RotateAnimation(0, event.getRotateInfo().getDirection()
                .equals(RotateDirection.Clockwise) ? 90 : -90, xpivot, ypivot);
        rotate.setStartOffset(1000);
        rotate.setDuration(1000);
        ScaleAnimation inside = new ScaleAnimation(0.6f, 1.5f, 0.6f, 1.5f, xpivot, ypivot);
        /*
         * TranslateAnimation inside = new TranslateAnimation(0,
         * desc.getArea().isLeft() ? w : -w, 0, desc.getArea() .isTop() ? h :
         * -h);
         */
        inside.setStartOffset(2000);
        inside.setDuration(1000);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(outside);
        set.addAnimation(rotate);
        set.addAnimation(inside);
        startAnimation(set);
        set.setAnimationListener(new RotateAnimationListener(event.getRotateInfo()));
    }

    private void setArrowsVisibility(int visibility)
    {
        for (RotateImageDescription imageDesc : desc.getImages())
        {
            imageDesc.getImage().setVisibility(visibility);
        }
    }
}