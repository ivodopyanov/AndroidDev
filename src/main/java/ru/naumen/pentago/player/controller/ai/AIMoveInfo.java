/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class AIMoveInfo
{
    private final Ball ball;
    private final RotateInfo rotateInfo;

    public AIMoveInfo(Ball ball, RotateInfo rotateInfo)
    {
        this.ball = ball;
        this.rotateInfo = rotateInfo;
    }

    public Ball getBall()
    {
        return ball;
    }

    public RotateInfo getRotateInfo()
    {
        return rotateInfo;
    }
}