/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;

/**
 * @author ivodopyanov
 * @since 26.10.2012
 * 
 */
public class StartedRotateAnimationEvent implements Event<StartedRotateAnimationHandler>
{
    @Override
    public void dispatch(StartedRotateAnimationHandler handler)
    {
        handler.onStartedRotateAnimation(this);
    }
}
