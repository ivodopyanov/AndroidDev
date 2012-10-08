/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.events.RequestBallMoveEvent;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.player.controller.ai.BestMoveCalculator;
import ru.naumen.pentago.player.controller.ai.BestRotateCalculator;

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
            eventBus.fireEvent(bestMoveCalc.calculate(player));
        }
    }

    @Override
    public void onRequestBoardRotate(RequestBoardRotateEvent event)
    {
        if (event.getActivePlayerCode().equals(player.getCode()))
        {
            eventBus.fireEvent(bestRotateCalc.calculate(player));
        }
    }
}