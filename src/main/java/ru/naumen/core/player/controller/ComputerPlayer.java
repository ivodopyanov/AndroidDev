/**
 * 
 */
package ru.naumen.core.player.controller;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.core.framework.collections.Collections;
import ru.naumen.core.framework.collections.Predicate;
import ru.naumen.core.framework.eventbus.EventBus;
import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public class ComputerPlayer extends PlayerControllerImpl
{
    private static final Predicate<Ball> FREE_BALL_FILTER = new Predicate<Ball>()
    {
        @Override
        public boolean apply(Ball ball)
        {
            return ball.getPlayer() == Ball.NO_PLAYER;
        }
    };

    public ComputerPlayer(Player player, EventBus eventBus, Board board)
    {
        super(player, eventBus, board);
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            eventBus.fireEvent(new MoveBallEvent(calcNextBall(), player.getCode()));
        }
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
    }

    private Ball calcNextBall()
    {
        List<Ball> freeBalls = new ArrayList<Ball>(Collections.filter(board.getBalls(), FREE_BALL_FILTER));
        int pos = (int)(Math.random() * (freeBalls.size() - 1));
        return freeBalls.get(pos);
    }
}