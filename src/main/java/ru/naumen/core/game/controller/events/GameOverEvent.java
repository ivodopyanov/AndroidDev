/**
 * 
 */
package ru.naumen.core.game.controller.events;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class GameOverEvent
{
    private final int winner;

    public GameOverEvent(int winner)
    {
        this.winner = winner;
    }

    public int getWinner()
    {
        return winner;
    }
}