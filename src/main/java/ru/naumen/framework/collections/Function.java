/**
 * 
 */
package ru.naumen.framework.collections;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public interface Function<T, V>
{
    V apply(T value);
}