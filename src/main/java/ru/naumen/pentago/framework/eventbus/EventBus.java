/**
 * 
 */
package ru.naumen.pentago.framework.eventbus;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public interface EventBus
{
    public static final EventBus INSTANCE = new SimpleEventBus();

    <H extends Handler> void fireEvent(Event<H> event);

    <H extends Handler> HandlerRegistration register(Class<? extends Event<H>> eventClass, H handler);
}
