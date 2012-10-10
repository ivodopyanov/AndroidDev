/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.game.controller.events.MoveCalculatedEvent;
import ru.naumen.pentago.game.model.Player;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public abstract class AICalculatorImpl extends Handler implements AICalculator
{
    protected final List<Player> players;
    protected Player player;
    private final Handler resultHandler;

    protected AICalculatorImpl(List<Player> players, Looper looper, Handler resultHandler)
    {
        super(looper);
        this.players = players;
        this.resultHandler = resultHandler;
    }

    @Override
    public void handleMessage(Message msg)
    {
        Player player = (Player)msg.obj;
        AIMoveInfo moveInfo = runCalculation(player);
        Message message = resultHandler.obtainMessage();
        message.obj = new MoveCalculatedEvent(moveInfo, player);
        resultHandler.sendMessage(message);
    }

    abstract protected AIMoveInfo runCalculation(Player player);
}