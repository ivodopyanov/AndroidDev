package ru.naumen.core.game.controller;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.core.game.Constants;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;

public class BoardPositionChecker
{
    private class PlayerWonException extends Exception
    {
        private final int player;

        public PlayerWonException(int player)
        {
            this.player = player;
        }

        public int getPlayer()
        {
            return player;
        }
    }

    public int hasAnyPlayerWon(Board input)
    {
        int[][] board = input.toArray();
        try
        {
            checkHorizontal(board);
            checkVertical(board);
            checkDiagonal(board);
        }
        catch (PlayerWonException e)
        {
            return e.getPlayer();
        }
        return Ball.NO_PLAYER;
    }

    private void checkDiagonal(int[][] board) throws PlayerWonException
    {
        List<Integer> line = new ArrayList<Integer>();
        for (int x = 0; x < Constants.BOARD_SIZE; x++)
        {
            checkDiagonalLine(x, 0, 1, 1, line, board);
            checkDiagonalLine(x, Constants.BOARD_SIZE - 1, -1, -1, line, board);
        }
        for (int y = 0; y < Constants.BOARD_SIZE; y++)
        {
            checkDiagonalLine(0, y, 1, -1, line, board);
            checkDiagonalLine(Constants.BOARD_SIZE - 1, y, -1, 1, line, board);
        }
    }

    private void checkDiagonalLine(int startx, int starty, int xmodifier, int ymodifier, List<Integer> line,
            int[][] board) throws PlayerWonException
    {
        line.clear();
        int xx = startx, yy = starty;
        while (0 <= xx && xx < Constants.BOARD_SIZE && 0 <= yy && yy < Constants.BOARD_SIZE)
        {
            line.add(board[xx][yy]);
            xx += xmodifier;
            yy += ymodifier;
        }
        if (line.size() > 4)
            checkLine(line);
    }

    private void checkHorizontal(int[][] board) throws PlayerWonException
    {
        List<Integer> line = new ArrayList<Integer>();
        for (int y = 0; y < Constants.BOARD_SIZE; y++)
        {
            line.clear();
            for (int x = 0; x < Constants.BOARD_SIZE; x++)
                line.add(board[x][y]);
            checkLine(line);
        }
    }

    private void checkLine(List<Integer> line) throws PlayerWonException
    {
        int currentPlayer = -1;
        int count = 0;
        for (Integer player : line)
        {
            if (player == currentPlayer)
                count++;
            else
            {
                currentPlayer = player;
                count = 1;
            }
            if (count == 5 && currentPlayer != Ball.NO_PLAYER)
                throw new PlayerWonException(currentPlayer);
        }
    }

    private void checkVertical(int[][] board) throws PlayerWonException
    {
        List<Integer> line = new ArrayList<Integer>();
        for (int x = 0; x < Constants.BOARD_SIZE; x++)
        {
            line.clear();
            for (int y = 0; y < Constants.BOARD_SIZE; y++)
                line.add(board[x][y]);
            checkLine(line);
        }
    }

}
