/**
 * 
 */
package ru.naumen.pentago.game.positionchecker.iterators;

import ru.naumen.pentago.game.model.Board;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public interface LineIteratorFactory
{
    LineIterator create(Board board);
}