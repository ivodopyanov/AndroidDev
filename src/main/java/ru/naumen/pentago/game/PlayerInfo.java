/**
 * 
 */
package ru.naumen.pentago.game;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBallMoveHandler;
import ru.naumen.pentago.game.model.Player;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class PlayerInfo extends RelativeLayout implements RequestBallMoveHandler
{
    private final View layout;
    private Player player;

    public PlayerInfo(Context context)
    {
        super(context);
        layout = LayoutInflater.from(context).inflate(R.layout.playerinfo, this, true);
    }

    public PlayerInfo(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        layout = LayoutInflater.from(context).inflate(R.layout.playerinfo, this, true);
    }

    public void init(Player player, EventBus eventBus)
    {
        this.player = player;
        eventBus.register(RequestBallMoveEvent.class, this);
        TextView title = (TextView)layout.findViewById(R.id.title);
        title.setText(player.getTitle());
        ImageView ball = (ImageView)layout.findViewById(R.id.ball);
        ball.setImageDrawable(getResources().getDrawable(player.getBallResource()));
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        ImageView light = (ImageView)findViewById(R.id.light);
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            light.setVisibility(VISIBLE);
        }
        else
        {
            light.setVisibility(INVISIBLE);
        }
    }
}