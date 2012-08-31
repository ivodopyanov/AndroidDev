package ru.naumen.core.game.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import ru.naumen.core.R;

import com.google.common.collect.Lists;

public class Game implements Serializable
{
    private static final long serialVersionUID = 7769642206955663086L;
    private Board board;
    private List<Player> players = Lists.newArrayList();

    public Game()
    {
        this.board = new Board();
        players.add(new Player("Player1", R.drawable.ball1));
        players.add(new Player("Player2", R.drawable.ball2));
    }

    public Board getBoard()
    {
        return board;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        board = (Board)in.readObject();
        players.clear();
        int size = in.readInt();
        for (int i = 0; i < size; i++)
        {
            players.add((Player)in.readObject());
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        out.writeObject(board);
        out.write(players.size());
        for (Player player : players)
        {
            out.writeObject(player);
        }
    }
}