/**
 * 
 */
package ru.naumen.pentago;

import java.io.Serializable;
import java.util.List;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.GameActivity;
import ru.naumen.pentago.game.model.Player;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class GoToLocalGameListener implements OnClickListener
{
    private final Context context;
    private final List<Player> players;

    public GoToLocalGameListener(Context context, List<Player> players)
    {
        this.context = context;
        this.players = players;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)players);
        context.startActivity(intent);
    }
}