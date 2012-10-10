/**
 * 
 */
package ru.naumen.pentago.player.controller;

import java.util.List;

import ru.naumen.pentago.framework.collections.Function;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.AIStrategies;
import ru.naumen.pentago.game.controller.events.MoveCalculatedEvent;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Player.PlayerType;
import ru.naumen.pentago.player.controller.ai.PositionEvaluationCalculator;
import ru.naumen.pentago.player.controller.ai.StrategicCalculator;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * @author ivodopyanov
 * @since 03.10.2012
 * 
 */
public class PlayerControllerFactory implements Function<Player, PlayerController>
{
    private final Handler.Callback calculatedMoveCallback = new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            eventBus.fireEvent((MoveCalculatedEvent)msg.obj);
            return true;
        }
    };

    private final EventBus eventBus;
    private final Board board;
    private final PositionEvaluationCalculator posEvalCalc;
    private final StrategicCalculator strCalc;
    private final HandlerThread aiCalcThread;
    private final Handler calculatedMoveHandler;

    public PlayerControllerFactory(EventBus eventBus, Board board, List<Player> players)
    {
        this.eventBus = eventBus;
        this.board = board;
        calculatedMoveHandler = new Handler(Looper.getMainLooper(), calculatedMoveCallback);
        aiCalcThread = new HandlerThread("aiCalc");
        aiCalcThread.start();
        posEvalCalc = new PositionEvaluationCalculator(players, board, aiCalcThread.getLooper(), calculatedMoveHandler);
        strCalc = new StrategicCalculator(players, board, AIStrategies.ALL, aiCalcThread.getLooper(),
                calculatedMoveHandler);

    }

    @Override
    public PlayerController apply(Player input)
    {
        if (PlayerType.human.equals(input.getType()))
            return new HumanPlayerController(input, eventBus, board);
        else if (PlayerType.computer.equals(input.getType()))
            return new ComputerPlayer(input, eventBus, board, strCalc);
        return null;
    }
}