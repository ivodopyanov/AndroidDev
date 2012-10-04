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
public interface RequestBallMoveHandler extends Handler
{
    void onRequestBallMove(RequestBallMoveEvent event);
}