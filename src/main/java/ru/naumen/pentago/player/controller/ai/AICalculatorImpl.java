/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.events.MoveCalculatedEvent;
import ru.naumen.pentago.game.model.Player;
import android.os.AsyncTask;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public abstract class AICalculatorImpl implements AICalculator
{
    protected final List<Player> players;
    protected final EventBus eventBus;

    protected AICalculatorImpl(List<Player> players, EventBus eventBus)
    {
        this.players = players;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(Player player)
    {
        new AsyncTask<Player, Integer, MoveCalculatedEvent>()
        {
            @Override
            protected MoveCalculatedEvent doInBackground(Player... players)
            {
                AIMoveInfo moveInfo = runCalculation(players[0]);
                return new MoveCalculatedEvent(moveInfo, players[0]);
            }

            @Override
            protected void onPostExecute(MoveCalculatedEvent event)
            {
                eventBus.fireEvent(event);
            }

        }.execute(player);
    }

    abstract protected AIMoveInfo runCalculation(Player player);
}