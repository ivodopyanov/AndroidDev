package ru.naumen.core.game;

import java.util.List;

import ru.naumen.core.R;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BoardListAdapter extends BaseAdapter
{
    private final Context context;
    private final Board board;
    private final List<Player> players;
    private final Resources resources;

    public BoardListAdapter(Context context, Board board, List<Player> players, Resources resources)
    {
        this.context = context;
        this.board = board;
        this.players = players;
        this.resources = resources;
    }

    @Override
    public int getCount()
    {
        return board.getBalls().size();
    }

    @Override
    public Object getItem(int position)
    {
        return board.getBalls().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return board.getBalls().get(position).getY();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Ball ball = board.getBalls().get(position);

        ImageView imageView = new ImageView(context);

        imageView.setImageDrawable(resources.getDrawable(getBallResource(ball)));
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