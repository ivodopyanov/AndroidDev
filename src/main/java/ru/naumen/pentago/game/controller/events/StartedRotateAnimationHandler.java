/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Handler;

/**
 * @author ivodopyanov
 * @since 26.10.2012
 * 
 */
public interface StartedRotateAnimationHandler extends Handler
{
    void onStartedRotateAnimation(StartedRotateAnimationEvent event);
}