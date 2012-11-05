/**
 * 
 */
package ru.naumen.pentago.player.controller.ai.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public interface RotateCalculatedHandler extends Handler
{
    void onRotateCalculated(RotateCalculatedEvent event);
}