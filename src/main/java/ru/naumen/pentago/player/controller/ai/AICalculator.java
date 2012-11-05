/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import ru.naumen.pentago.game.model.Player;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AICalculator
{
    void calculateMove(Player player);

    void calculateRotate(Player player);
}