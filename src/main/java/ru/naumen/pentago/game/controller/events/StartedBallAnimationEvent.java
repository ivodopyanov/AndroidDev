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
public class StartedBallAnimationEvent implements Event<StartedBallAnimationHandler>
{
    @Override
    public void dispatch(StartedBallAnimationHandler handler)
    {
        handler.onStartedBallAnimation(this);
    }
}