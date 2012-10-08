/**
 * 
 */
package ru.naumen.core.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.naumen.core.R;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Player;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout.LayoutParams;

/**
 * @author ivodopyanov
 * @since 07.10.2012
 * 
 */
public class BallViewFactory
{
    private static class PoolInfo
    {
        public static PoolInfo fromBall(Ball ball)
        {
            return new PoolInfo(ball.getPlayer());
        }

        int player;

        private PoolInfo(int player)
        {
            this.player = player;
        }

        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof PoolInfo))
                return false;
            return player == ((PoolInfo)o).player;
        }

        @Override
        public int hashCode()
        {
            return player;
        }
    }

    private final Context context;
    private final Resources resources;
    private final List<Player> players;

    private final Map<PoolInfo, View> viewPool;

    public BallViewFactory(Context context, Resources resources, List<Player> players)
    {
        this.context = context;
        this.resources = resources;
        this.players = players;
        viewPool = new HashMap<PoolInfo, View>();
    }

    public View create(Ball ball)
    {
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(resources.getDrawable(getBallResource(ball)));
        imageView.setScaleType(ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setAdjustViewBounds(true);
        return imageView;
    }

    public View createFromPool(Ball ball)
    {
        PoolInfo poolInfo = PoolInfo.fromBall(ball);
        if (viewPool.containsKey(poolInfo))
            return viewPool.get(poolInfo);
        View view = create(ball);
        viewPool.put(poolInfo, view);
        return view;
    }

    private int getBallResource(Ball ball)
    {
        if (ball.getPlayer() == Ball.NO_PLAYER)
        {
            return R.drawable.glow;
        }
        else
        {
            Player player = players.get(ball.getPlayer());
            return player.getBallResource();
        }
    }
}