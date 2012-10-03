/**
 * 
 */
package ru.naumen.core.game.controller.events;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class RequestBallMoveEvent
{
    private final String activePlayerCode;

    public RequestBallMoveEvent(String activePlayerCode)
    {
        this.activePlayerCode = activePlayerCode;
    }

    public String getActivePlayerCode()
    {
        return activePlayerCode;
    }
}