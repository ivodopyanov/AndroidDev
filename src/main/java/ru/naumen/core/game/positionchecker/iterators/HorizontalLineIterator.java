/**
 * 
 */
package ru.naumen.core.game.positionchecker.iterators;

import ru.naumen.core.game.Constants;
import ru.naumen.core.game.model.Board;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class HorizontalLineIterator extends LineIterator
{
    public HorizontalLineIterator(Board board)
    {
        super(board, Constants.BOARD_SIZE);
    }

    @Override
    protected void fillNextLine()
    {
        for (int x = 0; x < Constants.BOARD_SIZE; x++)
            nextLine.add(board.getBall(x, pos));
    }
}