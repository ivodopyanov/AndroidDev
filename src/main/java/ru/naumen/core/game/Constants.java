package ru.naumen.core.game;

import java.util.List;

import ru.naumen.core.R;
import ru.naumen.core.game.model.Player;
import ru.naumen.core.game.model.Player.PlayerType;

import com.google.common.collect.Lists;

public interface Constants
{
    String BOARD_EXTRA = "board";
    String PLAYERS_EXTRA = "players";
    int BOARD_SIZE = 6;

    List<Player> TWO_HUMAN_PLAYERS = Lists.newArrayList(new Player("Player1", "Player1", R.drawable.black_ball,
            PlayerType.human), new Player("Player2", "Player2", R.drawable.white_ball, PlayerType.human));
    List<Player> HUMAN_COMPUTER_PLAYERS = Lists.newArrayList(new Player("Player", "Player", R.drawable.black_ball,
            PlayerType.human), new Player("Computer", "Computer", R.drawable.white_ball, PlayerType.computer));
}