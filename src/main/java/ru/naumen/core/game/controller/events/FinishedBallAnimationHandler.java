/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 08.10.2012
 * 
 */
public interface FinishedBallAnimationHandler extends Handler
{
    void onFinishedBallAnimation(FinishedBallAnimationEvent event);
}