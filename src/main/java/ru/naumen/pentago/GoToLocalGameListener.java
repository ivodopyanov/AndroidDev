/**
 * 
 */
package ru.naumen.pentago;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.DefaultPlayerDescription;
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

    public GoToLocalGameListener(Context context, List<DefaultPlayerDescription> players)
    {
        this.context = context;
        this.players = generatePlayers(players);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)players);
        context.startActivity(intent);
    }

    private List<Player> generatePlayers(List<DefaultPlayerDescription> players)
    {
        List<Player> result = new ArrayList<Player>();
        for (DefaultPlayerDescription playerDesc : players)
        {
            Player player = new Player(context.getResources().getString(playerDesc.getTitleCode()),
                    playerDesc.getCode(), playerDesc.getBallResource(), playerDesc.getType());
            result.add(player);
        }
        return result;
    }
}