package ru.naumen.core.game;

import java.util.Arrays;
import java.util.List;

import ru.naumen.core.R;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.Player.PlayerType;
import ru.naumen.core.game.positionchecker.CheckPattern;

public interface Constants
{
    public interface LineCheckPatterns
    {
        CheckPattern[] PLAYER_WON = new CheckPattern[] { new CheckPattern("?????", 0) };
        CheckPattern[] PLAYER_CAN_WIN = new CheckPattern[] { new CheckPattern("O????", 0), new CheckPattern("????O", 4) };
        CheckPattern[] THREE_IN_A_ROW = new CheckPattern[] { new CheckPattern("O???", 0), new CheckPattern("???O", 3) };
        CheckPattern[] TWO_IN_A_ROW = new CheckPattern[] { new CheckPattern("O??", 0), new CheckPattern("??O", 2) };
        CheckPattern[] SINGLE = new CheckPattern[] { new CheckPattern("O?", 0), new CheckPattern("?O", 1) };
    }

    String BOARD_EXTRA = "board";
    String PLAYERS_EXTRA = "players";

    int BOARD_SIZE = 6;
    List<Player> TWO_HUMAN_PLAYERS = Arrays.asList(new Player("Player1", "Player1", R.drawable.black_ball,
            PlayerType.human), new Player("Player2", "Player2", R.drawable.white_ball, PlayerType.human));

    List<Player> HUMAN_COMPUTER_PLAYERS = Arrays.asList(new Player("Player", "Player", R.drawable.black_ball,
            PlayerType.human), new Player("Computer", "Computer", R.drawable.white_ball, PlayerType.computer));
}