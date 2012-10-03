package ru.naumen.core.game.controller;

import java.util.List;

import ru.naumen.core.game.controller.events.GameOverEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.SquareArea;

import com.google.common.eventbus.EventBus;

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
    private final BoardPositionChecker positionChecker = new BoardPositionChecker();
    private final List<Player> players;
    private final EventBus eventBus;

    public GameController(Game game, List<Player> players, EventBus eventBus)
    {
        this.game = game;
        this.players = players;
        this.phase = GamePhase.PutBall;
        this.eventBus = eventBus;
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
            phase = GamePhase.RotateBoard;
            eventBus.post(new RequestBoardRotateEvent(players.get(activePlayer).getCode()));
        }
    }

    public void rotateBoard(SquareArea input, RotateDirection direction)
    {
        int[][] area = game.getBoard().toArray(input);
        int[][] rotated = doRotate(area, input, direction);
        game.getBoard().fromArray(input, rotated);
        phase = GamePhase.PutBall;
        activePlayer = getNextPlayer(activePlayer);
        int winner = positionChecker.hasAnyPlayerWon(game.getBoard());
        if (winner != Ball.NO_PLAYER)
        {
            phase = GamePhase.Ended;
            eventBus.post(new GameOverEvent(winner));
        }
        else
        {
            eventBus.post(new RequestBallMoveEvent(players.get(activePlayer).getCode()));
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