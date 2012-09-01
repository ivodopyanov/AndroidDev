package ru.naumen.core.game.controller;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.SquareArea;

public class GameController
{
    public enum GamePhase
    {
        PutBall, RotateBoard, Ended;
    }

    public enum RotateDirection
    {
        Clockwise, CounterClockwise;
    }

    private final Game game;
    private int activePlayer = 0;
    private GamePhase phase;
    private OnBoardStateChangedListener onBoardStateChangedListener;
    private OnGameOverListener onGameOverListener;
    private final BoardPositionChecker positionChecker = new BoardPositionChecker();

    public GameController(Game game)
    {
        this.game = game;
        this.phase = GamePhase.PutBall;
    }

    public int getActivePlayer()
    {
        return activePlayer;
    }

    public Game getGame()
    {
        return game;
    }

    public GamePhase getPhase()
    {
        return phase;
    }

    public void makeMove(int position)
    {
        Ball ball = game.getBoard().getBalls().get(position);
        if (ball.getPlayer() == Ball.NO_PLAYER)
        {
            ball.setPlayer(activePlayer);
            activePlayer = getNextPlayer(activePlayer);
            phase = GamePhase.RotateBoard;
            if (onBoardStateChangedListener != null)
            {
                onBoardStateChangedListener.onBoardStateChanged();
            }
        }
    }

    public void rotateBoard(SquareArea input, RotateDirection direction)
    {
        int[][] area = game.getBoard().toArray(input);
        int[][] rotated = doRotate(area, input, direction);
        game.getBoard().fromArray(input, rotated);
        phase = GamePhase.PutBall;
        if (onBoardStateChangedListener != null)
            onBoardStateChangedListener.onBoardStateChanged();
        int winner = positionChecker.hasAnyPlayerWon(game.getBoard());
        if (winner != Ball.NO_PLAYER)
        {
            phase = GamePhase.Ended;
            if (onGameOverListener != null)
                onGameOverListener.onGameOver(winner);
        }
    }

    public void setOnBoardStateChangedListener(OnBoardStateChangedListener onBoardStateChangedListener)
    {
        this.onBoardStateChangedListener = onBoardStateChangedListener;
    }

    public void setOnGameOverListener(OnGameOverListener onGameOverListener)
    {
        this.onGameOverListener = onGameOverListener;
    }

    private int[][] doRotate(int[][] area, SquareArea input, RotateDirection direction)
    {
        int[][] rotated = new int[input.getLength()][];
        for (int i = 0; i < input.getLength(); i++)
            rotated[i] = new int[input.getLength()];
        for (int x = 0; x < input.getLength(); x++)
        {
            for (int y = 0; y < input.getLength(); y++)
            {
                if (RotateDirection.Clockwise.equals(direction))
                    rotated[-y - 1 + input.getLength()][x] = area[x][y];
                else if (RotateDirection.CounterClockwise.equals(direction))
                    rotated[y][-x - 1 + input.getLength()] = area[x][y];
            }
        }
        return rotated;
    }

    private int getNextPlayer(int player)
    {
        player++;
        if (player == game.getPlayers().size())
            player = 0;
        return player;
    }
}