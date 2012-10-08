/**
 * 
 */
package ru.naumen.core.player.controller.ai;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.Player;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AICalculator<T extends Event<?>>
{
    T calculate(Player player);
}