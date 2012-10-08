/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Player;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public abstract class AICalculatorImpl<T extends Event<?>> implements AICalculator<T>
{
    protected final List<Player> players;

    protected AICalculatorImpl(List<Player> players)
    {
        this.players = players;
    }
}