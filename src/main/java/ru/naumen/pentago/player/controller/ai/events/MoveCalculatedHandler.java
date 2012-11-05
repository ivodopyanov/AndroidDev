/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public interface MoveCalculatedHandler extends Handler
{
    void onMoveCalculated(MoveCalculatedEvent event);
}