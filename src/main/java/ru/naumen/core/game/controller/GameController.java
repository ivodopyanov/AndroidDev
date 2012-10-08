package ru.naumen.core.game.controller;

import java.util.List;

import ru.naumen.core.framework.eventbus.EventBus;
import ru.naumen.core.game.Constants.LineCheckPatterns;
import ru.naumen.core.game.Constants.LineIteratorFactories;
import ru.naumen.core.game.controller.events.FinishedBallAnimationEvent;
import ru.naumen.core.game.controller.events.FinishedBallAnimationHandler;
import ru.naumen.core.game.controller.events.FinishedRotateAnimationEvent;
import ru.naumen.core.game.controller.events.FinishedRotateAnimationHandler;
import ru.naumen.core.game.controller.events.GameOverEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Game;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.positionchecker.WinStateScanner;

public class GameController implements FinishedBallAnimationHandler, FinishedRotateAnimationHandler//MoveBallHandler, RotateBoardHandler
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
    private final WinStateScanner winStateScanner;
    private final List<Player> players;
    private final EventBus eventBus;

    public GameController(Game game, List<Player> players, EventBus eventBus)
    {
        this.game = game;
        this.players = players;
        this.eventBus = eventBus;
        this.winStateScanner = new WinStateScanner(LineCheckPatterns.PLAYER_WON, LineIteratorFactories.POSITION_CHECK,
                game.getBoard());
        eventBus.register(FinishedBallAnimationEvent.class, this);
        eventBus.register(FinishedRotateAnimationEvent.class, this);
    }

    @Override
    public void onFinishedBallAnimation(FinishedBallAnimationEvent event)
    {
        if (event.getBall().getPlayer() == Ball.NO_PLAYER)
        {
            event.getBall().setPlayer(activePlayer);
            eventBus.fireEvent(new RequestBoardRotateEvent(players.get(activePlayer).getCode()));
        }
    }

    @Override
    public void onFinishedRotateAnimation(FinishedRotateAnimationEvent event)
    {
        game.getBoard().rotate(event.getRotateInfo());
        activePlayer = getNextPlayer(activePlayer);
        int winner = winStateScanner.hasAnyPlayerWon();
        if (winner != Ball.NO_PLAYER)
        {
            eventBus.fireEvent(new GameOverEvent(winner));
        }
        else
        {
            eventBus.fireEvent(new RequestBallMoveEvent(players.get(activePlayer).getCode()));
        }
    }

    private int getNextPlayer(int player)
    {
        player++;
        if (player == game.getPlayers().size())
            player = 0;
        return player;
    }
}