/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public interface RotateCornerHandler extends Handler
{
    void onRotateCorner(RotateCornerEvent event);
}