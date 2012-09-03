package ru.naumen.core.game;

import java.util.List;

import ru.naumen.core.R;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.SquareArea;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

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

    private final Context context;
    private final List<Player> players;
    private final Resources resources;
    private final SquareArea squareArea;
    private final List<Ball> balls;

    public BoardListAdapter(Context context, Board board, List<Player> players, Resources resources,
            SquareArea squareArea)
    {
        this.context = context;
        this.players = players;
        this.resources = resources;
        this.squareArea = squareArea;
        this.balls = Lists.newArrayList(Collections2.filter(board.getBalls(), BALL_FILTER));
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Ball ball = balls.get(position);
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(resources.getDrawable(getBallResource(ball)));
        imageView.setScaleType(ScaleType.CENTER_CROP);
        return imageView;
    }

    private int getBallResource(Ball ball)
    {
        if (ball.getPlayer() == -1)
        {
            return R.drawable.ball_empty;
        }
        else
        {
            Player player = players.get(ball.getPlayer());
            return player.getBallResource();
        }
    }
}