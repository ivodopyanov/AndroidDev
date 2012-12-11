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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class PlayerInfo implements RequestBallMoveHandler
{
    private final View layout;
    private Player player;
    private final Context context;

    public PlayerInfo(Context context, View layout)
    {
        this.layout = layout;
        this.context = context;
    }

    public void init(Player player)
    {
        this.player = player;
        EventBus.INSTANCE.register(RequestBallMoveEvent.class, this);
        TextView title = (TextView)layout.findViewById(R.id.title);
        title.setText(player.getTitle());
        ImageView ball = (ImageView)layout.findViewById(R.id.ball);
        ball.setImageDrawable(context.getResources().getDrawable(player.getBallResource()));
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            ((ImageView)layout.findViewById(R.id.glow)).setVisibility(View.VISIBLE);
        }
        else
        {
            ((ImageView)layout.findViewById(R.id.glow)).setVisibility(View.INVISIBLE);
        }
    }
}