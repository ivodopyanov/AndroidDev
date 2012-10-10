/**
 * 
 */
package ru.naumen.pentago.game;

import ru.naumen.pentago.R;
import ru.naumen.pentago.game.model.Player;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class PlayerInfo extends LinearLayout
{
    private final View layout;

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

    public void init(Player player)
    {
        TextView title = (TextView)layout.findViewById(R.id.playerTitle);
        title.setText(getResources().getString(R.string.playerTitle) + player.getTitle());
        ImageView ball = (ImageView)layout.findViewById(R.id.playerBall);
        ball.setImageDrawable(getResources().getDrawable(player.getBallResource()));
    }
}
