/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

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