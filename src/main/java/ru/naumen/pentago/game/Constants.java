package ru.naumen.pentago.game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import ru.naumen.pentago.R;
import ru.naumen.pentago.game.model.Player.PlayerType;
import ru.naumen.pentago.game.positionchecker.CheckPattern;
import ru.naumen.pentago.game.positionchecker.CheckPatternSet;
import ru.naumen.pentago.game.positionchecker.iterators.factories.DiagonalLineIteratorFactory;
import ru.naumen.pentago.game.positionchecker.iterators.factories.HorizontalLineIteratorFactory;
import ru.naumen.pentago.game.positionchecker.iterators.factories.LineIteratorFactory;
import ru.naumen.pentago.game.positionchecker.iterators.factories.VerticalLineIteratorFactory;

public interface Constants
{
    public interface LineCheckPatterns
    {
        //@formatter:off
        CheckPattern[] PLAYER_WON=new CheckPattern[]{new CheckPattern("?????", 0)};
        CheckPattern[] CAN_WIN=new CheckPattern[] { 
                new CheckPattern("X????", 0),
                new CheckPattern("?X???", 1), 
                new CheckPattern("??X??", 2), 
                new CheckPattern("???X?", 3),
                new CheckPattern("????X", 4)}; 
        CheckPattern[] CAN_FOUR_IN_A_ROW = new CheckPattern[] { 
                new CheckPattern("X???", 0),
                new CheckPattern("?X??", 1),
                new CheckPattern("??X?", 2),
                new CheckPattern("???X", 3) };
        CheckPattern[] CAN_THREE_IN_A_ROW = new CheckPattern[] { 
                new CheckPattern("X??", 0),
                new CheckPattern("?X?", 1),
                new CheckPattern("??X", 2) };
        CheckPattern[] CAN_TWO_IN_A_ROW = new CheckPattern[] { 
                new CheckPattern("X?", 0), 
                new CheckPattern("?X", 1) };
        //@formatter:on
    }

    public interface LineCheckPatternSets
    {
        //@formatter:off
        CheckPatternSet PLAYER_WON = new CheckPatternSet(LineCheckPatterns.PLAYER_WON, 10000.0);
        CheckPatternSet OPPONENT_WON = new CheckPatternSet(LineCheckPatterns.PLAYER_WON, -10000.0);
        CheckPatternSet PLAYER_CAN_WIN = new CheckPatternSet(LineCheckPatterns.CAN_WIN, 1000.0);
        CheckPatternSet OPPONENT_CAN_WIN = new CheckPatternSet(LineCheckPatterns.CAN_WIN, -1000.0);
        CheckPatternSet PLAYER_CAN_FOUR_IN_A_ROW = new CheckPatternSet(LineCheckPatterns.CAN_FOUR_IN_A_ROW, 200.0);
        CheckPatternSet OPPONENT_CAN_FOUR_IN_A_ROW = new CheckPatternSet(LineCheckPatterns.CAN_FOUR_IN_A_ROW, -200.0);
        CheckPatternSet PLAYER_CAN_THREE_IN_A_ROW = new CheckPatternSet(LineCheckPatterns.CAN_THREE_IN_A_ROW, 20.0);
        CheckPatternSet OPPONENT_CAN_THREE_IN_A_ROW = new CheckPatternSet(LineCheckPatterns.CAN_THREE_IN_A_ROW, -20.0);
        CheckPatternSet PLAYER_CAN_TWO_IN_A_ROW = new CheckPatternSet(LineCheckPatterns.CAN_TWO_IN_A_ROW, 1.0);
        CheckPatternSet[] ROTATE=new CheckPatternSet[]{
                    LineCheckPatternSets.PLAYER_WON,
                    LineCheckPatternSets.OPPONENT_WON,
                    LineCheckPatternSets.PLAYER_CAN_WIN, 
                    LineCheckPatternSets.OPPONENT_CAN_WIN, 
                    LineCheckPatternSets.OPPONENT_CAN_FOUR_IN_A_ROW,
                    LineCheckPatternSets.PLAYER_CAN_FOUR_IN_A_ROW,
                    LineCheckPatternSets.PLAYER_CAN_THREE_IN_A_ROW,
                    LineCheckPatternSets.PLAYER_CAN_TWO_IN_A_ROW};
        CheckPatternSet[] MOVE=new CheckPatternSet[]{
                LineCheckPatternSets.PLAYER_CAN_WIN, 
                LineCheckPatternSets.OPPONENT_CAN_WIN, 
                LineCheckPatternSets.OPPONENT_CAN_FOUR_IN_A_ROW,
                LineCheckPatternSets.PLAYER_CAN_FOUR_IN_A_ROW,
                LineCheckPatternSets.PLAYER_CAN_THREE_IN_A_ROW,
                LineCheckPatternSets.PLAYER_CAN_TWO_IN_A_ROW};
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

    String APPLICATION_TITLE = "Twisting Balls";
    String BOARD_EXTRA = "board";
    String PLAYERS_EXTRA = "players";
    int REQUEST_BT_ENABLED = 1;
    int REQUEST_BT_DISCOVERABLE = 2;
    UUID uuid = UUID.nameUUIDFromBytes(APPLICATION_TITLE.getBytes());
    int MESSAGE_READ = 2;
    int GAME_OVER_BLUETOOTH_DISCONNECTED = -1;

    int BOARD_SIZE = 6;
    //@formatter:off
    List<DefaultPlayerDescription> TWO_HUMAN_PLAYERS = Arrays.asList(
            new DefaultPlayerDescription(R.string.Player1, "Player1", R.drawable.blackball, PlayerType.human), 
            new DefaultPlayerDescription(R.string.Player2, "Player2", R.drawable.whiteball, PlayerType.human));

    List<DefaultPlayerDescription> HUMAN_COMPUTER_PLAYERS = Arrays.asList(
            new DefaultPlayerDescription(R.string.You,    "Player",   R.drawable.blackball, PlayerType.human), 
            new DefaultPlayerDescription(R.string.Computer,  "Computer", R.drawable.whiteball, PlayerType.computer));
    //@formatter:on
}