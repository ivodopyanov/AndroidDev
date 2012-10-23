package ru.naumen.pentago.game.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.R;
import ru.naumen.pentago.game.model.Player.PlayerType;

public class Game implements Serializable
{
    private static final long serialVersionUID = 7769642206955663086L;
    private Board board;
    private List<Player> players = new ArrayList<Player>();

    public Game()
    {
        this.board = new Board();
        players.add(new Player("Player1", "Player1", R.drawable.blackball, PlayerType.human));
        players.add(new Player("Player2", "Player1", R.drawable.whiteball, PlayerType.human));
    }

    public Game(List<Player> players)
    {
        this.board = new Board();
        this.players = players;
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