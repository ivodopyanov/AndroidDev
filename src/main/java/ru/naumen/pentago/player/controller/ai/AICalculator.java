/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import ru.naumen.pentago.framework.eventbus.Event;
import ru.naumen.pentago.game.model.Player;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AICalculator<T extends Event<?>>
{
    T calculate(Player player);
}