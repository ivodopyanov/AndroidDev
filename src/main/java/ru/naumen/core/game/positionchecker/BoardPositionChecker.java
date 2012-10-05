package ru.naumen.core.game.positionchecker;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.core.game.Constants;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;

public class BoardPositionChecker
{
    private final LineCheckPredicate predicate;

    public BoardPositionChecker(CheckPattern... patterns)
    {
        this.predicate = new LineCheckPredicate(patterns);
    }

    public Ball findMove(Board input, int player)
    {
        int[] players = new int[] { player };
        try
        {
            checkHorizontal(input, players);
            checkVertical(input, players);
            checkDiagonal(input, players);
        }
        catch (MoveFoundException e)
        {
            return e.getBall();
        }
        return null;
    }

    public int hasAnyPlayerWon(Board input)
    {
        int[] players = new int[] { 0, 1 };
        try
        {
            checkHorizontal(input, players);
            checkVertical(input, players);
            checkDiagonal(input, players);
        }
        catch (MoveFoundException e)
        {
            return e.getBall().getPlayer();
        }
        return Ball.NO_PLAYER;
    }

    private void checkDiagonal(Board board, int[] players) throws MoveFoundException
    {
        List<Ball> line = new ArrayList<Ball>();
        for (int x = 0; x < Constants.BOARD_SIZE; x++)
        {
            checkDiagonalLine(x, 0, 1, 1, line, board, players);
            checkDiagonalLine(x, Constants.BOARD_SIZE - 1, -1, -1, line, board, players);
        }
        for (int y = 0; y < Constants.BOARD_SIZE; y++)
        {
            checkDiagonalLine(0, y, 1, -1, line, board, players);
            checkDiagonalLine(Constants.BOARD_SIZE - 1, y, -1, 1, line, board, players);
        }
    }

    private void checkDiagonalLine(int startx, int starty, int xmodifier, int ymodifier, List<Ball> line, Board board,
            int[] players) throws MoveFoundException
    {
        line.clear();
        int xx = startx, yy = starty;
        while (0 <= xx && xx < Constants.BOARD_SIZE && 0 <= yy && yy < Constants.BOARD_SIZE)
        {
            line.add(board.getBall(xx, yy));
            xx += xmodifier;
            yy += ymodifier;
        }
        if (line.size() > 4)
        {
            for (int player : players)
                predicate.check(lineToString(line), line, intToCharacter(player), player);
        }
    }

    private void checkHorizontal(Board board, int[] players) throws MoveFoundException
    {
        List<Ball> line = new ArrayList<Ball>();
        for (int y = 0; y < Constants.BOARD_SIZE; y++)
        {
            line.clear();
            for (int x = 0; x < Constants.BOARD_SIZE; x++)
                line.add(board.getBall(x, y));
            if (line.size() > 4)
            {
                for (int player : players)
                    predicate.check(lineToString(line), line, intToCharacter(player), player);
            }
        }
    }

    private void checkVertical(Board board, int[] players) throws MoveFoundException
    {
        List<Ball> line = new ArrayList<Ball>();
        for (int x = 0; x < Constants.BOARD_SIZE; x++)
        {
            line.clear();
            for (int y = 0; y < Constants.BOARD_SIZE; y++)
                line.add(board.getBall(x, y));
            if (line.size() > 4)
            {
                for (int player : players)
                    predicate.check(lineToString(line), line, intToCharacter(player), player);
            }
        }
    }

    private char intToCharacter(int player)
    {
        return (char)(player + 48);
    }

    private String lineToString(List<Ball> balls)
    {
        StringBuilder sb = new StringBuilder();
        for (Ball ball : balls)
        {
            if (ball.getPlayer() == Ball.NO_PLAYER)
                sb.append("O");
            else
                sb.append(intToCharacter(ball.getPlayer()));
        }
        return sb.toString();
    }
}