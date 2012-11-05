/**
 * 
 */
package ru.naumen.pentago.game.positionchecker.iterators;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.model.Board;

/**
 * Итератор, который пробегает по горизонтальным линиям доски
 * 
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