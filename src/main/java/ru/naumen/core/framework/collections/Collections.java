/**
 * 
 */
package ru.naumen.core.framework.collections;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ivodopyanov
 * @since 04.10.2012
 * 
 */
public class Collections
{
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate)
    {
        List<T> result = new LinkedList<T>();
        for (T value : list)
        {
            if (predicate.apply(value))
                result.add(value);
        }
        return result;
    }

    public static <T, V> List<V> transform(List<T> list, Function<T, V> function)
    {
        List<V> result = new LinkedList<V>();
        for (T value : list)
        {
            result.add(function.apply(value));
        }
        return result;
    }

}
