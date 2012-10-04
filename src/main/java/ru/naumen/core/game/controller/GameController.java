package ru.naumen.core.game.controller;

import java.util.List;

import ru.naumen.core.framework.eventbus.EventBus;
import ru.naumen.core.game.controller.events.GameOverEvent;
import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.controller.events.MoveBallHandler;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.controller.events.RotateBoardEvent;
import ru.naumen.core.game.controller.events.RotateBoardHandler;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.SquareArea;

public class GameController implements MoveBallHandler, RotateBoardHandler
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
    private final BoardPositionChecker positionChecker = new BoardPositionChecker();
    private final List<Player> players;
    private final EventBus eventBus;

    public GameController(Game game, List<Player> players, EventBus eventBus)
    {
        this.game = game;
        this.players = players;
        this.eventBus = eventBus;
        eventBus.register(MoveBallEvent.class, this);
        eventBus.register(RotateBoardEvent.class, this);
    }

    @Override
    public void onMoveBall(MoveBallEvent event)
    {
        if (event.getBall().getPlayer() == Ball.NO_PLAYER)
        {
            event.getBall().setPlayer(activePlayer);
            eventBus.fireEvent(new RequestBoardRotateEvent(players.get(activePlayer).getCode()));
        }
    }

    @Override
    public void onRotateBoard(RotateBoardEvent event)
    {
        int[][] area = game.getBoard().toArray(event.getArea());
        int[][] rotated = doRotate(area, event.getArea(), event.getDirection());
        game.getBoard().fromArray(event.getArea(), rotated);
        activePlayer = getNextPlayer(activePlayer);
        int winner = positionChecker.hasAnyPlayerWon(game.getBoard());
        if (winner != Ball.NO_PLAYER)
        {
            eventBus.fireEvent(new GameOverEvent(winner));
        }
        else
        {
            eventBus.fireEvent(new RequestBallMoveEvent(players.get(activePlayer).getCode()));
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