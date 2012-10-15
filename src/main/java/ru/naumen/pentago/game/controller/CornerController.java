package ru.naumen.pentago.game.controller;

import java.util.List;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.collections.Collections;
import ru.naumen.pentago.framework.collections.Predicate;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.LogTag;
import ru.naumen.pentago.game.controller.GameController.GamePhase;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.controller.events.FinishedRotateAnimationEvent;
import ru.naumen.pentago.game.controller.events.InsertBallInCornerEvent;
import ru.naumen.pentago.game.controller.events.MoveBallEvent;
import ru.naumen.pentago.game.controller.events.MoveBallHandler;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBallMoveHandler;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CornerController extends RelativeLayout implements RequestBoardRotateHandler, MoveBallHandler,
        RotateBoardHandler, RequestBallMoveHandler
{
    private static class BallInsertionListener implements OnClickListener
    {
        private final EventBus eventBus;
        private final Ball ball;

        public BallInsertionListener(EventBus eventBus, Ball ball)
        {
            this.eventBus = eventBus;
            this.ball = ball;
        }

        @Override
        public void onClick(View arg0)
        {
            eventBus.fireEvent(new InsertBallInCornerEvent(ball));
        }
    }

    private class BallMoveListener implements AnimationListener
    {
        private final Ball ball;
        private final ImageView animBallView;
        private final ImageView ballView;
        private final Player player;

        public BallMoveListener(Ball ball, ImageView animBallView, ImageView ballView, Player player)
        {
            this.ball = ball;
            this.animBallView = animBallView;
            this.ballView = ballView;
            this.player = player;
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            Log.d(LogTag.CORNER, "Ball move animation ended");
            ballView.setImageDrawable(getResources().getDrawable(player.getBallResource()));
            ball.setPlayer(game.getPlayers().indexOf(player));
            animBallView.clearAnimation();
            animBallView.setVisibility(INVISIBLE);
            eventBus.fireEvent(new RequestBoardRotateEvent(player.getCode()));
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

        public RotateAnimationListener(RotateInfo rotateInfo)
        {
            this.rotateInfo = rotateInfo;
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            Log.d(LogTag.CORNER, "onAnimationEnd");
            eventBus.fireEvent(new FinishedRotateAnimationEvent(rotateInfo));
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

    private final Predicate<Ball> BALL_FILTER = new Predicate<Ball>()
    {
        @Override
        public boolean apply(Ball ball)
        {
            return ball.inside(desc.getArea());
        }
    };

    private CornerViewDescription desc;
    private final View layout;
    private GamePhase gamePhase = GamePhase.PutBall;
    private EventBus eventBus;
    private List<Ball> balls;
    private Game game;
    private static final int[] BALL_IDS = new int[] { R.id.ball11, R.id.ball12, R.id.ball13, R.id.ball21, R.id.ball22,
            R.id.ball23, R.id.ball31, R.id.ball32, R.id.ball33 };

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

    public void init(CornerViewDescription desc, Game game, EventBus eventBus)
    {
        this.desc = desc;
        this.eventBus = eventBus;
        this.game = game;
        balls = Collections.filter(game.getBoard().getBalls(), BALL_FILTER);
        eventBus.register(RequestBallMoveEvent.class, this);
        eventBus.register(RequestBoardRotateEvent.class, this);
        eventBus.register(MoveBallEvent.class, this);
        eventBus.register(RotateBoardEvent.class, this);

        for (RotateImageDescription imageDesc : desc.getImages())
        {
            CustomView imageView = imageDesc.getImage();
            imageView.setContentDescription(getResources().getString(imageDesc.getDescId()));
            imageView.setOnClickListener(new RotateQuaterListener(desc.getArea(), imageDesc.getDir(), eventBus));
        }
        for (int i = 0; i < BALL_IDS.length; i++)
        {
            findViewById(BALL_IDS[i]).setOnClickListener(new BallInsertionListener(eventBus, balls.get(i)));
        }
        setArrowsVisibility(View.INVISIBLE);
    }

    @Override
    public void onMoveBall(MoveBallEvent event)
    {
        if (!event.getBall().inside(desc.getArea()))
            return;
        Log.d(LogTag.CORNER, "onMoveBall");
        ImageView animBall = (ImageView)findViewById(R.id.animBall);
        animBall.setImageDrawable(getResources().getDrawable(event.getPlayer().getBallResource()));
        int ballPos = balls.indexOf(event.getBall());
        float deltaleft = getResources().getDimension(R.dimen.move_ball_animation_dx);
        float deltatop = getResources().getDimension(R.dimen.move_ball_animation_dy);
        float w = layout.getWidth();
        float h = layout.getHeight();
        ImageView ballView = (ImageView)findViewById(BALL_IDS[ballPos]);
        ImageView startView = (ImageView)findViewById(R.id.ball22);

        float left = ballView.getLeft() + deltaleft;
        float top = ballView.getTop() + deltatop;

        TranslateAnimation anim = new TranslateAnimation(startView.getLeft() - startView.getWidth() / 2, left,
                startView.getTop() - startView.getHeight() / 2, top);
        anim.setDuration(1000);
        ScaleAnimation scaleAnim = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f, ballView.getWidth() / 2,
                ballView.getHeight() / 2);
        scaleAnim.setDuration(1000);
        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(anim);
        animSet.addAnimation(scaleAnim);
        animSet.setAnimationListener(new BallMoveListener(event.getBall(), animBall, ballView, event.getPlayer()));
        animBall.setVisibility(VISIBLE);
        animBall.bringToFront();
        animBall.startAnimation(animSet);
        Log.d(LogTag.CORNER, "onMoveBall finished, animation started");
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        Log.d(LogTag.CORNER, "onRequestBallMove");
        updateBalls();
        setArrowsVisibility(View.INVISIBLE);
        gamePhase = GamePhase.PutBall;
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        Log.d(LogTag.CORNER, "onRequestBoardRotate");
        //updateBalls();
        setArrowsVisibility(View.VISIBLE);
        gamePhase = GamePhase.RotateBoard;
    }

    @Override
    public void onRotateBoard(RotateBoardEvent event)
    {
        if (!event.getRotateInfo().getQuarter().equals(desc.getArea()))
            return;
        Log.d(LogTag.CORNER, "onRotateBoard");
        int w = layout.getWidth() / 2;
        int h = layout.getHeight() / 2;
        float xpivot = w;
        float ypivot = h;
        ScaleAnimation outside = new ScaleAnimation(1.2f, 0.8f, 1.2f, 0.8f, xpivot, ypivot);
        outside.setStartOffset(0);
        outside.setDuration(1000);
        RotateAnimation rotate = new RotateAnimation(0, event.getRotateInfo().getDirection()
                .equals(RotateDirection.Clockwise) ? 90 : -90, xpivot, ypivot);
        rotate.setStartOffset(1000);
        rotate.setDuration(1000);
        ScaleAnimation inside = new ScaleAnimation(0.8f, 1.25f, 0.8f, 1.25f, xpivot, ypivot);
        inside.setStartOffset(2000);
        inside.setDuration(1000);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(outside);
        set.addAnimation(rotate);
        set.addAnimation(inside);
        set.setFillAfter(true);
        set.setAnimationListener(new RotateAnimationListener(event.getRotateInfo()));
        startAnimation(set);
        Log.d(LogTag.CORNER, "onRotateBoard finished, animation started");
    }

    private int getBallResource(Ball ball)
    {
        if (ball.getPlayer() == Ball.NO_PLAYER)
        {
            return R.drawable.circle_100;
        }
        else
        {
            Player player = game.getPlayers().get(ball.getPlayer());
            return player.getBallResource();
        }
    }

    private void setArrowsVisibility(int visibility)
    {
        for (RotateImageDescription imageDesc : desc.getImages())
        {
            imageDesc.getImage().setVisibility(visibility);
        }
    }

    private void updateBalls()
    {
        for (int i = 0; i < balls.size(); i++)
        {
            ImageView view = ((ImageView)findViewById(BALL_IDS[i]));
            view.setImageDrawable(getResources().getDrawable(getBallResource(balls.get(i))));
        }
    }
}