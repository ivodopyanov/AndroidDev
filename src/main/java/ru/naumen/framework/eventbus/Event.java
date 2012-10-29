/**
 * 
 */
package ru.naumen.framework.eventbus;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public interface Event<H extends Handler>
{
    void dispatch(H handler);
}