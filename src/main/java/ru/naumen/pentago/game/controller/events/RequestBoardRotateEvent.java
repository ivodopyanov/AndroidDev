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
public class RequestBoardRotateEvent implements Event<RequestBoardRotateHandler>
{
    private final String activePlayerCode;

    public RequestBoardRotateEvent(String activePlayerCode)
    {
        this.activePlayerCode = activePlayerCode;
    }

    @Override
    public void dispatch(RequestBoardRotateHandler handler)
    {
        handler.onRequestBoardRotate(this);
    }

    public String getActivePlayerCode()
    {
        return activePlayerCode;
    }
}