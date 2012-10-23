package ru.naumen.pentago.game;

import java.util.Arrays;
import java.util.List;

import ru.naumen.pentago.R;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Player.PlayerType;
import ru.naumen.pentago.game.positionchecker.CheckPattern;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.game.positionchecker.iterators.DiagonalLineIteratorFactory;
import ru.naumen.pentago.game.positionchecker.iterators.HorizontalLineIteratorFactory;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;
import ru.naumen.pentago.game.positionchecker.iterators.VerticalLineIteratorFactory;
import ru.naumen.pentago.player.controller.ai.AIStrategyDescriptor;

public interface Constants
{
    public interface AIStrategies
    {
        //@formatter:off
        AIStrategyDescriptor[] ONLY_POSITIVE=
                new AIStrategyDescriptor[]{
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.PLAYER_CAN_WIN),
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.THREE_IN_A_ROW),
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.TWO_IN_A_ROW),
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.SINGLE)
                    };
        AIStrategyDescriptor[] ALL=
                new AIStrategyDescriptor[]{
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.PLAYER_CAN_WIN),
                    new AIStrategyDescriptor(AIStrategy.INVERT, LineCheckPatterns.PLAYER_CAN_WIN),
                    new AIStrategyDescriptor(AIStrategy.INVERT, LineCheckPatterns.THREE_IN_A_ROW),
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.THREE_IN_A_ROW),
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.TWO_IN_A_ROW),
                    new AIStrategyDescriptor(AIStrategy.DIRECT, LineCheckPatterns.SINGLE)
                    };
        //@formatter:on
    }

    public interface AIStrategy
    {
        String DIRECT = "direct";
        String INVERT = "invert";
    }

    public interface LineCheckPatterns
    {
        //@formatter:off
        CheckPatternSet PLAYER_WON = new CheckPatternSet(new CheckPattern[] { new CheckPattern("?????", 0) }, 1000.0);
        CheckPatternSet PLAYER_CAN_WIN = new CheckPatternSet(new CheckPattern[] { 
                new CheckPattern("O????", 0),
                new CheckPattern("?O???", 1), 
                new CheckPattern("??O??", 2), 
                new CheckPattern("??O??", 3),
                new CheckPattern("????O", 4) }, 160.0);
        CheckPatternSet THREE_IN_A_ROW = new CheckPatternSet(new CheckPattern[] { 
                new CheckPattern("O???", 0), 
                new CheckPattern("???O", 3) }, 80.0);
        CheckPatternSet TWO_IN_A_ROW = new CheckPatternSet(new CheckPattern[] { 
                new CheckPattern("O??", 0), 
                new CheckPattern("??O", 2) }, 40.0);
        CheckPatternSet SINGLE = new CheckPatternSet(new CheckPattern[] { 
                new CheckPattern("O?", 0), 
                new CheckPattern("?O", 1) }, 20.0);
        //@formatter:on
    }

    public interface LineIteratorFactories
    {
        //@formatter:off
        LineIteratorFactory[] POSITION_CHECK = new LineIteratorFactory[] { 
                new HorizontalLineIteratorFactory(),
                new VerticalLineIteratorFactory(), 
                new DiagonalLineIteratorFactory() };
        //@formatter:on
    }

    public interface LogTag
    {
        String CORNER = "corner";
        String GAME = "game";
        String COMPUTER = "computer";
    }

    public interface PositionEvaluationPatterns
    {
        //@formatter:off
        CheckPatternSet[] ALL=new CheckPatternSet[]{
                LineCheckPatterns.PLAYER_CAN_WIN,
                LineCheckPatterns.THREE_IN_A_ROW,
                LineCheckPatterns.TWO_IN_A_ROW,
                LineCheckPatterns.SINGLE
        };
        //@formatter:on
    }

    String BOARD_EXTRA = "board";
    String PLAYERS_EXTRA = "players";

    int BOARD_SIZE = 6;
    //@formatter:off
    List<Player> TWO_HUMAN_PLAYERS = Arrays.asList(
            new Player("Player1", "Player1", R.drawable.blackball, PlayerType.human), 
            new Player("Player2", "Player2", R.drawable.whiteball, PlayerType.human));

    List<Player> HUMAN_COMPUTER_PLAYERS = Arrays.asList(
            new Player("Player",    "Player",   R.drawable.blackball, PlayerType.human), 
            new Player("Computer",  "Computer", R.drawable.whiteball, PlayerType.computer));
    //@formatter:on
}