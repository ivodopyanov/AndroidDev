/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.player.controller.ai.events.MoveCalculatedEvent;
import ru.naumen.pentago.player.controller.ai.events.RotateCalculatedEvent;
import android.os.AsyncTask;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public abstract class AICalculatorImpl implements AICalculator
{
    protected final List<Player> players;

    protected AICalculatorImpl(List<Player> players)
    {
        this.players = players;
    }

    @Override
    public void calculateMove(Player player)
    {
        new AsyncTask<Player, Integer, MoveCalculatedEvent>()
        {
            @Override
            protected MoveCalculatedEvent doInBackground(Player... players)
            {
                Ball moveInfo = runMoveCalculation(players[0]);
                return new MoveCalculatedEvent(moveInfo, players[0]);
            }

            @Override
            protected void onPostExecute(MoveCalculatedEvent event)
            {
                EventBus.INSTANCE.fireEvent(event);
            }

        }.execute(player);
    }

    @Override
    public void calculateRotate(Player player)
    {
        new AsyncTask<Player, Integer, RotateCalculatedEvent>()
        {

            @Override
            protected RotateCalculatedEvent doInBackground(Player... players)
            {
                RotateInfo rotateInfo = runRotateCalculation(players[0]);
                return new RotateCalculatedEvent(rotateInfo, players[0]);
            }

            @Override
            protected void onPostExecute(RotateCalculatedEvent event)
            {
                EventBus.INSTANCE.fireEvent(event);
            }
        }.execute(player);
    }

    abstract protected Ball runMoveCalculation(Player player);

    abstract protected RotateInfo runRotateCalculation(Player player);
}