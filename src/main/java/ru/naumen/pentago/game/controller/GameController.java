package ru.naumen.pentago.game.controller;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.LineCheckPatternSets;
import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.Constants.LogTag;
import ru.naumen.pentago.game.controller.events.FinishedBallAnimationEvent;
import ru.naumen.pentago.game.controller.events.FinishedBallAnimationHandler;
import ru.naumen.pentago.game.controller.events.FinishedRotateAnimationEvent;
import ru.naumen.pentago.game.controller.events.FinishedRotateAnimationHandler;
import ru.naumen.pentago.game.controller.events.GameOverEvent;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Game;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.positionchecker.WinStateScanner;
import android.util.Log;

public class GameController implements FinishedBallAnimationHandler, FinishedRotateAnimationHandler//MoveBallHandler, RotateBoardHandler
{
    public enum GamePhase
    {
        START, MOVE, ROTATE, GAME_OVER;
    }

    private final Game game;
    private final WinStateScanner winStateScanner;
    private final List<Player> players;

    public GameController(Game game, List<Player> players)
    {
        this.game = game;
        this.players = players;
        this.winStateScanner = new WinStateScanner(LineCheckPatternSets.PLAYER_WON,
                LineIteratorFactories.POSITION_CHECK, game.getBoard());
        EventBus.INSTANCE.register(FinishedBallAnimationEvent.class, this);
        EventBus.INSTANCE.register(FinishedRotateAnimationEvent.class, this);
    }

    @Override
    public void onFinishedBallAnimation(FinishedBallAnimationEvent event)
    {
        if (event.getBall().getPlayer() == Ball.NO_PLAYER)
        {
            //Т.к. ball из event может придти к нам через bluetooth
            game.getBoard().getBall(event.getBall().getX(), event.getBall().getY()).setPlayer(game.getActivePlayer());
            game.setGamePhase(GamePhase.ROTATE);
            performNextStep();
        }
    }

    @Override
    public void onFinishedRotateAnimation(FinishedRotateAnimationEvent event)
    {
        Log.d(LogTag.GAME, "onFinishedRotateAnimation");
        game.setActivePlayer(getNextPlayer(game.getActivePlayer()));
        int winner = winStateScanner.hasAnyPlayerWon();
        if (winner != Ball.NO_PLAYER)
        {
            game.setGamePhase(GamePhase.GAME_OVER);

        }
        else
        {
            game.setGamePhase(GamePhase.MOVE);

        }
        performNextStep();
    }

    public void performNextStep()
    {
        switch (game.getGamePhase())
        {
        case START:
        {
            game.setGamePhase(GamePhase.MOVE);
            performNextStep();
            break;
        }
        case MOVE:
        {
            EventBus.INSTANCE.fireEvent(new RequestBallMoveEvent(players.get(game.getActivePlayer()).getCode()));
            break;
        }
        case ROTATE:
        {
            EventBus.INSTANCE.fireEvent(new RequestBoardRotateEvent(players.get(game.getActivePlayer()).getCode()));
            break;
        }
        case GAME_OVER:
        {
            EventBus.INSTANCE.fireEvent(new GameOverEvent(game.getWinner()));
            break;
        }
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