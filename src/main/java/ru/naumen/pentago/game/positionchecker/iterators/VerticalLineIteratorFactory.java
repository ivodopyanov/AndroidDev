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
public class VerticalLineIteratorFactory implements LineIteratorFactory
{
    @Override
    public LineIterator create(Board board)
    {
        return new VerticalLineIterator(board);
    }
}