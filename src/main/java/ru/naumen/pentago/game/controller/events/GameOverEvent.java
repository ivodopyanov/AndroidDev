/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class GameOverEvent implements Event<GameOverHandler>
{
    private final int winner;

    public GameOverEvent(int winner)
    {
        this.winner = winner;
    }

    @Override
    public void dispatch(GameOverHandler handler)
    {
        handler.onGameOver(this);
    }

    public int getWinner()
    {
        return winner;
    }
}