/**
 * 
 */
package ru.naumen.pentago.game.positionchecker.iterators;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.model.Board;

/**
 * Итератор, которые пробегает по вертикальным линиям доски
 * 
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class VerticalLineIterator extends LineIterator
{
    public VerticalLineIterator(Board board)
    {
        super(board, Constants.BOARD_SIZE);
    }

    @Override
    protected void fillNextLine()
    {
        for (int y = 0; y < Constants.BOARD_SIZE; y++)
            nextLine.add(board.getBall(pos, y));
    }
}