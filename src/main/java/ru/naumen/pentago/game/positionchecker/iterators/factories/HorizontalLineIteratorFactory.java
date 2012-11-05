/**
 * 
 */
package ru.naumen.pentago.game.positionchecker.iterators.factories;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.iterators.HorizontalLineIterator;
import ru.naumen.pentago.game.positionchecker.iterators.LineIterator;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class HorizontalLineIteratorFactory implements LineIteratorFactory
{
    @Override
    public LineIterator create(Board board)
    {
        return new HorizontalLineIterator(board);
    }
}