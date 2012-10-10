/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.strategies;

import ru.naumen.pentago.player.controller.ai.AIMoveInfo;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AIStrategy
{
    AIMoveInfo apply(int player);
}
