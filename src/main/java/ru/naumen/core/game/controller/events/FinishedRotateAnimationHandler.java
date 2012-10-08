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
public interface FinishedRotateAnimationHandler extends Handler
{
    void onFinishedRotateAnimation(FinishedRotateAnimationEvent event);
}