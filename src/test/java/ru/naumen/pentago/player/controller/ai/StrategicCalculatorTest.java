/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.naumen.pentago.game.model.Ball;
import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import ru.naumen.pentago.game.model.RotateInfo;

/**
 * @author ivodopyanov
 * @since 05.11.2012
 * 
 */
public class StrategicCalculatorTest
{

    private final Player p0 = new Player(0, "p0", 0, null);
    private final Player p1 = new Player(0, "p1", 0, null);
    private final List<Player> players = Arrays.asList(p0, p1);
    private final Board board = new Board();
    private final StrategicCalculator calc = new StrategicCalculator(players, board, null);

    @Before
    public void setUp() throws Exception
    {
        for (Ball ball : board.getBalls())
        {
            ball.setPlayer(Ball.NO_PLAYER);
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testMove2()
    {
        initBoard("XXXXXX" + "XXXXXX" + "XXX1XX" + "XXXXXX" + "XXXXXX" + "000111");
        Ball expected = new Ball(3, 1, Ball.NO_PLAYER);
        Ball actual = calc.runMoveCalculation(p0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMove3()
    {
        initBoard("XXXXXX" + "XXX0XX" + "XXX1X1" + "XXXX11" + "X0XXXX" + "X0XXXX");
        Ball expected = new Ball(4, 2, Ball.NO_PLAYER);
        Ball actual = calc.runMoveCalculation(p0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMove4()
    {
        initBoard("XXXXXX" + "XXXXXX" + "XXX00X" + "X01XXX" + "XX1XXX" + "XX0111");
        Ball expected = new Ball(0, 5, Ball.NO_PLAYER);
        Ball actual = calc.runMoveCalculation(p0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRotate2()
    {
        initBoard("XX0XXX" + "XXX0XX" + "XXX1X1" + "XXXX11" + "X00XXX" + "XXXXXX");
        RotateInfo expected = new RotateInfo(Quarter.create(true, true), true);
        RotateInfo actual = calc.runRotateCalculation(p0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRotateToBreakFour()
    {
        initBoard("0000XX" + "XXXXXX" + "XXXXXX" + "XXXXXX" + "XXXXXX" + "XXXXXX");
        RotateInfo expected = new RotateInfo(Quarter.create(true, true), true);
        RotateInfo actual = calc.runRotateCalculation(p1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRotateToBreakFourWithPotentailLoseToSix()
    {
        initBoard("0000XX" + "XXXXXX" + "XXXXXX" + "0X0XXX" + "0X0XXX" + "0X0XXX");
        RotateInfo expected = new RotateInfo(Quarter.create(false, true), true);
        RotateInfo actual = calc.runRotateCalculation(p1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRotateToBreakFourWithPotentialLoseToFive()
    {
        initBoard("0000XX" + "XXXXXX" + "XXXXXX" + "0X0XXX" + "0X0XXX" + "XXXXXX");
        RotateInfo expected = new RotateInfo(Quarter.create(false, true), true);
        RotateInfo actual = calc.runRotateCalculation(p1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRotateToBreakThree()
    {
        initBoard("X000XX" + "XXXXXX" + "XXXXXX" + "XXXXXX" + "XXXXXX" + "XXXXXX");
        RotateInfo expected = new RotateInfo(Quarter.create(true, true), true);
        RotateInfo actual = calc.runRotateCalculation(p1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRotateToWin()
    {
        initBoard("000XX0" + "XXXXX0" + "XXXXXX" + "XXXXXX" + "XXXXXX" + "XXXXXX");
        RotateInfo expected = new RotateInfo(Quarter.create(false, true), false);
        RotateInfo actual = calc.runRotateCalculation(p0);
        Assert.assertEquals(expected, actual);
    }

    private void initBoard(String position)
    {
        for (int i = 0; i < position.length(); i++)
        {
            if (position.charAt(i) == '0')
                board.getBalls().get(i).setPlayer(0);
            else if (position.charAt(i) == '1')
                board.getBalls().get(i).setPlayer(1);
        }
    }
}
