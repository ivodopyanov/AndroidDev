package ru.naumen.core.game.controller;

import java.util.Set;

import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.SquareArea;

import com.google.common.collect.Sets;

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
    private final Set<OnBoardStateChangedListener> onBoardStateChangedListeners = Sets.newHashSet();
    private final Set<OnGameOverListener> onGameOverListeners = Sets.newHashSet();
    private final BoardPositionChecker positionChecker = new BoardPositionChecker();

    public GameController(Game game)
    {
        this.game = game;
        this.phase = GamePhase.PutBall;
    }

    public void addOnBoardStateChangedListener(OnBoardStateChangedListener onBoardStateChangedListener)
    {
        this.onBoardStateChangedListeners.add(onBoardStateChangedListener);
    }

    public void addOnGameOverListener(OnGameOverListener onGameOverListener)
    {
        this.onGameOverListeners.add(onGameOverListener);
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

    public void makeMove(Ball ball)
    {
        if (ball.getPlayer() == Ball.NO_PLAYER)
        {
            ball.setPlayer(activePlayer);
            activePlayer = getNextPlayer(activePlayer);
            phase = GamePhase.RotateBoard;
            for (OnBoardStateChangedListener listener : onBoardStateChangedListeners)
            {
                listener.onBoardStateChanged();
            }
        }
    }

    public void rotateBoard(SquareArea input, RotateDirection direction)
    {
        int[][] area = game.getBoard().toArray(input);
        int[][] rotated = doRotate(area, input, direction);
        game.getBoard().fromArray(input, rotated);
        phase = GamePhase.PutBall;
        for (OnBoardStateChangedListener listener : onBoardStateChangedListeners)
        {
            listener.onBoardStateChanged();
        }
        int winner = positionChecker.hasAnyPlayerWon(game.getBoard());
        if (winner != Ball.NO_PLAYER)
        {
            phase = GamePhase.Ended;
            for (OnGameOverListener listener : onGameOverListeners)
            {
                listener.onGameOver(winner);
            }
        }
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