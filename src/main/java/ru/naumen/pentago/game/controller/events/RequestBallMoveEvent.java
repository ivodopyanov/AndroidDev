/**
 * 
 */
package ru.naumen.pentago.game.controller.events;

import ru.naumen.pentago.framework.eventbus.Event;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class RequestBallMoveEvent implements Event<RequestBallMoveHandler>
{
    private final String activePlayerCode;

    public RequestBallMoveEvent(String activePlayerCode)
    {
        this.activePlayerCode = activePlayerCode;
    }

    @Override
    public void dispatch(RequestBallMoveHandler handler)
    {
        handler.onRequestBallMove(this);
    }

    public String getActivePlayerCode()
    {
        return activePlayerCode;
    }
}