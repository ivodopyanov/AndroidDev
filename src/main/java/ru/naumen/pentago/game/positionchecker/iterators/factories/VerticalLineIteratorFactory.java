/**
 * 
 */
package ru.naumen.pentago.game.positionchecker.iterators.factories;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.VerticalLineIterator;

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