/**
 * 
 */
package ru.naumen.core.player.controller;

import ru.naumen.core.framework.eventbus.EventBus;
import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.core.game.model.Board;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.player.controller.ai.BestMoveCalculator;
import ru.naumen.core.player.controller.ai.BestRotateCalculator;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public class ComputerPlayer extends PlayerControllerImpl
{
    private final BestMoveCalculator bestMoveCalc;
    private final BestRotateCalculator bestRotateCalc;

    public ComputerPlayer(Player player, EventBus eventBus, Board board, BestMoveCalculator bestMoveCalc,
            BestRotateCalculator bestRotateCalc)
    {
        super(player, eventBus, board);
        this.bestMoveCalc = bestMoveCalc;
        this.bestRotateCalc = bestRotateCalc;
    }

    @Override
    public void onRequestBallMove(RequestBallMoveEvent event)
    {
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            eventBus.fireEvent(bestMoveCalc.calculate(board, player.getCode()));
        }
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            eventBus.fireEvent(bestRotateCalc.calculate(board, player.getCode()));
        }
    }
}