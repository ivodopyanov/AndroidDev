package ru.naumen.pentago.game.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.game.controller.GameController.GamePhase;

public class Game implements Serializable
{
    private static final long serialVersionUID = 7769642206955663086L;
    private Board board;
    private List<Player> players = new ArrayList<Player>();
    private int activePlayer = 0;
    private GamePhase gamePhase = GamePhase.START;
    private int winner = -1;

    public Game()
    {
        this.board = new Board();
    }

    public Game(List<Player> players)
    {
        this.board = new Board();
        this.players = players;
    }

    public int getActivePlayer()
    {
        return activePlayer;
    }

    public Board getBoard()
    {
        return board;
    }

    public GamePhase getGamePhase()
    {
        return gamePhase;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public int getWinner()
    {
        return winner;
    }

    public void setActivePlayer(int activePlayer)
    {
        this.activePlayer = activePlayer;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public void setGamePhase(GamePhase gamePhase)
    {
        this.gamePhase = gamePhase;
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    public void setWinner(int winner)
    {
        this.winner = winner;
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