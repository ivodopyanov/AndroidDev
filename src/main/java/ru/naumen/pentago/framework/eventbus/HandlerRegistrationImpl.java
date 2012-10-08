/**
 * 
 */
package ru.naumen.pentago.framework.eventbus;

import java.util.List;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public class HandlerRegistrationImpl<T extends Handler> implements HandlerRegistration
{
    private final List<T> handlers;
    private final T handler;

    public HandlerRegistrationImpl(List<T> handlers, T handler)
    {
        this.handlers = handlers;
        this.handler = handler;
    }

    @Override
    public void unregister()
    {
        handlers.remove(handler);
    }
}