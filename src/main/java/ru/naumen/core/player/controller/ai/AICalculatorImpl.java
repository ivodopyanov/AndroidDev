/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import java.util.List;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.Player;

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
