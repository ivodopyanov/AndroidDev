/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public interface RequestBallMoveHandler extends Handler
{
    void onRequestBallMove(RequestBallMoveEvent event);
}