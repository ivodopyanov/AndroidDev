/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 08.10.2012
 * 
 */
public interface FinishedBallAnimationHandler extends Handler
{
    void onFinishedBallAnimation(FinishedBallAnimationEvent event);
}