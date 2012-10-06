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
public class DiagonalLineIterator extends LineIterator
{
    public DiagonalLineIterator(Board board)
    {
        super(board, Constants.BOARD_SIZE * 4);
    }

    @Override
    protected void fillNextLine()
    {
        int x = getX();
        int y = getY();
        int dx = getDX();
        int dy = getDY();
        while (0 <= x && x < Constants.BOARD_SIZE && y < Constants.BOARD_SIZE)
        {
            nextLine.add(board.getBall(x, y));
            x += dx;
            y += dy;
        }
    }

    private int getDX()
    {
        if (pos < Constants.BOARD_SIZE * 2)
            return 1;
        else
            return -1;
    }

    private int getDY()
    {
        return 1;
    }

    private int getX()
    {
        if (pos < Constants.BOARD_SIZE)
            return 0;
        else if (pos < Constants.BOARD_SIZE * 2)
            return pos - Constants.BOARD_SIZE;
        else if (pos < Constants.BOARD_SIZE * 3)
            return pos - Constants.BOARD_SIZE * 2;
        else
            return Constants.BOARD_SIZE - 1;
    }

    private int getY()
    {
        if (pos < Constants.BOARD_SIZE)
            return Constants.BOARD_SIZE - pos - 1;
        else if (pos < Constants.BOARD_SIZE * 3)
            return 0;
        else
            return pos - Constants.BOARD_SIZE * 3;
    }
}