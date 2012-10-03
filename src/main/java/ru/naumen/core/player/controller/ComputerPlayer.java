/**
 * 
 */
package ru.naumen.core.player.controller;

import java.util.List;

import ru.naumen.core.game.controller.events.MoveBallEvent;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.model.Ball;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

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
    @Subscribe
    public void requestBoardRotation(RequestBoardRotateEvent event)
    {
    }

    @Override
    @Subscribe
    public void requestMove(RequestBallMoveEvent event)
    {
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            eventBus.post(new MoveBallEvent(calcNextBall(), player.getCode()));
        }
    }

    private Ball calcNextBall()
    {
        List<Ball> freeBalls = Lists.newArrayList(Collections2.filter(board.getBalls(), FREE_BALL_FILTER));
        int pos = (int)(Math.random() * (freeBalls.size() - 1));
        return freeBalls.get(pos);
    }
}