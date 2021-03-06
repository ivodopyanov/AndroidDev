/**
 * 
 */
package ru.naumen.framework.eventbus;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public interface EventBus
{
    <H extends Handler> void fireEvent(Event<H> event);

    <H extends Handler> HandlerRegistration register(Class<? extends Event<H>> eventClass, H handler);
}
