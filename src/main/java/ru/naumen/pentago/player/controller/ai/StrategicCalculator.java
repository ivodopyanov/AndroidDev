/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.List;

import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.Constants.LineCheckPatternSets;
import ru.naumen.pentago.game.Constants.LineIteratorFactories;
import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;
import ru.naumen.pentago.game.positionchecker.MoveScanner;
import ru.naumen.pentago.game.positionchecker.RotateScanner;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public class StrategicCalculator extends AICalculatorImpl
{
    private final RotateScanner rotateScanner;
    private final MoveScanner moveScanner;

    public StrategicCalculator(List<Player> players, Board board, EventBus eventBus)
    {
        super(players, eventBus);
        moveScanner = new MoveScanner(LineCheckPatternSets.MOVE, LineIteratorFactories.POSITION_CHECK, board);
        rotateScanner = new RotateScanner(LineCheckPatternSets.ROTATE, LineIteratorFactories.POSITION_CHECK, board);
    }

    @Override
    protected Ball runMoveCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        List<Ball> moves = moveScanner.findMove(playerPos);
        return moves.get(0);
    }

    @Override
    protected RotateInfo runRotateCalculation(Player player)
    {
        int playerPos = players.indexOf(player);
        List<RotateInfo> rotates = rotateScanner.findRotate(playerPos);
        return rotates.get(0);
    }

    private RotateInfo getRandomRotateInfo()
    {
        Quarter quarter = Quarter.create((int)(Math.random() * 3));
        boolean clockwise = Math.random() < 0.5 ? true : false;
        return new RotateInfo(quarter, clockwise);
    }
}