package ru.naumen.core.game;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.core.framework.collections.Collections;
import ru.naumen.core.framework.collections.Predicate;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Quarter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BoardListAdapter extends BaseAdapter
{
    private final Predicate<Ball> BALL_FILTER = new Predicate<Ball>()
    {
        @Override
        public boolean apply(Ball ball)
        {
            return ball.inside(squareArea);
        }
    };

    private final Quarter squareArea;
    private final List<Ball> balls;
    private final BallViewFactory ballFactory;

    public BoardListAdapter(Board board, Quarter squareArea, BallViewFactory ballFactory)
    {
        this.squareArea = squareArea;
        this.balls = new ArrayList<Ball>(Collections.filter(board.getBalls(), BALL_FILTER));
        this.ballFactory = ballFactory;
    }

    @Override
    public int getCount()
    {
        return balls.size();
    }

    @Override
    public Object getItem(int position)
    {
        return balls.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return balls.get(position).getY();
    }

    public int getItemPos(Ball ball)
    {
        return balls.indexOf(ball);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return ballFactory.create(balls.get(position));
    }
}