/**
 * 
 */
package ru.naumen.core.game.controller.events;

import ru.naumen.core.framework.eventbus.Event;
import ru.naumen.core.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 08.10.2012
 * 
 */
public class FinishedRotateAnimationEvent implements Event<FinishedRotateAnimationHandler>
{
    private final RotateInfo rotateInfo;

    public FinishedRotateAnimationEvent(RotateInfo rotateInfo)
    {
        this.rotateInfo = rotateInfo;
    }

    @Override
    public void dispatch(FinishedRotateAnimationHandler handler)
    {
        handler.onFinishedRotateAnimation(this);
    }

    public RotateInfo getRotateInfo()
    {
        return rotateInfo;
    }
}