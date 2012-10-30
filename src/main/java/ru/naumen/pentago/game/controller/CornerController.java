package ru.naumen.pentago.game.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.ChildLayoutDescriptor;
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
import ru.naumen.pentago.game.controller.events.StartedBallAnimationEvent;
import ru.naumen.pentago.game.controller.events.StartedBallAnimationHandler;
import ru.naumen.pentago.game.controller.events.StartedRotateAnimationEvent;
import ru.naumen.pentago.game.controller.events.StartedRotateAnimationHandler;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class CornerController extends ViewGroup implements RequestBoardRotateHandler, MoveBallHandler,
        RotateBoardHandler, RequestBallMoveHandler, StartedRotateAnimationHandler, StartedBallAnimationHandler
{
    private class BallInsertionListener implements View.OnClickListener
    {
        private final Ball ball;

        public BallInsertionListener(Ball ball)
        {
            this.ball = ball;
        }

        @Override
        public void onClick(View arg0)
        {
            if (!GamePhase.PutBall.equals(gamePhase))
                return;
            if (ballMoveActive)
                return;
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
            animBallView.clearAnimation();
            ballView.setImageDrawable(getResources().getDrawable(player.getBallResource()));
            ball.setPlayer(game.getPlayers().indexOf(player));
            animBallView.clearAnimation();
            animBallView.setVisibility(View.INVISIBLE);
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

    private class RotateQuaterListener implements View.OnClickListener
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
            if (GamePhase.RotateBoard.equals(gamePhase) && !rotationActive)
            {
                eventBus.fireEvent(new RotateCornerEvent(area, direction));
            }
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

    private static final Map<Integer, ChildLayoutDescriptor> LAYOUT_DESCS;

    static
    {
        LAYOUT_DESCS = new HashMap<Integer, ChildLayoutDescriptor>();
        LAYOUT_DESCS.put(R.id.animBall, new ChildLayoutDescriptor(0.25f, 0.25f, 0, 0));
        LAYOUT_DESCS.put(R.id.ball11, new ChildLayoutDescriptor(0.25f, 0.25f, 0.125f, 0.125f));
        LAYOUT_DESCS.put(R.id.ball12, new ChildLayoutDescriptor(0.25f, 0.25f, 0.375f, 0.125f));
        LAYOUT_DESCS.put(R.id.ball13, new ChildLayoutDescriptor(0.25f, 0.25f, 0.625f, 0.125f));
        LAYOUT_DESCS.put(R.id.ball21, new ChildLayoutDescriptor(0.25f, 0.25f, 0.125f, 0.375f));
        LAYOUT_DESCS.put(R.id.ball22, new ChildLayoutDescriptor(0.25f, 0.25f, 0.375f, 0.375f));
        LAYOUT_DESCS.put(R.id.ball23, new ChildLayoutDescriptor(0.25f, 0.25f, 0.625f, 0.375f));
        LAYOUT_DESCS.put(R.id.ball31, new ChildLayoutDescriptor(0.25f, 0.25f, 0.125f, 0.625f));
        LAYOUT_DESCS.put(R.id.ball32, new ChildLayoutDescriptor(0.25f, 0.25f, 0.375f, 0.625f));
        LAYOUT_DESCS.put(R.id.ball33, new ChildLayoutDescriptor(0.25f, 0.25f, 0.625f, 0.625f));
    }

    private CornerViewDescription desc;

    private GamePhase gamePhase = GamePhase.PutBall;
    private EventBus eventBus;
    private List<Ball> balls;
    private Game game;
    private static final int[] BALL_IDS = new int[] { R.id.ball11, R.id.ball12, R.id.ball13, R.id.ball21, R.id.ball22,
            R.id.ball23, R.id.ball31, R.id.ball32, R.id.ball33 };
    private RotateInfo rotateInfoBuf;
    private boolean rotationActive = false;
    private boolean ballMoveActive = false;

    public CornerController(Context context)
    {
        super(context);
        initLayout();
    }

    public CornerController(Context context, AttributeSet attrSet)
    {
        super(context, attrSet);
        initLayout();
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
        eventBus.register(StartedRotateAnimationEvent.class, this);
        eventBus.register(StartedBallAnimationEvent.class, this);

        for (RotateImageDescription imageDesc : desc.getImages())
        {
            ImageView imageView = imageDesc.getImage();
            imageView.setContentDescription(getResources().getString(imageDesc.getDescId()));
            imageView.setOnClickListener(new RotateQuaterListener(desc.getArea(), imageDesc.getDir(), eventBus));
        }
        for (int i = 0; i < BALL_IDS.length; i++)
        {
            findViewById(BALL_IDS[i]).setOnClickListener(new BallInsertionListener(balls.get(i)));
        }
        setArrowsVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationEnd()
    {
        Log.d(LogTag.CORNER, "onAnimationEnd");
        super.onAnimationEnd();
        for (int ballId : BALL_IDS)
        {
            findViewById(ballId).clearAnimation();
        }
        game.getBoard().rotate(rotateInfoBuf);
        updateBalls();
        eventBus.fireEvent(new FinishedRotateAnimationEvent(rotateInfoBuf));
    }

    @Override
    public void onMoveBall(MoveBallEvent event)
    {
        if (!event.getBall().inside(desc.getArea()))
            return;
        if (event.getBall().getPlayer() != Ball.NO_PLAYER)
            return;
        Log.d(LogTag.CORNER, "onMoveBall");
        eventBus.fireEvent(new StartedBallAnimationEvent());
        ImageView animBall = (ImageView)findViewById(R.id.animBall);
        animBall.setImageDrawable(getResources().getDrawable(event.getPlayer().getBallResource()));
        int ballPos = balls.indexOf(event.getBall());
        float deltaleft = getResources().getDimension(R.dimen.move_ball_animation_dx);
        float deltatop = getResources().getDimension(R.dimen.move_ball_animation_dy);
        float w = getWidth();
        float h = getHeight();
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
        animBall.setVisibility(View.VISIBLE);
        animBall.bringToFront();
        animBall.startAnimation(animSet);
        Log.d(LogTag.CORNER, "onMoveBall finished, animation started");
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        Log.d(LogTag.CORNER, "onRequestBallMove");
        rotationActive = false;
        setArrowsVisibility(View.INVISIBLE);
        gamePhase = GamePhase.PutBall;
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        Log.d(LogTag.CORNER, "onRequestBoardRotate");
        ballMoveActive = false;
        setArrowsVisibility(View.VISIBLE);
        gamePhase = GamePhase.RotateBoard;
    }

    @Override
    public void onRotateBoard(RotateBoardEvent event)
    {
        if (!event.getRotateInfo().getQuarter().equals(desc.getArea()))
            return;
        Log.d(LogTag.CORNER, "onRotateBoard");
        eventBus.fireEvent(new StartedRotateAnimationEvent());
        Animation ballRotateAnimation = generateBallRotateAnimation(event.getRotateInfo().getDirection());
        Animation quarterRotateAnimation = generateQuarterRotateAnimation(event.getRotateInfo().getDirection());
        rotateInfoBuf = event.getRotateInfo();
        for (int ballId : BALL_IDS)
        {
            findViewById(ballId).startAnimation(ballRotateAnimation);
        }
        startAnimation(quarterRotateAnimation);
        Log.d(LogTag.CORNER, "onRotateBoard finished, animation started");
    }

    @Override
    public void onStartedBallAnimation(StartedBallAnimationEvent event)
    {
        ballMoveActive = true;
    }

    @Override
    public void onStartedRotateAnimation(StartedRotateAnimationEvent event)
    {
        rotationActive = true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int w = r - l;
        int h = b - t;
        int size = w;
        if (h < size)
            size = h;
        for (Entry<Integer, ChildLayoutDescriptor> entry : LAYOUT_DESCS.entrySet())
        {
            View view = findViewById(entry.getKey());
            int childl = (int)(size * entry.getValue().getLeftPercent());
            int childr = childl + (int)(size * entry.getValue().getWidthPercent());
            int childt = (int)(size * entry.getValue().getTopPercent());
            int childb = childt + (int)(size * entry.getValue().getHeightPercent());
            view.layout(childl, childt, childr, childb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (size > MeasureSpec.getSize(heightMeasureSpec))
            size = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(size, size);
        for (Entry<Integer, ChildLayoutDescriptor> entry : LAYOUT_DESCS.entrySet())
        {
            View view = findViewById(entry.getKey());
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int)(size * entry.getValue().getWidthPercent()),
                    MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int)(size * entry.getValue().getHeightPercent()),
                    MeasureSpec.EXACTLY);
            view.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    private Animation generateBallRotateAnimation(RotateDirection direction)
    {
        View ballView = findViewById(R.id.ball11);
        int w = ballView.getWidth() / 2;
        int h = ballView.getHeight() / 2;
        float xpivot = w;
        float ypivot = h;
        RotateAnimation ballRotate = new RotateAnimation(0, direction.equals(RotateDirection.Clockwise) ? -90 : 90,
                xpivot, ypivot);
        ballRotate.setStartOffset(1000);
        ballRotate.setDuration(1000);
        ballRotate.setFillEnabled(true);
        ballRotate.setFillAfter(true);
        return ballRotate;
    }

    private Animation generateQuarterRotateAnimation(RotateDirection direction)
    {
        int w = getWidth() / 2;
        int h = getHeight() / 2;
        float xpivot = w;
        float ypivot = h;
        ScaleAnimation outside = new ScaleAnimation(1.2f, 0.8f, 1.2f, 0.8f, xpivot, ypivot);
        outside.setStartOffset(0);
        outside.setDuration(1000);
        RotateAnimation rotate = new RotateAnimation(0, direction.equals(RotateDirection.Clockwise) ? 90 : -90, xpivot,
                ypivot);
        rotate.setStartOffset(1000);
        rotate.setDuration(1000);
        ScaleAnimation inside = new ScaleAnimation(0.8f, 1.25f, 0.8f, 1.25f, xpivot, ypivot);
        inside.setStartOffset(2000);
        inside.setDuration(1000);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(outside);
        set.addAnimation(rotate);
        set.addAnimation(inside);
        return set;
    }

    private int getBallResource(Ball ball)
    {
        if (ball.getPlayer() == Ball.NO_PLAYER)
        {
            return R.drawable.emptyball;
        }
        else
        {
            Player player = game.getPlayers().get(ball.getPlayer());
            return player.getBallResource();
        }
    }

    private void initLayout()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.corner, this);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.smallsquare));
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