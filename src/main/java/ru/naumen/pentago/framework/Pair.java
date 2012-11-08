/**
 * 
 */
package ru.naumen.pentago.framework;

/**
 * @author ivodopyanov
 * @since 06.11.2012
 * 
 */
public class Pair<T, V>
{
    public static <T, V> Pair<T, V> create(T first, V second)
    {
        return new Pair<T, V>(first, second);
    }

    private final T first;
    private final V second;

    public Pair(T first, V second)
    {
        this.first = first;
        this.second = second;
    }

    public T getFirst()
    {
        return first;
    }

    public V getSecond()
    {
        return second;
    }

}
